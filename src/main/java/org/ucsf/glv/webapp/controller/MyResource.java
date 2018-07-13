package org.ucsf.glv.webapp.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.service.SimpleService;
import org.ucsf.glv.webapp.service.glverification.DashboardService;


@Path("/resource")
public class MyResource {
	
	@Inject
	SimpleService simpleService;
	
	@Inject 
	DashboardService dService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return simpleService.getMessage();
    }
    
    @GET
    @Path("/test")
    public String getTets() throws JsonGenerationException, JsonMappingException, SQLException, IOException {
        return dService.getDashboardData("ucsfmanager@gmail.com");
    }
}