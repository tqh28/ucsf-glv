package org.ucsf.glv.webapp.repository.glverification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface ReviewAndVerifyPayrollRepo {

    public List<HashMap<String, Object>> getPayrollData(String deptId, String businessUnit, String fiscalYear,
            String fiscalMonth) throws SQLException;

    public List<HashMap<String, Object>> getPayrollFTEData(String sessionUserId, int fiscalYear) throws SQLException;

    public HashMap<String, Object> getPayrollExpenseData(String sessionUserId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

}
