package org.ucsf.glv.webapp.controller.glverification;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyPayrollService;


@Path("gl-verification/review-and-verify-payroll")
public class ReviewAndVerifyPayrollController {

    @Inject
    private ReviewAndVerifyPayrollService reviewAndVerifyPayrollService;
    
    @Path("get-payroll-data")
    @GET
    public String getPayrollData(@QueryParam("deptId") String deptId, @QueryParam("businessUnit") String businessUnit,
            @QueryParam("fy") String fy, @QueryParam("fp") String fp) throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return reviewAndVerifyPayrollService.getPayrollData(deptId, businessUnit, fy, fp);
    }

    @Path("get-payroll-fet-data")
    @GET
    public String getPayrollFETData(@QueryParam("userId") String userId, @QueryParam("fy") int fy)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return reviewAndVerifyPayrollService.getPayrollFTEData(userId, fy);
    }

    @Path("get-payroll-expense-data")
    @GET
    public String getPayrollExpenseData(@QueryParam("userId") String userId,
            @QueryParam("empName") String empName, @QueryParam("start") int start, @QueryParam("length") int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return reviewAndVerifyPayrollService.getPayrollExpenseData(userId, empName, start, length);
    }
}
