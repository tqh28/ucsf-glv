package org.ucsf.glv.webapp.controller.glverification;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.service.glverification.EditMyFilterService;

@Path("gl-verification/edit-my-filter")
public class EditMyFilterController {

    @Inject
    private EditMyFilterService editMyFilterService;
    
    @GET
    @Path("get-filter-list")
    public String getFilterList(@QueryParam("userId") String userId, @QueryParam("deptId") String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return editMyFilterService.getFilterList(userId, deptId);
    }
}
