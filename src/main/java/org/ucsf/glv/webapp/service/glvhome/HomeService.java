package org.ucsf.glv.webapp.service.glvhome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_UserPreferences;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_Departments;
import org.ucsf.glv.webapp.repository.VW_Get_Deparments;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HomeService {

    @Inject
    private Jdbc jdbc;

    @Inject
    private VW_COA_SOM_Departments departmentsView;

    @Inject
    private SOM_BFA_UserPreferences userPreference;

    @Inject
    private VW_Get_Deparments getDepartmentsView;

    @Inject
    private ObjectMapper mapper;

    public String getControlPointList()
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res = null;
        try {
            res = departmentsView.getControlPointList(connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        return mapper.writeValueAsString(res);
    }

    public String getListRollUpData(String userId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> departmentList = null;
        try {
            List<HashMap<String, Object>> userPreferenceDataList = userPreference.getPreferenceByUserId(connection, userId);
            String controlPointDefault = null;
            for (HashMap<String, Object> data : userPreferenceDataList) {
                if (data.get("Preference") != null && data.get("Preference").equals("Default ControlPoint")) {
                    controlPointDefault = (String) data.get("String");
                }
            }

            departmentList = getDepartmentsView.getListRollUpByDeptId(connection, controlPointDefault);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        return mapper.writeValueAsString(departmentList);
    }

    public String getDefaultDeptData(String userId) throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        HashMap<String, String> deptData = null;
        try {
            List<HashMap<String, Object>> userPreferenceDataList = userPreference.getPreferenceByUserId(connection, userId);
    
            String defaultDeptData = null;
            for (HashMap<String, Object> data : userPreferenceDataList) {
                if (data.get("Preference") != null && data.get("Preference").equals("Default Deptid")) {
                    defaultDeptData = (String) data.get("String");
                }
            }
            
            deptData = new HashMap<>();
            deptData.put("defaultDeptId", defaultDeptData);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        return mapper.writeValueAsString(deptData);
    }

}
