package org.cachos.dimon.state.logger.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.event.DimonEvent;
import org.cachos.dimon.state.logger.repo.FileRepo;

@Path("/logger")
public class StateLoggerService {
	
	static Logger logger = Logger.getLogger(StateLoggerService.class.getName());
	
	@GET
	@Path("/{eventType}/{ip}/{port}/{comment}")
	public Response log(
			@PathParam("eventType") String eventType, 
			@PathParam("comment") String comment,
			@PathParam("ip") String ip,
			@PathParam("port") String port
			) {
 
		DimonEvent event = new DimonEvent(eventType, ip, port, comment);
		FileRepo.getInstance().log(event);
		return Response.status(200).entity("OK").build();
 
	}
}
