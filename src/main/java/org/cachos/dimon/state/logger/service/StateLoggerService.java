package org.cachos.dimon.state.logger.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.AliveEvent;
import org.cachos.dimon.state.logger.event.PullEvent;
import org.cachos.dimon.state.logger.event.PushEvent;
import org.cachos.dimon.state.logger.event.ShutDownEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.RepositoryManager;

@Path("/logger")
public class StateLoggerService {

	static Logger logger = Logger.getLogger(StateLoggerService.class.getName());
	
	@GET
	@Path("/plan")
	@Produces(MediaType.APPLICATION_JSON)
	public RetrievalPlan getDemoPlan(){
		RetrievalPlan plan = new DemoRetrievalPlanFactory().createDemoRetrievalPlan();
		return plan;
	}
	
	@GET
	@Path("/plan/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public RetrievalPlan getPlan(@PathParam("id") String id){
		return RepositoryManager.getInstance().getPrevayler().prevalentSystem().getPlansMap().get(id);
	}
	
	@GET
	@Path("/pull/{ip}/{port}/{planId}/{pullerId}/{progress}")
	public Response logPullEvent(@PathParam("ip") String ip,
			@PathParam("port") String port, @PathParam("planId") String planId, @PathParam("pullerId") int pullerId, @PathParam("progress") int progress) {

		RepositoryManager.getInstance().logPullEvent(new PullEvent(ip, port, planId, pullerId, progress));
		
		return ok();
	}
	
	@GET
	@Path("/push/{ip}/{port}/{planId}/{pusherId}/{progress}")
	public Response logPushEvent(@PathParam("ip") String ip,
			@PathParam("port") String port, @PathParam("planId") String planId, @PathParam("pusherId") int pullerId, @PathParam("progress") int progress) {

		RepositoryManager.getInstance().logPushEvent(new PushEvent(ip, port, planId, pullerId, progress));
		
		return ok();
	}
		
	@GET
	@Path("/start/{ip}/{port}")
	public Response logStartUp(@PathParam("ip") String ip,
			@PathParam("port") String port) {

		RepositoryManager.getInstance().log(new StartUpEvent(ip, port));
		return ok();
	}
	
	@GET
	@Path("/stop/{ip}/{port}")
	public Response logShutDown(@PathParam("ip") String ip,
			@PathParam("port") String port) {

		RepositoryManager.getInstance().log(new ShutDownEvent(ip, port));
		return ok();
	}
	
	@GET
	@Path("/alive/{ip}/{port}")
	public Response logAlive(@PathParam("ip") String ip,
			@PathParam("port") String port) {

		RepositoryManager.getInstance().log(new AliveEvent(ip, port));
		return ok();
	}

	private Response ok() {
		return Response.status(200).entity("OK").build();
	}
}
