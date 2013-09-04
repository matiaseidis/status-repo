package org.cachos.dimon.state.logger.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cachos.dimon.state.logger.repo.FileRepo;

@Path("/logger")
public class StateLoggerService {
	
	static Logger logger = Logger.getLogger(StateLoggerService.class.getName());
	
	@GET
	@Path("/{eventType}/{comment}")
	public Response getMsg(@PathParam("eventType") String eventType, @PathParam("comment") String comment) {
 
		String output = eventType + " - " + comment;
		logger.info("about to append line to db file: "+output);
		FileRepo.getInstance().append(output);
 
		return Response.status(200).entity(output).build();
 
	}
}
