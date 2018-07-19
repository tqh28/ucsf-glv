package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_COA_SOM_Funds_Tree {

    public List<HashMap<String, Object>> getFundList() throws SQLException;
}
