package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_COA_SOM_ProjectUses {

    public List<HashMap<String, Object>> getProjectUseList() throws SQLException;

}