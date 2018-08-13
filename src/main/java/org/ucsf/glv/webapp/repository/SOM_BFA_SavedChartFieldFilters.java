package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface SOM_BFA_SavedChartFieldFilters {

    public List<HashMap<String, Object>> getFilterList(Connection connection, String userId, String deptId) throws SQLException;
    
    public List<HashMap<String, Object>> getDataForDllFilter(Connection connection, String filterId, String userId, String deptId) throws SQLException;
}
