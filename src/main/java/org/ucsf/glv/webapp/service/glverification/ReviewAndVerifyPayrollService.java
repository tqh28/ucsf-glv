package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface ReviewAndVerifyPayrollService {

    public String getPayrollData(String deptId, String businessUnit, String fiscalYear, String fiscalMonth)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

    public String getPayrollFTEData(String sessionUserId, int fiscalYear)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

    public String getPayrollExpenseData(String sessionUserId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;
}
