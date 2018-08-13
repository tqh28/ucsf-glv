package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_SavedChartFieldFilters;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_Funds_Tree;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_ProjectUses;
import org.ucsf.glv.webapp.repository.VW_SOM_BFA_ReconGroups;

import com.google.inject.Inject;

public class EditMyFilterService {

    @Inject
    private Jdbc jdbc;

    @Inject
    private ObjectMapper mapper;

    @Inject
    private SOM_BFA_SavedChartFieldFilters savedChartFieldFilters;

    @Inject
    private VW_SOM_BFA_ReconGroups reconGroups;

    @Inject
    private VW_COA_SOM_Funds_Tree fundsTree;

    @Inject
    private VW_COA_SOM_ProjectUses projectUsers;

    public String getFilterList(String userId, String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = savedChartFieldFilters.getFilterList(connection, userId, deptId);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        
        return mapper.writeValueAsString(res);
    }

    public String getFilterData(String filterId, String userId, String deptId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> result = new LinkedList<>();
        try {
            List<HashMap<String, Object>> filterData = savedChartFieldFilters.getDataForDllFilter(connection, filterId, userId, deptId);

            for (HashMap<String, Object> data : filterData) {
                String chartStrField = (String) data.get("ChartStrField");
                if (!chartStrField.equals("DeptCdSaved")) {
                    String chartStrValue = (String) data.get("ChartStrValue");
                    String except = (String) data.get("Except");

                    String projectMgrId = "";
                    if (chartStrField.equals("ProjectManagerCd")) {
                        projectMgrId = reconGroups.getProjectMgrCdByProjectMgr(connection, chartStrValue);
                    }
                    boolean notValue = false;
                    if (except.equals("-")) {
                        notValue = true;
                    }

                    HashMap<String, Object> element = new HashMap<>();
                    element.put("type", chartStrField);
                    element.put("value", chartStrValue);
                    element.put("notValue", notValue);
                    element.put("projectMgrId", projectMgrId);

                    result.add(element);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }

        return mapper.writeValueAsString(result);
    }

    public String getProjectList(String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = reconGroups.getProjectList(connection, deptId);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        
        return mapper.writeValueAsString(res);
    }

    public String getFundList() throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = fundsTree.getFundList(connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        
        return mapper.writeValueAsString(res);
    }

    public String getProjectMgrList(String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = reconGroups.getProjectMgrList(connection, deptId);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        
        return mapper.writeValueAsString(res);
    }

    public String getProjectUseList() throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = projectUsers.getProjectUseList(connection);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        
        return mapper.writeValueAsString(res);
    }
}
