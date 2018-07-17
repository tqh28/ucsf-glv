package org.ucsf.glv.webapp.controller.glverification;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.service.glverification.DashboardService;

@Path("gl-verification/dashboard")
public class DashboardController {

    @Inject
    private DashboardService dashboardService;

    @GET
    @Path("get-dashboard-data")
    public String getDashboardData(@QueryParam("sessionUserId") String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return dashboardService.getDashboardData(sessionUserId);
    }

    @GET
    @Path("get-monthly-trend-percent")
    public String getMonthlyTrendPercent(@QueryParam("deptId") String deptId, @QueryParam("fy") int fy,
            @QueryParam("fp") int fp) throws JsonGenerationException, JsonMappingException, SQLException, IOException {
        return dashboardService.getMonthlyTrendPercent(deptId, fy, fp);
    }
}
