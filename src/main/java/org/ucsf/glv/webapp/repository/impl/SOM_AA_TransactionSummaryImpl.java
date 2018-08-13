package org.ucsf.glv.webapp.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class SOM_AA_TransactionSummaryImpl implements SOM_AA_TransactionSummary {

    @Inject
    private ConvertData convertData;

    public List<HashMap<String, Object>> getReviewAndVerifyTransactions(Connection connection, String userId, String reconGroupTitle)
            throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ReconItemCd, ReconGroupTitle, ReconItemTitle, NotVerified, Pending,Complete, AutoComplete, PriorNotVerified, PriorPending, NotVerifiedCount, PendingCount, CompleteCount, AutoCompleteCount, PriorNotVerifiedCount, PriorPendingCount, AmtM01x, AmtM02x, AmtM03x, AmtM04x, AmtM05x, AmtM06x, AmtM07x, AmtM08x, AmtM09x, AmtM10x, AmtM11x, AmtM12x, AmtTotx ")
                .append("FROM SOM_AA_TransactionSummary ");
        PreparedStatement prepareStatement;

        if (reconGroupTitle.equals("Total")) {
            sql.append("WHERE SessionUserid=? ORDER BY Sort1 asc, Sort2 ASC");
            prepareStatement = connection.prepareStatement(sql.toString());
            prepareStatement.setString(1, userId);
        } else {
            sql.append("WHERE SessionUserid=? AND ReconGroupTitle=? ORDER BY Sort1 ASC, Sort2 ASC");
            prepareStatement = connection.prepareStatement(sql.toString());
            prepareStatement.setString(1, userId);
            prepareStatement.setString(2, reconGroupTitle);
        }
        ResultSet rs = prepareStatement.executeQuery();
        List<HashMap<String, Object>> json = convertData.convertResultSetToListHashMap(rs);

        rs.close();
        prepareStatement.close();
        return json;
    }

    @Override
    public void deleteBySessionUserId(Connection connection, String sessionUserId) throws SQLException {
        String sql = "DELETE FROM SOM_AA_TransactionSummary WHERE SessionUserid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sessionUserId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void insertData(Connection connection, String query, String sessionUserId, Timestamp sesstionTime) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, sessionUserId);
        statement.setTimestamp(2, sesstionTime);
        statement.executeUpdate();
        statement.close();
    }
}
