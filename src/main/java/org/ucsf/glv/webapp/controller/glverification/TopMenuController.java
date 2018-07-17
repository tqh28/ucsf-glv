package org.ucsf.glv.webapp.controller.glverification;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.service.glverification.TopMenuService;

@Path("gl-verification")
public class TopMenuController {
    
    @Inject
    private TopMenuService topMenuService;

    @GET
    @Path("get-child-dept-list")
    public String getChildDeptList(@QueryParam("sessionUserId") String sessionUserId,
            @QueryParam("deptId") String deptId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return topMenuService.getTopMenuData(sessionUserId, deptId);
    }
}
