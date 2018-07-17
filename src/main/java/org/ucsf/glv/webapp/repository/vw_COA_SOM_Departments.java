package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface vw_COA_SOM_Departments {

    List<HashMap<String, Object>> getControlPointList() throws SQLException;

}
