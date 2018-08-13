package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_SOM_BFA_ReconGroups {

    public String getProjectMgrCdByProjectMgr(Connection connection, String projectMgr) throws SQLException;

    public List<HashMap<String, Object>> getProjectList(Connection connection, String deptId) throws SQLException;

    public List<HashMap<String, Object>> getProjectMgrList(Connection connection, String deptId) throws SQLException;
}
