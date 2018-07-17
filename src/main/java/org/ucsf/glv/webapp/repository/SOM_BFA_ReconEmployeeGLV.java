package org.ucsf.glv.webapp.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface SOM_BFA_ReconEmployeeGLV {

    public List<HashMap<String, Object>> getVerifyPayroll(String deptId, String businessUnit, String fiscalYear,
            String fiscalMonth) throws SQLException;

    public List<HashMap<String, Object>> getListCategorySumary(String sessionUserId, int fiscalYear)
            throws SQLException;

    public List<HashMap<String, Object>> getExpenseDetail(String sessionUserId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;
    
    public int countExpenseDetail(String sessionUserId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

}
