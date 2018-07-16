package org.ucsf.glv.webapp.service.glvhome;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.glvhome.HomeRepo;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HomeService {
    
    @Inject
    private HomeRepo homeRepo;
    
    @Inject
    private ObjectMapper mapper;

    public String getControlPointList() throws JsonGenerationException, JsonMappingException, IOException, SQLException {
        return mapper.writeValueAsString(homeRepo.getControlPointList());
    }

}
