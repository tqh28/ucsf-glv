package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.SOM_BFA_UserPreferences;
import org.ucsf.glv.webapp.repository.SOM_BFA_Variables;
import org.ucsf.glv.webapp.repository.vw_COA_SOM_Departments;

import com.google.inject.Inject;

public class TopMenuService {

    @Inject
    private SOM_BFA_UserPreferences userPreferences;

    @Inject
    private vw_COA_SOM_Departments departments;

    @Inject
    private SOM_BFA_Variables variables;

    @Inject
    private ObjectMapper mapper;

    public String getTopMenuData(String sessionUserId, String deptId)
            throws JsonGenerationException, JsonMappingException, SQLException, IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("deptList", getDeptIdList(sessionUserId, deptId));
        map.put("reportDate", getReportDate());

        return mapper.writeValueAsString(map);
    }

    private HashMap<String, Object> getDeptIdList(String sessionUserId, String urlDeptId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        String deptId = null;
        String defaultDeptId = userPreferences.getDefaultDeptIdByUserId(sessionUserId);

        List<HashMap<String, Object>> listDepartments = departments.getListDepartmentByDeptId(defaultDeptId);

        if (urlDeptId == null || urlDeptId.equals("")) {
            deptId = defaultDeptId;
        } else {
            deptId = urlDeptId;
            if (!listContainValue(listDepartments, urlDeptId)) {
                HashMap<String, Object> newUrlDeptId = new HashMap<>();
                newUrlDeptId.put("DeptTreeTitleAbbrev", "");
                newUrlDeptId.put("DeptCd", urlDeptId);
                listDepartments.add(newUrlDeptId);
            }
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("defaultDeptId", deptId);
        result.put("depts", listDepartments);

        return result;
    }

    private boolean listContainValue(List<HashMap<String, Object>> list, String value) {
        for (HashMap<String, Object> map : list) {
            String deptCd = (String) map.get("DeptCd");
            if (deptCd.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private String getReportDate() throws SQLException {
        int fp = variables.getFPFYDefault("DefaultFPMax");
        int fy = variables.getFPFYDefault("DefaultFY");

        if (fp < 7) {
            fp += 6;
            fy -= 1;
        } else {
            fp -= 6;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, fp - 1); // month ranger from 0 to 11

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
        simpleDateFormat.setCalendar(calendar);
        String monthName = simpleDateFormat.format(calendar.getTime());

        StringBuilder date = new StringBuilder(monthName).append(" ").append(fy);
        return date.toString();
    }
}
