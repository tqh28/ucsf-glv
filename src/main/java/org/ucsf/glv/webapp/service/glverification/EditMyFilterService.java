package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.SOM_BFA_SavedChartFieldFilters;
import org.ucsf.glv.webapp.repository.vw_SOM_BFA_ReconGroups;

import com.google.inject.Inject;

public class EditMyFilterService {

    @Inject
    private ObjectMapper mapper;

    @Inject
    private SOM_BFA_SavedChartFieldFilters savedChartFieldFilters;

    @Inject
    private vw_SOM_BFA_ReconGroups reconGroups;

    public String getFilterList(String userId, String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return mapper.writeValueAsString(savedChartFieldFilters.getFilterList(userId, deptId));
    }

    public String getFilterData(String filterId, String userId, String deptId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        List<HashMap<String, Object>> filterData = savedChartFieldFilters.getDataForDllFilter(filterId, userId, deptId);
        List<HashMap<String, Object>> result = new LinkedList<>();

        for (HashMap<String, Object> data : filterData) {
            String chartStrField = (String) data.get("ChartStrField");
            if (!chartStrField.equals("DeptCdSaved")) {
                String chartStrValue = (String) data.get("ChartStrValue");
                String except = (String) data.get("Except");

                String projectMgrId = reconGroups.getProjectMgrCdByProjectMgr(chartStrValue);
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

        return mapper.writeValueAsString(result);
    }
}
