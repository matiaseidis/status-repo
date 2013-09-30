package org.cachos.dimon.state.logger.service;

import java.util.Collections;
import java.util.Comparator;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;
import org.cachos.dimon.state.logger.event.type.CachoDirection;
import org.cachos.dimon.state.logger.event.type.ClientState;
import org.cachos.dimon.state.logger.plan.ClientActivity;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.plan.RetrievalPlanParticipant;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/logger")
public class StateLoggerService {

	static Logger logger = Logger.getLogger(StateLoggerService.class.getName());

	@GET
	@Path("/plan")
	@Produces(MediaType.APPLICATION_JSON)
	public RetrievalPlan getDemoPlan() {
		RetrievalPlan plan = new DemoRetrievalPlanFactory()
				.createDemoRetrievalPlan();
		return plan;
	}

	@GET
	@Path("/plan/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RetrievalPlan getPlan(@PathParam("id") String id) {
		
		
		
		RetrievalPlan result = initRepo().getPrevayler().prevalentSystem()
				.getPlansMap().get(id);
		if(result != null){
		
		Collections.sort(result.getPulls(), new Comparator<RetrievalPlanParticipant>(){

			public int compare(RetrievalPlanParticipant o1,
					RetrievalPlanParticipant o2) {
				
				return o1.getByteFrom() > o2.getByteFrom() ? 1 : o1.getByteFrom() == o2.getByteFrom() ? 0 : -1;
			}
			
		});
		
		
		Collections.sort(result.getPulls(), new Comparator<RetrievalPlanParticipant>(){

			public int compare(RetrievalPlanParticipant o1,
					RetrievalPlanParticipant o2) {
				
				return o1.getProgress() > o2.getProgress() ? -1 : o1.getProgress() == o2.getProgress() ? 0 : 1;
			}
			
		});
	}
		return result == null ? new RetrievalPlan() : result;
	}
	
	@GET
	@Path("/clientActivity/{ip}/{port}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientActivity getClientActivity(@PathParam("ip") String ip, @PathParam("port") String port) {
		return new ClientActivity(initRepo().getPrevayler().prevalentSystem()
				.getEventsByClient(ip, port));
	}

	@GET
	@Path("/check/{ip}/{port}")
	public Response checkCLientState(@PathParam("ip") String ip, @PathParam("port") String port) {
		String status = "UNKNOWN";
		
		RepositoryManager repo = initRepo();
		status = repo.isUp(ip, port) ? "UP" : "DOWN";
		return Response.status(200).entity(status).build();
	}
//	http://localhost:8081/service/logger/activityReport/PULL/localhost/10002/plan-id/localhost:10002/27294226/27262976/29360127/49682.03497615262
//	@GET
//	@Path("/activityReport/{action}/{ip}/{port}/{planId}/{clientId}/{byteCurrent}/{byteFrom}/{byteTo}/{bandWidth}")
//	public Response logPlanParticipantEvent(@PathParam("action") String action,
//			@PathParam("ip") String ip, @PathParam("port") String port,
//			@PathParam("planId") String planId,
//			@PathParam("clientId") String clientId,
//			@PathParam("byteCurrent") long byteCurrent,
//			@PathParam("byteFrom") long byteFrom,
//			@PathParam("byteTo") long byteTo,
//			@PathParam("bandWidth") double bandWidth) {
//
//		RepositoryManager repo = initRepo();
//		CachoDirection direction = CachoDirection.PUSH.name().equalsIgnoreCase(action) ? CachoDirection.PUSH : CachoDirection.PULL; 
//		repo.logClientActivityEvent(new ClientActivityEvent(direction, ip, port, planId, clientId, byteCurrent, byteFrom, byteTo, bandWidth));
//		
//		return Response.status(200).entity("OK").build();
//	}
	
	@POST
	@Path("/activityReport")
	public Response logPlanParticipantEvent(JSONObject activity) {
		
//		logger.info(activity.toString());

		RepositoryManager repo = initRepo();
		int messageLenght;
		try {
			messageLenght = activity.getJSONArray("cachos").length();
		
		for(int i = 0; i < messageLenght; i++){
			JSONObject cacho = activity.getJSONArray("cachos").getJSONObject(i);
			repo.logClientActivityEvent(new ClientActivityEvent(
					CachoDirection.forEvent(cacho.getString("action")), 
					cacho.getString("ip"), 
					cacho.getString("port"), 
					cacho.getString("planId"), 
					cacho.getString("clientId"), 
					cacho.getLong("byteCurrent"), 
					cacho.getLong("byteFrom"), 
					cacho.getLong("byteTo"), 
					cacho.getDouble("bandWidth")));
		}
		} catch (JSONException e) {
			logger.error(e);
		}
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("/statusReport/{event}/{ip}/{port}/{clientId}/{bandWidth}")
	public Response logLifeCycleEvent(
			@PathParam("event") String event, 
			@PathParam("ip") String ip,
			@PathParam("port") String port,
			@PathParam("clientId") String clientId,
			@PathParam("bandWidth") long bandWidth) {

		RepositoryManager repo = initRepo();
		ClientState clientState = ClientState.forEvent(event);
		repo.logClientStatusEvent(new ClientStatusEvent(clientState, ip, port, clientId, bandWidth));
		return Response.status(200).entity("OK").build();
	}

	private RepositoryManager initRepo() {
		try {
			return RepositoryManager.getInstance().open();
		} catch (Exception e) {
			logger.fatal("unable to open() repo", e);
		}
		return null;
	}

}
