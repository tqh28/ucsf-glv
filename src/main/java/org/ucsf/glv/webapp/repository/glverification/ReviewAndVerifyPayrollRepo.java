package org.ucsf.glv.webapp.repository.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface ReviewAndVerifyPayrollRepo {

    public String getPayrollData(String deptId, String businessUnit, String fiscalYear, String fiscalMonth)
            throws SQLException;

    public String getPayrollFTEData(String sessionUserId, int fiscalYear) throws SQLException;

    public String getPayrollExpenseData(String sessionUserId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

}
