package org.cachos.dimon.state.logger.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.AliveEvent;
import org.cachos.dimon.state.logger.event.PullEvent;
import org.cachos.dimon.state.logger.event.PushEvent;
import org.cachos.dimon.state.logger.event.ShutDownEvent;
import org.cachos.dimon.state.logger.event.StartUpEvent;
import org.cachos.dimon.state.logger.repo.RepositoryManager;

@Path("/logger")
public class StateLoggerService {

	static Logger logger = Logger.getLogger(StateLoggerService.class.getName());

	@GET
	@Path("/{ip}/{port}")
	public Response logStartUp(@PathParam("ip") String ip,
			@PathParam("port") String port) {

		RepositoryManager.getInstance().log(new StartUpEvent(ip, port));
		int size = RepositoryManager.getInstance().getPrevayler().prevalentSystem().getEvents().get(StartUpEvent.class.getSimpleName()).size();
		
		return Response.status(200).entity("OK-"+size).build();
	}

//	@GET
//	@Path("/{ip}/{port}")
//	public Response logShutDown(@PathParam("ip") String ip,
//			@PathParam("port") String port) {
//
//		RepositoryManager.getInstance().log(new ShutDownEvent(ip, port));
//		return Response.status(200).entity("OK").build();
//	}
//
//	@GET
//	@Path("/{ip}/{port}")
//	public Response logAlive(@PathParam("ip") String ip,
//			@PathParam("port") String port) {
//
//		RepositoryManager.getInstance().log(new AliveEvent(ip, port));
//		return Response.status(200).entity("OK").build();
//	}
//
//	@GET
//	@Path("/{ip}/{port}")
//	public Response logPush(@PathParam("ip") String ip,
//			@PathParam("port") String port) {
//
//		RepositoryManager.getInstance().log(new PushEvent(ip, port));
//		return Response.status(200).entity("OK").build();
//	}
//
//	@GET
//	@Path("/{ip}/{port}")
//	public Response logPull(@PathParam("ip") String ip,
//			@PathParam("port") String port) {
//
//		RepositoryManager.getInstance().log(new PullEvent(ip, port));
//		return Response.status(200).entity("OK").build();
//	}

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
