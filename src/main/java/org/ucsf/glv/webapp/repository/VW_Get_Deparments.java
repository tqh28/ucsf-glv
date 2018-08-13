package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_Get_Deparments {

    public List<HashMap<String, Object>> getListRollUpByDeptId(Connection connection, String deptId) throws SQLException;

}
