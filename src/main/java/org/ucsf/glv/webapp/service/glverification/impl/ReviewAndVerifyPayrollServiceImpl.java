package org.ucsf.glv.webapp.service.glverification.impl;

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
public class ReviewAndVerifyPayrollServiceImpl implements ReviewAndVerifyPayrollService {

    @Inject
    ReviewAndVerifyPayrollRepo reviewAndVerifyPayrollRepo;

    private ObjectMapper mapper;

    public ReviewAndVerifyPayrollServiceImpl() {
        mapper = new ObjectMapper();
    }

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
