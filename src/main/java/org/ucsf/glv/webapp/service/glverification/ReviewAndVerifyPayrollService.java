package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyPayrollRepo;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyPayrollService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ReviewAndVerifyPayrollService {

    @Inject
    private ReviewAndVerifyPayrollRepo reviewAndVerifyPayrollRepo;

    @Inject
    private ObjectMapper mapper;

    public String getPayrollData(String deptId, String businessUnit, String fiscalYear, String fiscalMonth)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(
                reviewAndVerifyPayrollRepo.getPayrollData(deptId, businessUnit, fiscalYear, fiscalMonth));
    }

    public String getPayrollFTEData(String sessionUserId, int fiscalYear)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(reviewAndVerifyPayrollRepo.getPayrollFTEData(sessionUserId, fiscalYear));
    }

    public String getPayrollExpenseData(String sessionUserId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(
                reviewAndVerifyPayrollRepo.getPayrollExpenseData(sessionUserId, empName, start, length));
    }
}
