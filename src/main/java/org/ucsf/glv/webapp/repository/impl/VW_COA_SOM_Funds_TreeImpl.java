package org.ucsf.glv.webapp.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_Funds_Tree;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_COA_SOM_Funds_TreeImpl implements VW_COA_SOM_Funds_Tree {

    @Inject
    private Jdbc jdbc;

    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getFundList() throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT FundTreeTitleShort, FundCd FROM vw_COA_SOM_Funds_Tree ").append(
                "GROUP BY FundCd, FundTreeTitleShort, FundTreeCd HAVING FundCd Is Not Null ORDER BY FundTreeCd");

        Statement statement = jdbc.getStatement();
        ResultSet rs = statement.executeQuery(sql.toString());
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);

        rs.close();
        statement.close();

        return result;
    }

}
