package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.SOM_BFA_SavedChartFieldFilters;

import com.google.inject.Inject;

public class EditMyFilterService {

    @Inject
    private ObjectMapper mapper;

    @Inject
    private SOM_BFA_SavedChartFieldFilters savedChartFieldFilters;

    public String getFilterList(String userId, String deptId)
            throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return mapper.writeValueAsString(savedChartFieldFilters.getFilterList(userId, deptId));
    }
}
