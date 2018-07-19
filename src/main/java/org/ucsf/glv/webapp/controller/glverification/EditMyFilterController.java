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

    @GET
    @Path("get-filter-data")
    public String getFilterData(@QueryParam("filterId") String filterId, @QueryParam("userId") String userId,
            @QueryParam("deptId") String deptId)
            throws JsonGenerationException, JsonMappingException, SQLException, IOException {
        return editMyFilterService.getFilterData(filterId, userId, deptId);
    }

    @GET
    @Path("get-project-list")
    public String getProjectList(@QueryParam("deptId") String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return editMyFilterService.getProjectList(deptId);
    }

    @GET
    @Path("get-fund-list")
    public String getFundList() throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return editMyFilterService.getFundList();
    }

    @GET
    @Path("get-project-mgr-list")
    public String getProjectMgrList(@QueryParam("deptId") String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return editMyFilterService.getProjectMgrList(deptId);
    }

    @GET
    @Path("get-project-use-list")
    public String getProjectUserList() throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return editMyFilterService.getProjectUseList();
    }
}
