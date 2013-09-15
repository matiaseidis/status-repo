package org.cachos.dimon.state.logger.service;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.ClientActivityEvent;
import org.cachos.dimon.state.logger.event.ClientStatusEvent;
import org.cachos.dimon.state.logger.event.type.CachoDirection;
import org.cachos.dimon.state.logger.event.type.ClientState;
import org.cachos.dimon.state.logger.plan.ClientActivity;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.RepositoryManager;

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
		return RepositoryManager.getInstance().getPrevayler().prevalentSystem()
				.getPlansMap().get(id);
	}
	
	@GET
	@Path("/clientActivity/{ip}/{port}")
	@Produces(MediaType.APPLICATION_JSON)
	public ClientActivity getClientActivity(@PathParam("ip") String ip, @PathParam("port") String port) {
		return new ClientActivity(RepositoryManager.getInstance().getPrevayler().prevalentSystem()
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
	
	@GET
	@Path("/activityReport/{action}/{ip}/{port}/{planId}/{clientId}/{byteCurrent}/{byteFrom}/{byteTo}/{bandWidth}")
	public Response logPlanParticipantEvent(@PathParam("action") String action,
			@PathParam("ip") String ip, @PathParam("port") String port,
			@PathParam("planId") String planId,
			@PathParam("clientId") String clientId,
			@PathParam("byteCurrent") long byteCurrent,
			@PathParam("byteFrom") long byteFrom,
			@PathParam("byteTo") long byteTo,
			@PathParam("bandWidth") long bandWidth) {

		RepositoryManager repo = initRepo();
		CachoDirection direction = CachoDirection.PUSH.name().equalsIgnoreCase(action) ? CachoDirection.PUSH : CachoDirection.PULL; 
		repo.logClientActivityEvent(new ClientActivityEvent(direction, ip, port, planId, clientId, byteFrom, byteTo, byteCurrent, bandWidth));
		
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
			return RepositoryManager.getInstance(new Conf()).open();
		} catch (Exception e) {
			logger.fatal("unable to open() repo", e);
		}
		return null;
	}

}
