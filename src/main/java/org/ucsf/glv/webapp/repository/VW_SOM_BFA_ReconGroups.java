package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface VW_SOM_BFA_ReconGroups {

    public String getProjectMgrCdByProjectMgr(String projectMgr) throws SQLException;

    public List<HashMap<String, Object>> getProjectList(String deptId) throws SQLException;

    public List<HashMap<String, Object>> getProjectMgrList(String deptId) throws SQLException;
}
