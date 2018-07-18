package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface SOM_BFA_SavedChartFieldFilters {

    public List<HashMap<String, Object>> getFilterList(String userId, String deptId) throws SQLException;
}
