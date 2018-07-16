package org.ucsf.glv.webapp.service.glvhome;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.SOM_BFA_UserPreferences;
import org.ucsf.glv.webapp.repository.vw_Get_Deparments;
import org.ucsf.glv.webapp.repository.glvhome.HomeRepo;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HomeService {

    @Inject
    private HomeRepo homeRepo;

    @Inject
    private SOM_BFA_UserPreferences userPreference;
    
    @Inject
    private vw_Get_Deparments getDepartmentsView;

    @Inject
    private ObjectMapper mapper;

    public String getControlPointList()
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return mapper.writeValueAsString(homeRepo.getControlPointList());
    }

    public String getListRollUpData(String sessionUserId) throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        List<HashMap<String, Object>> userPreferenceDataList = userPreference.findByUserId(sessionUserId);
        
        String controlPointDefault = null;
        for (HashMap<String, Object> data: userPreferenceDataList) {
            if (data.get("Preference") != null && data.get("Preference").equals("Default ControlPoint")) {
                controlPointDefault = (String) data.get("String");
            }
        }
        
        List<HashMap<String, Object>> departmentList = getDepartmentsView.findListRollUpByDeptId(controlPointDefault);
        return mapper.writeValueAsString(departmentList);
    }

}
