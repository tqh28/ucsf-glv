package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_COA_SOM_Departments {

    public List<HashMap<String, Object>> getControlPointList(Connection connection) throws SQLException;
    
    public List<HashMap<String, Object>> getListDepartmentByDeptId(Connection connection, String deptId) throws SQLException;

    public Integer getDeptLevelByDeptCd (Connection connection, String deptCd) throws SQLException;
}
