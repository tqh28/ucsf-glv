package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_DeptID {

    public List<HashMap<String, Object>> getByDeptCdAndFyAndFp(Connection connection, String deptCd, int fy, int fp) throws SQLException;

}
