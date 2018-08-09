package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_SOM_BFA_SavedChartFieldFilters {

    public List<HashMap<String, Object>> getDataForGetWhereSavedFilter(String userId, String filterName, String deptCdSaved,
            String deptCdOverride) throws SQLException;

}
