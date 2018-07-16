package org.ucsf.glv.webapp.controller.glverification;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyTransactionsService;

@Path("gl-verification/review-and-verify-transactions")
public class ReviewAndVerifyTransactionsController {

    @Inject
    private ReviewAndVerifyTransactionsService reviewAndVerifyTransactionsService;

    @GET
    @Path("get-transactions-data")
    public String getTransactionsData(@QueryParam("sessionUserId") String sessionUserId,
            @QueryParam("reconGroupTitle") String reconGroupTitle)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return reviewAndVerifyTransactionsService.getTransactionsData(sessionUserId, reconGroupTitle);
    }
}
