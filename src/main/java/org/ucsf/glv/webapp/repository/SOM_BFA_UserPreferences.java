package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface SOM_BFA_UserPreferences {

    public List<HashMap<String, Object>> getPreferenceByUserId(String userId) throws SQLException;
    
    public String getDefaultDeptIdByUserId(String userId) throws SQLException;
}
