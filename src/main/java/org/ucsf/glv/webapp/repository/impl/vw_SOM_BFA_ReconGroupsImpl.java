package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.vw_SOM_BFA_ReconGroups;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class vw_SOM_BFA_ReconGroupsImpl implements vw_SOM_BFA_ReconGroups {

    @Inject
    private ConvertData convertData;

    @Inject
    private Jdbc jdbc;

    @Override
    public String getProjectMgrCdByProjectMgr(String projectMgr) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ProjectManagerCd FROM vw_SOM_BFA_ReconGroups ")
                .append("WHERE ProjectManager = ? GROUP BY ProjectManager, ProjectManagerCd");
        PreparedStatement preparedStatement = jdbc.getPrepareStatement(sql.toString());
        preparedStatement.setString(1, projectMgr);

        ResultSet rs = preparedStatement.executeQuery();
        String projectManagerCd = (String) convertData.getObjectFromResultSet(rs);

        rs.close();
        preparedStatement.close();

        return projectManagerCd;
    }

}
