package org.ucsf.glv.webapp.controller.glvhome;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.service.glvhome.HomeService;

@Path("glv-home")
public class HomeController {

    @Inject
    private HomeService homeService;

    @GET
    @Path("get-control-point-list")
    public String getControlPointList()
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return homeService.getControlPointList();
    }

}
