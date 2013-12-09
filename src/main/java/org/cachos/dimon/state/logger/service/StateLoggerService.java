package org.cachos.dimon.state.logger.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.ClientEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;
import org.cachos.dimon.state.logger.event.type.CachoDirection;
import org.cachos.dimon.state.logger.event.type.ClientState;
import org.cachos.dimon.state.logger.plan.ClientActivity;
import org.cachos.dimon.state.logger.plan.NetworkState;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.plan.RetrievalPlanParticipant;
import org.cachos.dimon.state.logger.repo.RepositoryManager;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/logger")
public class StateLoggerService {

	static Logger logger = Logger.getLogger(StateLoggerService.class.getName());
	
	/**
	 * 
	 * @return por cada nodo del cluster, 
	 * si esta recibiendo o enviando, y el status del cachoRequest
	 */
	@GET
	@Path("/networkState")
	@Produces(MediaType.APPLICATION_JSON)
	public NetworkState getNetworkStatus() {
		
//		Map<String, RetrievalPlan> plansMap = initRepo().getPrevayler().prevalentSystem().getPlansMap();
		
//		Map<String, ClientEvent> networkStatus = new HashMap<String, ClientEvent>();
//		Map<String, List<ClientEvent>> eventsByClientMap = initRepo().getPrevayler().prevalentSystem().getEventsByClientMap();
//		
//		for(Map.Entry<String, List<ClientEvent>> clientEventHistory : eventsByClientMap.entrySet()) {
//			if(CollectionUtils.isEmpty(clientEventHistory.getValue())) {
//				logger.debug("Nothing to report for client " + clientEventHistory.getKey()+ " - IGONRE");
//			} else {
//				int last = clientEventHistory.getValue().size() - 1;
//				networkStatus.put(clientEventHistory.getKey(), clientEventHistory.getValue().get(last));
//			}
//		}
		
		Map<String, ClientEvent> strimers = new HashMap<String, ClientEvent>();
		Map<String, ClientEvent> pushers = new HashMap<String, ClientEvent>();
		Map<String, ClientEvent> pullers = new HashMap<String, ClientEvent>();
		List<String> iddles = new ArrayList<String>();
		
		for(int i = 1; i<6; i++) {
			ClientActivityEvent clientActivityEvent = new ClientActivityEvent(CachoDirection.PULL, "localhost", "999"+i, "demoPlanId", "demoClientId",
					i*1000, i*700, i*1500, 1.1);
			strimers.put("localhost:999"+i, clientActivityEvent);
			pushers.put("localhost:999"+i, clientActivityEvent);
			pullers.put("localhost:999"+i, clientActivityEvent);
			iddles.add("localhost:999"+i);
		}
		
		NetworkState ns = new NetworkState();
		ns.setIddles(iddles);
		ns.setPullers(pullers);
		ns.setPushers(pushers);
		ns.setStrimers(strimers);
		
		return ns;
	}
	

	@GET
	@Path("/plan")
	@Produces(MediaType.APPLICATION_JSON)
	public RetrievalPlan getDemoPlan() {
		RetrievalPlan plan = new DemoRetrievalPlanFactory().createDemoRetrievalPlan();
		return plan;
	}

	@GET
	@Path("/plan/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RetrievalPlan getPlan(@PathParam("id") String id) {

		RetrievalPlan result = initRepo().getPrevayler().prevalentSystem().getPlansMap().get(id);
		if (result != null) {

			Collections.sort(result.getPulls(), new Comparator<RetrievalPlanParticipant>() {

				public int compare(RetrievalPlanParticipant o1, RetrievalPlanParticipant o2) {

					return o1.getByteFrom() > o2.getByteFrom() ? 1 : o1.getByteFrom() == o2.getByteFrom() ? 0 : -1;
				}

			});

		}
		return result == null ? new RetrievalPlan() : result;
	}

	@GET
	@Path("/clientActivity/{ip}/{port}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientActivity getClientActivity(@PathParam("ip") String ip, @PathParam("port") String port) {
		return new ClientActivity(initRepo().getPrevayler().prevalentSystem().getEventsByClient(ip, port));
	}

	@GET
	@Path("/check/{ip}/{port}")
	public Response checkCLientState(@PathParam("ip") String ip, @PathParam("port") String port) {
		String status = "UNKNOWN";

		RepositoryManager repo = initRepo();
		status = repo.isUp(ip, port) ? "UP" : "DOWN";
		return Response.status(200).entity(status).build();
	}

	// http://localhost:8081/service/logger/activityReport/PULL/localhost/10002/plan-id/localhost:10002/27294226/27262976/29360127/49682.03497615262
	// @GET
	// @Path("/activityReport/{action}/{ip}/{port}/{planId}/{clientId}/{byteCurrent}/{byteFrom}/{byteTo}/{bandWidth}")
	// public Response logPlanParticipantEvent(@PathParam("action") String
	// action,
	// @PathParam("ip") String ip, @PathParam("port") String port,
	// @PathParam("planId") String planId,
	// @PathParam("clientId") String clientId,
	// @PathParam("byteCurrent") long byteCurrent,
	// @PathParam("byteFrom") long byteFrom,
	// @PathParam("byteTo") long byteTo,
	// @PathParam("bandWidth") double bandWidth) {
	//
	// RepositoryManager repo = initRepo();
	// CachoDirection direction =
	// CachoDirection.PUSH.name().equalsIgnoreCase(action) ? CachoDirection.PUSH
	// : CachoDirection.PULL;
	// repo.logClientActivityEvent(new ClientActivityEvent(direction, ip, port,
	// planId, clientId, byteCurrent, byteFrom, byteTo, bandWidth));
	//
	// return Response.status(200).entity("OK").build();
	// }

	@POST
	@Path("/activityReport")
	public Response logPlanParticipantEvent(JSONObject activity) {

		logger.info(activity.toString());

		RepositoryManager repo = initRepo();
		int messageLenght;
		try {
			messageLenght = activity.getJSONArray("cachos").length();

			for (int i = 0; i < messageLenght; i++) {
				JSONObject cacho = activity.getJSONArray("cachos").getJSONObject(i);
				repo.logClientActivityEvent(new ClientActivityEvent(CachoDirection.forEvent(cacho.getString("action")),
						// cacho.getString("ip")
						"lalala", cacho.getString("port"), cacho.getString("planId"), cacho.getString("clientId"),
						cacho.getLong("byteCurrent"), cacho.getLong("byteFrom"), cacho.getLong("byteTo"), cacho
								.getDouble("bandWidth")));
			}
		} catch (JSONException e) {
			logger.error(e);
		}
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("/statusReport/{event}/{ip}/{port}/{clientId}/{bandWidth}")
	public Response logLifeCycleEvent(@PathParam("event") String event, @PathParam("ip") String ip,
			@PathParam("port") String port, @PathParam("clientId") String clientId,
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
