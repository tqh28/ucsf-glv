package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconEmployeeGLV;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ReviewAndVerifyPayrollService {

    @Inject
    private SOM_BFA_ReconEmployeeGLV reconEmployee;

    @Inject
    private ObjectMapper mapper;

    public String getPayrollData(String deptId, String businessUnit, String fiscalYear, String fiscalMonth)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(
                reconEmployee.getVerifyPayroll(deptId, businessUnit, fiscalYear, fiscalMonth));
    }

    public String getPayrollFTEData(String userId, int fiscalYear)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(reconEmployee.getListCategorySumary(userId, fiscalYear));
    }

    public String getPayrollExpenseData(String userId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        List<HashMap<String, Object>> data = reconEmployee.getExpenseDetail(userId, empName, start, length);
        int totalRecords = reconEmployee.countExpenseDetail(userId, empName, start, length);
        
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("data", data);
        resultMap.put("recordsTotal", totalRecords);
        resultMap.put("recordsFiltered", totalRecords);
        
        return mapper.writeValueAsString(resultMap);
    }
}
