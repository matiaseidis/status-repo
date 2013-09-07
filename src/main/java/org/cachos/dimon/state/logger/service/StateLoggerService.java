package org.cachos.dimon.state.logger.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.plan.Puller;
import org.cachos.dimon.state.logger.plan.Pusher;
import org.cachos.dimon.state.logger.plan.RetrievalPlan;
import org.cachos.dimon.state.logger.repo.RepositoryManager;

@Path("/logger")
public class StateLoggerService {

	static Logger logger = Logger.getLogger(StateLoggerService.class.getName());
	
	static int progress = 0;

	@GET
	@Path("/plan")
	@Produces(MediaType.APPLICATION_JSON)
	public RetrievalPlan getPlan(){
		
		RetrievalPlan plan = new RetrievalPlan();
		plan.setId(1L);
		plan.setPushers(pushers());
		plan.setPuller(puller());
		
		return plan;
	}

	private Puller puller() {
		Puller p = new Puller("9.8.7.6","0000");
		p.setId(1);
		p.setProgress(progress);
		
		return p;
	}

	private List<Pusher> pushers() {
		int pushers = 12;
		List<Pusher> result = new ArrayList<Pusher>();
		for(int i = 1; i <= pushers; i++){
			Pusher p = new Pusher(""+i+".1.1.1", "9876");
			p.setProgress(progress);
			p.setId(i);
			result.add(p);
		}
		if(progress >= 100) {
			progress = 0;
		} else {
//			progress++;
			progress+=6;
		}
		return result;
	}

	@GET
	@Path("/{ip}/{port}")
	public Response logStartUp(@PathParam("ip") String ip,
			@PathParam("port") String port) {

		RepositoryManager.getInstance().log(new StartUpEvent(ip, port));
		int size = RepositoryManager.getInstance().getPrevayler()
				.prevalentSystem().getEvents(StartUpEvent.class).size();

		return Response.status(200).entity("OK-" + size).build();
	}

	// @GET
	// @Path("/{ip}/{port}")
	// public Response logShutDown(@PathParam("ip") String ip,
	// @PathParam("port") String port) {
	//
	// RepositoryManager.getInstance().log(new ShutDownEvent(ip, port));
	// return Response.status(200).entity("OK").build();
	// }
	//
	// @GET
	// @Path("/{ip}/{port}")
	// public Response logAlive(@PathParam("ip") String ip,
	// @PathParam("port") String port) {
	//
	// RepositoryManager.getInstance().log(new AliveEvent(ip, port));
	// return Response.status(200).entity("OK").build();
	// }
	//
	// @GET
	// @Path("/{ip}/{port}")
	// public Response logPush(@PathParam("ip") String ip,
	// @PathParam("port") String port) {
	//
	// RepositoryManager.getInstance().log(new PushEvent(ip, port));
	// return Response.status(200).entity("OK").build();
	// }
	//
	// @GET
	// @Path("/{ip}/{port}")
	// public Response logPull(@PathParam("ip") String ip,
	// @PathParam("port") String port) {
	//
	// RepositoryManager.getInstance().log(new PullEvent(ip, port));
	// return Response.status(200).entity("OK").build();
	// }

	// @GET
	// @Path("/{eventType}/{ip}/{port}/{comment}")
	// public Response log(
	// @PathParam("eventType") String eventType,
	// @PathParam("comment") String comment,
	// @PathParam("ip") String ip,
	// @PathParam("port") String port
	// ) {
	//
	// ClientEvent event = new ClientEvent(eventType, ip, port, comment);
	// FileRepo.getInstance().log(event);
	// return Response.status(200).entity("OK").build();
	//
	// }
}
