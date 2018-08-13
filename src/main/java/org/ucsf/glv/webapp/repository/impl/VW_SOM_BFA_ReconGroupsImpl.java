package org.ucsf.glv.webapp.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.repository.VW_SOM_BFA_ReconGroups;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_SOM_BFA_ReconGroupsImpl implements VW_SOM_BFA_ReconGroups {

    @Inject
    private ConvertData convertData;

    @Override
    public String getProjectMgrCdByProjectMgr(Connection connection, String projectMgr) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ProjectManagerCd FROM vw_SOM_BFA_ReconGroups ")
                .append("WHERE ProjectManager = ? GROUP BY ProjectManager, ProjectManagerCd");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, projectMgr);

        ResultSet rs = preparedStatement.executeQuery();
        String projectManagerCd = (String) convertData.getObjectByColumnNameFromResultSet(rs, "ProjectManagerCd");
        rs.close();
        preparedStatement.close();
        return projectManagerCd;
    }

    @Override
    public List<HashMap<String, Object>> getProjectList(Connection connection, String deptId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ProjectTitleCd, ProjectCd ")
                .append("FROM vw_SOM_BFA_ReconGroups ")
                .append("WHERE ReconDeptCd = ? ")
                .append("GROUP BY ProjectCd, ProjectTitleCd, ProjectUseShort ")
                .append("HAVING ProjectCd <> '-------' ")
                .append("ORDER BY ProjectCd");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, deptId);

        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
        
    }

    @Override
    public List<HashMap<String, Object>> getProjectMgrList(Connection connection, String deptId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ProjectManager, ProjectManagerCd ")
                .append("FROM vw_SOM_BFA_ReconGroups ")
                .append("WHERE ReconDeptCd = ? ")
                .append("GROUP BY ProjectManager, ProjectManagerCd");
        
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, deptId);
        
        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
    }

}
