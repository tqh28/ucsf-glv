package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface vw_Get_Deparments {

    public List<HashMap<String, Object>> getListRollUpByDeptId(String deptId) throws SQLException;

}
