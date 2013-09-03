package org.cachos.dimon.state.logger.services;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;

@Path("/logger")
public class StateLoggerService {
	
	@GET
	@Path("/{eventType}/{comment}")
	public Response getMsg(@PathParam("eventType") String eventType, @PathParam("comment") String comment) {
 
		String output = "Jersey say : " + eventType + " - " + comment;

		
 
		return Response.status(200).entity(output).build();
 
	}
}
