package org.ucsf.glv.webapp.repository.glverification.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyTransactionsRepo;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class ReviewAndVerifyTransactionsRepoImpl implements ReviewAndVerifyTransactionsRepo {

    @Inject
    private Jdbc jdbc;

    @Inject
    private ConvertData convertData;

    public List<HashMap<String, Object>> getTransactionsData(String sessionUserId, String reconGroupTitle)
            throws SQLException {

        String sql;
        PreparedStatement prepareStatement;

        if (reconGroupTitle.equals("Total")) {
            sql = "SELECT ReconItemCd, ReconGroupTitle, ReconItemTitle, NotVerified, Pending,Complete, AutoComplete, PriorNotVerified, PriorPending, NotVerifiedCount, PendingCount, CompleteCount, AutoCompleteCount, PriorNotVerifiedCount, PriorPendingCount, AmtM01x, AmtM02x, AmtM03x, AmtM04x, AmtM05x, AmtM06x, AmtM07x, AmtM08x, AmtM09x, AmtM10x, AmtM11x, AmtM12x, AmtTotx FROM SOM_AA_TransactionSummary WHERE SessionUserid=? ORDER BY Sort1 asc, Sort2 ASC";
            prepareStatement = jdbc.getPrepareStatement(sql);
            prepareStatement.setString(1, sessionUserId);
        } else {
            sql = "SELECT ReconItemCd, ReconGroupTitle, ReconItemTitle, NotVerified, Pending,Complete, AutoComplete, PriorNotVerified, PriorPending, NotVerifiedCount, PendingCount, CompleteCount, AutoCompleteCount, PriorNotVerifiedCount, PriorPendingCount, AmtM01x, AmtM02x, AmtM03x, AmtM04x, AmtM05x, AmtM06x, AmtM07x, AmtM08x, AmtM09x, AmtM10x, AmtM11x, AmtM12x, AmtTotx FROM SOM_AA_TransactionSummary WHERE SessionUserid=? AND ReconGroupTitle=? ORDER BY Sort1 ASC, Sort2 ASC";
            prepareStatement = jdbc.getPrepareStatement(sql);
            prepareStatement.setString(1, sessionUserId);
            prepareStatement.setString(2, reconGroupTitle);
        }

        ResultSet rs = prepareStatement.executeQuery();

        List<HashMap<String, Object>> json = convertData.convertResultSetToListHashMap(rs);

        rs.close();
        prepareStatement.close();

        return json;
    }
}
