package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconEmployeeGLV;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ReviewAndVerifyPayrollService {

    @Inject
    private Jdbc jdbc;

    @Inject
    private SOM_BFA_ReconEmployeeGLV reconEmployee;

    @Inject
    private ObjectMapper mapper;

    public String getPayrollData(String deptId, String businessUnit, String fiscalYear, String fiscalMonth)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = reconEmployee.getVerifyPayroll(connection, deptId, businessUnit, fiscalYear, fiscalMonth);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        
        return mapper.writeValueAsString(res);
    }

    public String getPayrollFTEData(String userId, int fiscalYear)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = reconEmployee.getListCategorySumary(connection, userId, fiscalYear);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        return mapper.writeValueAsString(res);
    }

    public String getPayrollExpenseData(String userId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        HashMap<String, Object> resultMap;
        
        try {
            List<HashMap<String, Object>> data = reconEmployee.getExpenseDetail(connection, userId, empName, start, length);
            int totalRecords = reconEmployee.countExpenseDetail(connection, userId, empName, start, length);
            
            resultMap = new HashMap<String, Object>();
            resultMap.put("data", data);
            resultMap.put("recordsTotal", totalRecords);
            resultMap.put("recordsFiltered", totalRecords);
            
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }

        return mapper.writeValueAsString(resultMap);
    }
}
