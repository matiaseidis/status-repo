package org.cachos.dimon.state.logger.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.Conf;
import org.cachos.dimon.state.logger.event.AliveEvent;
import org.cachos.dimon.state.logger.event.PullEvent;
import org.cachos.dimon.state.logger.event.PushEvent;
import org.cachos.dimon.state.logger.event.ShutDownEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.RepositoryManager;

@Path("/logger")
public class StateLoggerService {

	private static final String planParticipantUriSuffix = "/{ip}/{port}/{planId}/{pullerId}/{byteCurrent}/{byteFrom}/{byteTo}";
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
	@Path("/check/{ip}/{port}")
	public Response checkCLientState(@PathParam("ip") String ip, @PathParam("port") String port) {
		String status = "UNKNOWN";
		
		RepositoryManager repo = initRepo();
		status = repo.isUp(ip, port) ? "UP" : "DOWN";
		return Response.status(200).entity(status).build();
	}
	
	@GET
	@Path("/{action}/{ip}/{port}/{planId}/{clientId}/{byteCurrent}/{byteFrom}/{byteTo}")
	public Response logPlanParticipantEvent(@PathParam("action") String action,
			@PathParam("ip") String ip, @PathParam("port") String port,
			@PathParam("planId") String planId,
			@PathParam("clientId") String clientId,
			@PathParam("byteCurrent") long byteCurrent,
			@PathParam("byteFrom") long byteFrom,
			@PathParam("byteTo") long byteTo) {

		RepositoryManager repo = initRepo();
		
		if ("push".equalsIgnoreCase(action)) {
			repo.logPushEvent(new PushEvent(ip, port, planId, clientId, byteCurrent, byteFrom, byteTo));
		} else {
			repo.logPullEvent(new PullEvent(ip, port, planId, clientId, byteCurrent, byteFrom, byteTo));
		}
		return Response.status(200).entity("OK").build();
	}

	@GET
	@Path("/statusEvent/{event}/{ip}/{port}")
	public Response logLifeCycleEvent(@PathParam("event") String event, @PathParam("ip") String ip,
			@PathParam("port") String port) {

		RepositoryManager repo = initRepo();

		if("startup".equalsIgnoreCase(event)) {
			repo.log(new StartUpEvent(ip, port));
		} else if ("stop".equalsIgnoreCase(event)) {
			repo.log(new ShutDownEvent(ip, port));
		} else if ("alive".equalsIgnoreCase(event)) {
			repo.log(new AliveEvent(ip, port));
		}
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
