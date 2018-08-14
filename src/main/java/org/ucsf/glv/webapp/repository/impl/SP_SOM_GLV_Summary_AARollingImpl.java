package org.ucsf.glv.webapp.repository.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

import org.ucsf.glv.webapp.repository.FN_SOM_BFA_GetWhereFromSavedFilter;
import org.ucsf.glv.webapp.repository.SOM_AA_Dashboard;
import org.ucsf.glv.webapp.repository.SOM_AA_EmployeeListRolling;
import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;
import org.ucsf.glv.webapp.repository.SP_SOM_GLV_Summary_AARolling;

import com.google.inject.Inject;

public class SP_SOM_GLV_Summary_AARollingImpl implements SP_SOM_GLV_Summary_AARolling {

    @Inject
    private SOM_AA_Dashboard dashboard;

    @Inject
    private SOM_AA_TransactionSummary transactionSummary;

    @Inject
    private SOM_AA_EmployeeListRolling employeeListRolling;
    
    @Inject
    private FN_SOM_BFA_GetWhereFromSavedFilter getWhereFromSavedFilter;

    @Override
    public void execute(Connection connection, String sessionUserId, String deptCdGLV, String deptCdOverride, String businessUnitCd,
            String deptSite, String userId, String filtername, int fy, int fp, int withEmp) throws SQLException {
        Calendar cal = Calendar.getInstance();  
        Timestamp sesstionTime = new Timestamp(cal.getTimeInMillis());
        
        Statement statement = connection.createStatement();
        
        dashboard.deleteBySessionUserId(connection, sessionUserId);
        transactionSummary.deleteBySessionUserId(connection, sessionUserId);
        employeeListRolling.deleteBySessionUserId(connection, sessionUserId);

        if (deptSite.equals("*")) {
            deptSite = "%";
        }

        String createTemp1Sql = "CREATE TABLE temp1 ( FiscalYear smallint, FiscalPeriod smallint,"
                + " ReconGroupTitle varchar(20), ReconItemCd smallint, ReconItemTitle varchar(50),"
                + " ReconAssignCd smallint, ReconStatusCd smallint, ReconStatusTitle varchar(50),"
                + " Amt money, Cnt Integer)";
        String createTemp2Sql = "CREATE TABLE temp2 ( "
                + "FiscalYear smallint, ReconGroupTitle varchar(20), ReconItemTitle varchar(50), ReconItemCd smallint, "
                + "StatusAmt0 money, StatusCnt0 Integer, StatusAmt1 money, StatusCnt1 Integer, StatusAmt2 money, StatusCnt2 Integer, StatusAmt3 money, StatusCnt3 Integer, "
                + "PriorStatusAmt0 money, PriorStatusCnt0 Integer, PriorStatusAmt1 money, PriorStatusCnt1 Integer, "
                + "AmtM01 money, AmtM02 money, AmtM03 money, AmtM04 money, AmtM05 money, AmtM06 money, AmtM07 money, AmtM08 money, AmtM09 money, AmtM10 money, AmtM11 money, AmtM12 money, "
                + "IncM01 integer, IncM02 integer, IncM03 integer, IncM04 integer, IncM05 integer, IncM06 integer, IncM07 integer, IncM08 integer, IncM09 integer, IncM10 integer, IncM11 integer, IncM12 integer)";
        statement.execute(createTemp1Sql);
        statement.execute(createTemp2Sql);
        
        /**
         * Insert into temp1
         */
        String whereCondition = getWhereFromSavedFilter.execute(connection, userId, deptCdGLV, deptCdOverride, filtername, 0);
        String insertIntoTemp1Query = "INSERT INTO temp1 "
                + "SELECT 0 AS FiscalYear, FiscalPeriod, ReconGroupTitle, ReconItemCd, ReconItemTitle, ReconAssignCd, ReconStatusCd, ReconStatusTitle, SUM(Amount) AS Amt, SUM(1) AS Cnt "
                + "  FROM vw_COA_Report_Ledger_Details "
                + "  WHERE (FiscalYear = ? AND FiscalPeriod BETWEEN ? AND 12 OR FiscalYear = ? AND FiscalPeriod BETWEEN 1 AND ?) "
                + "    AND BusinessUnitCd = ? "
                + "    AND AccountLevelACd IN ('4000A','5000A','5700A') " 
                + "    AND " + whereCondition
                + "  GROUP BY FiscalYear, FiscalPeriod, ReconGroupTitle, ReconItemTitle, ReconItemCd, ReconAssignCd, ReconStatusTitle, ReconStatusCd ";
        PreparedStatement insertIntoTemp1Statement = connection.prepareStatement(insertIntoTemp1Query);
        insertIntoTemp1Statement.setInt(1, fy - 1);
        insertIntoTemp1Statement.setInt(2, fp + 1);
        insertIntoTemp1Statement.setInt(3, fy);
        insertIntoTemp1Statement.setInt(4, fp);
        insertIntoTemp1Statement.setString(5, businessUnitCd);
        insertIntoTemp1Statement.executeUpdate();
        insertIntoTemp1Statement.close();
        
        /**
         * insert into temp2
         */
        String insertIntoTemp2Query = "INSERT INTO temp2 "
                + "SELECT FiscalYear, ReconGroupTitle, ReconItemTitle, ReconItemCd, "
                + "    SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod = ? THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod = ? THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod = ? THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod = ? THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 2000 AND FiscalPeriod = ? THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 2000 AND FiscalPeriod = ? THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 3000 AND FiscalPeriod = ? THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 3000 AND FiscalPeriod = ? THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod < ? THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 0 AND FiscalPeriod < ? THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod < ? THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd = 1000 AND FiscalPeriod < ? THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 11) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 10) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 9) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 8) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 7) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 6) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 5) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 4) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 3) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 2) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 1) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN FiscalPeriod = ((? + 0) % 12) + 1 THEN Amt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 11) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 10) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 9) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 8) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 7) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 6) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 5) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 4) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 3) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 2) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 1) % 12) + 1 THEN Cnt ELSE 0 END),"
                + "    SUM(CASE WHEN ReconStatusCd < 2000 AND FiscalPeriod = ((? + 0) % 12) + 1 THEN Cnt ELSE 0 END) "
                + "  FROM temp1 "
                + "  GROUP BY FiscalYear, ReconGroupTitle, ReconItemTitle, ReconItemCd ";
        PreparedStatement insertIntoTemp2Statement = connection.prepareStatement(insertIntoTemp2Query);
        for (int i = 1; i <= 36; i++) {
            insertIntoTemp2Statement.setInt(i, fp);
        }
        insertIntoTemp2Statement.executeUpdate();
        insertIntoTemp2Statement.close();
        
        String insertIntoDashboardQuery = "INSERT INTO SOM_AA_Dashboard ( " + 
                " SessionUserid, SessionDate, FiscalYear, ReconGroupTitle," + 
                " StatusAmt0, StatusCnt0, StatusAmt1, StatusCnt1, StatusAmt2, StatusCnt2, StatusAmt3, StatusCnt3," + 
                " PriorStatusAmt0, PriorStatusCnt0, PriorStatusAmt1, PriorStatusCnt1," + 
                " AmtM01, AmtM02, AmtM03, AmtM04, AmtM05, AmtM06, AmtM07, AmtM08, AmtM09, AmtM10, AmtM11, AmtM12," + 
                " IncM01, IncM02, IncM03, IncM04, IncM05, IncM06, IncM07, IncM08, IncM09, IncM10, IncM11, IncM12)" + 
                "    SELECT ?, ?, T1.FiscalYear, T1.ReconGroupTitle, " + 
                "      SUM(T1.StatusAmt0), SUM(T1.StatusCnt0), SUM(T1.StatusAmt1), SUM(T1.StatusCnt1), " + 
                "      SUM(T1.StatusAmt2), SUM(T1.StatusCnt2), SUM(T1.StatusAmt3), SUM(T1.StatusCnt3), " + 
                "      SUM(T1.PriorStatusAmt0), SUM(T1.PriorStatusCnt0), SUM(T1.PriorStatusAmt1), SUM(T1.PriorStatusCnt1), " + 
                "      SUM(T1.AmtM01), SUM(T1.AmtM02), SUM(T1.AmtM03), SUM(T1.AmtM04), SUM(T1.AmtM05), SUM(T1.AmtM06), " + 
                "      SUM(T1.AmtM07), SUM(T1.AmtM08), SUM(T1.AmtM09), SUM(T1.AmtM10), SUM(T1.AmtM11), SUM(T1.AmtM12), " + 
                "      SUM(T1.IncM01), SUM(T1.IncM02), SUM(T1.IncM03), SUM(T1.IncM04), SUM(T1.IncM05), SUM(T1.IncM06), " + 
                "      SUM(T1.IncM07), SUM(T1.IncM08), SUM(T1.IncM09), SUM(T1.IncM10), SUM(T1.IncM11), SUM(T1.IncM12) " + 
                "    FROM temp2 as T1" + 
                "    GROUP BY T1.FiscalYear, T1.ReconGroupTitle";
        dashboard.modifyByQuery(connection, insertIntoDashboardQuery, sessionUserId, sesstionTime);
        
        String insertIntoTransactionSummaryForDisplayDetailsQuery = "INSERT INTO SOM_AA_TransactionSummary ( " + 
                " SessionUserid, SessionDate, ReconGroupTitle, Sort1, Sort2, ReconItemTitle, ReconItemCd," + 
                " NotVerified, Pending, AutoComplete, Complete, PriorNotVerified, PriorNotVerifiedCount, PriorPending, PriorPendingCount," + 
                " AmtM01x, AmtM02x, AmtM03x, AmtM04x, AmtM05x, AmtM06x, AmtM07x, AmtM08x, AmtM09x, AmtM10x, AmtM11x, AmtM12x, AmtTotx," + 
                " NotVerifiedCount, PendingCount, AutoCompleteCount, CompleteCount)" + 
                "    SELECT ?, ?, T1.ReconGroupTitle, T1.ReconGroupTitle AS Sort1, 1 AS Sort2, T1.ReconItemTitle, T1.ReconItemCd, " + 
                "      T1.StatusAmt0, T1.StatusAmt1, T1.StatusAmt2, T1.StatusAmt3, T1.PriorStatusAmt0, T1.PriorStatusCnt0, T1.PriorStatusAmt1, T1.PriorStatusAmt1, " + 
                "      T1.AmtM01 AS AmtM01x, T1.AmtM02 AS AmtM02x, T1.AmtM03 AS AmtM03x, T1.AmtM04 AS AmtM04x, T1.AmtM05 AS AmtM05x, T1.AmtM06 AS AmtM06x, " + 
                "      T1.AmtM07 AS AmtM07x, T1.AmtM08 AS AmtM08x, T1.AmtM09 AS AmtM09x, T1.AmtM10 AS AmtM10x, T1.AmtM11 AS AmtM11x, T1.AmtM12 AS AmtM12x, " + 
                "      [AmtM01]+[AmtM02]+[AmtM03]+[AmtM04]+[AmtM05]+[AmtM06]+[AmtM07]+[AmtM08]+[AmtM09]+[AmtM10]+[AmtM11]+[AmtM12] AS AmtTotx, " + 
                "      T1.StatusCnt0, T1.StatusCnt1, T1.StatusCnt2, T1.StatusCnt3" + 
                "    FROM temp2 AS T1; ";
        transactionSummary.insertData(connection, insertIntoTransactionSummaryForDisplayDetailsQuery, sessionUserId, sesstionTime);
        
        String insertIntoTransactionSummaryForDisplaySubtotalQuery = "INSERT INTO SOM_AA_TransactionSummary ( " + 
                " SessionUserid, SessionDate, ReconGroupTitle, Sort1, Sort2, ReconItemTitle, ReconItemCd," + 
                " NotVerified, Pending, AutoComplete, Complete, PriorNotVerified, PriorPending," + 
                " AmtM01x, AmtM02x, AmtM03x, AmtM04x, AmtM05x, AmtM06x, AmtM07x, AmtM08x, AmtM09x, AmtM10x, AmtM11x, AmtM12x, AmtTotx," + 
                " NotVerifiedCount, PendingCount, AutoCompleteCount, CompleteCount )" + 
                "    SELECT ?, ?, T1.ReconGroupTitle, T1.ReconGroupTitle AS Sort1, 2 AS Sort2, 'Total' AS ReconItemTitle, 0 AS ReconItemCd, " + 
                "      SUM(T1.StatusAmt0) AS SumOfStatusAmt0, SUM(T1.StatusAmt1) AS SumOfStatusAmt1, SUM(T1.StatusAmt2) AS SumOfStatusAmt21, SUM(T1.StatusAmt3) AS SumOfStatusAmt3, " + 
                "      SUM(T1.PriorStatusAmt0) AS SumOfPriorStatusAmt0, SUM(T1.PriorStatusAmt1) AS SumOfPriorStatusAmt1, " + 
                "      SUM(T1.AmtM01) AS AmtM01x, SUM(T1.AmtM02) AS AmtM02x, SUM(T1.AmtM03) AS AmtM03x, SUM(T1.AmtM04) AS AmtM04x, SUM(T1.AmtM05) AS AmtM05x, SUM(T1.AmtM06) AS AmtM06x, " + 
                "      SUM(T1.AmtM07) AS AmtM07x, SUM(T1.AmtM08) AS AmtM08x, SUM(T1.AmtM09) AS AmtM09x, SUM(T1.AmtM10) AS AmtM10x, SUM(T1.AmtM11) AS AmtM11x, SUM(T1.AmtM12) AS AmtM12x, " + 
                "      SUM(([AmtM01]+[AmtM02]+[AmtM03]+[AmtM04]+[AmtM05]+[AmtM06]+[AmtM07]+[AmtM08]+[AmtM09]+[AmtM10]+[AmtM11]+[AmtM12])) AS AmtTotx, " + 
                "      SUM(T1.StatusCnt0) AS SumOfStatusCnt0, SUM(T1.StatusCnt1) AS SumOfStatusCnt1, SUM(T1.StatusCnt2) AS SumOfStatusCnt2, SUM(T1.StatusCnt3) AS SumOfStatusCnt3" + 
                "    FROM temp2 AS T1 " + 
                "    GROUP BY T1.ReconGroupTitle";
        transactionSummary.insertData(connection, insertIntoTransactionSummaryForDisplaySubtotalQuery, sessionUserId, sesstionTime);
        
        String insertIntoTransactionSummaryEmptyLineQuery = "INSERT INTO SOM_AA_TransactionSummary ( " + 
                " SessionUserid, SessionDate, ReconGroupTitle, Sort1, Sort2, ReconItemTitle, ReconItemCd, " + 
                " NotVerified, Pending, Complete, AutoComplete, PriorNotVerified, PriorPending, " + 
                " AmtM01x, AmtM02x, AmtM03x, AmtM04x, AmtM05x, AmtM06x, AmtM07x, AmtM08x, AmtM09x, AmtM10x, AmtM11x, AmtM12x, AmtTotx )" + 
                "    SELECT ?, ?, NULL AS ReconGroupTitle, T1.ReconGroupTitle AS Sort1, 3 AS Sort2, NULL AS ReconItemTitle, NULL AS ReconItemCd, " + 
                "      NULL AS StatusAmt0, NULL AS StatusAmt1, NULL AS StatusAmt2, NULL AS StatusAmt3, NULL AS PriorStatusAmt0, NULL AS PriorStatusAmt1, " + 
                "      NULL AS AmtM01x, NULL AS AmtM02x, NULL AS AmtM03x, NULL AS AmtM04x, NULL AS AmtM05x, NULL AS AmtM06x, " + 
                "      NULL AS AmtM07x, NULL AS AmtM08x, NULL AS AmtM09x, NULL AS AmtM10x, NULL AS AmtM11x, NULL AS AmtM12x, NULL AS AmtTotx" + 
                "    FROM temp2 AS T1 " + 
                "    GROUP BY T1.ReconGroupTitle";
        transactionSummary.insertData(connection, insertIntoTransactionSummaryEmptyLineQuery, sessionUserId, sesstionTime);
        
        String insertIntoTransactionSummaryGrandTotalQuery = "INSERT INTO SOM_AA_TransactionSummary ( " + 
                " SessionUserid, SessionDate, ReconGroupTitle, Sort1, Sort2, ReconItemTitle, ReconItemCd, " + 
                " NotVerified, Pending, AutoComplete, Complete, PriorNotVerified, PriorPending, " + 
                " AmtM01x, AmtM02x, AmtM03x, AmtM04x, AmtM05x, AmtM06x, AmtM07x, AmtM08x, AmtM09x, AmtM10x, AmtM11x, AmtM12x, AmtTotx, " + 
                " NotVerifiedCount, PendingCount, AutoCompleteCount, CompleteCount )" + 
                "    SELECT ?, ?, 'All Types' AS ReconGroupTitle, 'zzz' AS Sort1, 4 AS Sort2, 'Total' AS ReconItemTitle, 0 AS ReconItemCd, " + 
                "      SUM(T1.StatusAmt0) AS SumOfStatusAmt0, SUM(T1.StatusAmt1) AS SumOfStatusAmt1, SUM(T1.StatusAmt2) AS SumOfStatusAmt21, SUM(T1.StatusAmt3) AS SumOfStatusAmt3, " + 
                "      SUM(T1.PriorStatusAmt0) AS SumOfPriorStatusAmt0, SUM(T1.PriorStatusAmt1) AS SumOfPriorStatusAmt1, " + 
                "      SUM(T1.AmtM01) AS AmtM01x, SUM(T1.AmtM02) AS AmtM02x, SUM(T1.AmtM03) AS AmtM03x, SUM(T1.AmtM04) AS AmtM04x, SUM(T1.AmtM05) AS AmtM05x, SUM(T1.AmtM06) AS AmtM06x, " + 
                "      SUM(T1.AmtM07) AS AmtM07x, SUM(T1.AmtM08) AS AmtM08x, SUM(T1.AmtM09) AS AmtM09x, SUM(T1.AmtM10) AS AmtM10x, SUM(T1.AmtM11) AS AmtM11x, SUM(T1.AmtM12) AS AmtM12x, " + 
                "      SUM(([AmtM01]+[AmtM02]+[AmtM03]+[AmtM04]+[AmtM05]+[AmtM06]+[AmtM07]+[AmtM08]+[AmtM09]+[AmtM10]+[AmtM11]+[AmtM12])) AS AmtTotx, " +
                "      SUM(T1.StatusCnt0) AS SumOfStatusCnt0, SUM(T1.StatusCnt1) AS SumOfStatusCnt1, SUM(T1.StatusCnt2) AS SumOfStatusCnt2, SUM(T1.StatusCnt3) AS SumOfStatusCnt3" + 
                "    FROM temp2 AS T1";
        transactionSummary.insertData(connection, insertIntoTransactionSummaryGrandTotalQuery, sessionUserId, sesstionTime);
        
        String sumPartQuery = buildSumForEmployeeData(fp, fy);
        String insertIntoEmployeeListRollingQuery = "INSERT INTO SOM_AA_EmployeeListRolling ( " + 
                " SessionUserid, SessionDate, NotVerified, RecType, Sort1, Sort2, FiscalYear, FiscalPeriod, " + 
                " Employee_Name, Employee_Id, EmpChanged, ReconAssignCd, ReconStatusCd, PositionTitleCategory, PositionTitleCd, PositionTitleCdTitle, " + 
                " DeptCd, ProjectManagerCd, ProjectManagerTitleCd, ProjectUseShort, ProjectCd, FundCd, FunctionCd, FlexCd, DeptTitle, ProjectTitle, FundTitle, FunctionTitle, " + 
                " M01, M02, M03, M04, M05, M06, M07, M08, M09, M10, M11, M12, tot) " + 
                "    SELECT ?, ?, 0 as NotVerified, %s as RecType, %s as Sort1, 1 as Sort2, ?, ?, Employee_Name, Employee_Id, " + 
                "      NULL AS EmpChanged, ReconAssignCd, ReconStatusCd, PositionTitleCategory, PositionTitleCd, PositionTitleCdTitle, " + 
                "      DeptCd, ProjectManagerCd, left(ProjectManagerTitleCd,50), ProjectUseShort, ProjectCd, FundCd, FunctionCd, FlexCd, DeptTitle, ProjectTitle, FundTitle, FunctionTitle, " + 
                       sumPartQuery + 
                "      0 AS tot " + 
                "    FROM SOM_BFA_ReconEmployeeGLV AS X " + 
                "    WHERE ((X.FiscalYear = ? AND X.FiscalPeriod = ?) OR (X.FiscalYear = ? AND X.FiscalPeriod = 12)) AND X.DeptSite LIKE ? AND X.DeptTreeCd LIKE ? AND %s " + 
                "    GROUP BY X.RecType, Employee_Name, Employee_Id, " + 
                "      IIF(ReconAssignCd = 10 OR ReconAssignCd = 30, 'CHG', NULL), " + 
                "      ReconAssignCd, ReconStatusCd, PositionTitleCategory, PositionTitleCd, PositionTitleCdTitle, " + 
                "      DeptCd, ProjectManagerCd, LEFT(ProjectManagerTitleCd, 50), ProjectUseShort, ProjectCd, FundCd, " + 
                "      FunctionCd, FlexCd, DeptTitle, ProjectTitle, FundTitle, FunctionTitle";
        employeeListRolling.modifyByQuery(connection,
                String.format(insertIntoEmployeeListRollingQuery, "'FTE'", 1, " X.RecType = 'XY' "), sessionUserId,
                sesstionTime, fy, fp, fy, fp, fy - 1, deptSite, "%" + deptCdOverride + "%");
        employeeListRolling.modifyByQuery(connection,
                String.format(insertIntoEmployeeListRollingQuery.replaceAll("\\[P", "[S"),
                        "CASE WHEN X.RecType = 'XY' THEN 'RegPay' ELSE 'AddPay' END ",
                        "CASE WHEN X.RecType = 'XY' THEN 2 ELSE 3 END ", " X.RecType NOT IN ('VLA') "),
                sessionUserId, sesstionTime, fy, fp, fy, fp, fy - 1, deptSite, "%" + deptCdOverride + "%");
        
        // remove all zero only records
        String removeZeroRecordsFromEmployeeListRollingQuery = "DELETE FROM SOM_AA_EmployeeListRolling " + 
                "WHERE SessionUserid = ? AND (ABS(M01) + ABS(M02) + ABS(M03) + ABS(M04) + ABS(M05) + ABS(M06) + ABS(M07) + ABS(M08) + ABS(M09) + ABS(M10) + ABS(M11) + ABS(M12)) = 0";
        employeeListRolling.modifyByQuery(connection, removeZeroRecordsFromEmployeeListRollingQuery, sessionUserId);
        
        // remove if missing names
        String removeMissingNameFromEmployeeListRollingQuery = "DELETE FROM SOM_AA_EmployeeListRolling WHERE employee_name IS NULL";
        employeeListRolling.modifyByQuery(connection, removeMissingNameFromEmployeeListRollingQuery);
        
        // mark changed records
        String markChangedRecordsQuery = "UPDATE SOM_AA_EmployeeListRolling SET EmpChanged = 'Chg' " + 
                " WHERE SessionUserid = ? AND RecType IN ('FTE', 'RegPay') AND M01 <> M02";
        employeeListRolling.modifyByQuery(connection, markChangedRecordsQuery, sessionUserId);
        
        String insertTotalIntoEmployeeListRollingQuery = "INSERT INTO SOM_AA_EmployeeListRolling ( " + 
                " SessionUserid, SessionDate, NotVerified, RecType, Sort1, Sort2, FiscalYear, FiscalPeriod, Employee_Name, Employee_Id, EmpChanged, " + 
                " ReconAssignCd, ReconStatusCd, PositionTitleCategory, PositionTitleCd, PositionTitleCdTitle, DeptCd, ProjectManagerCd, " + 
                " ProjectManagerTitleCd, ProjectUseShort, ProjectCd, FundCd, FunctionCd, FlexCd, DeptTitle, ProjectTitle, FundTitle, FunctionTitle, " + 
                " M01, M02, M03, M04, M05, M06, M07, M08, M09, M10, M11, M12) " + 
                "    SELECT ?, ?, 0 AS NotVerified, RecType, CASE WHEN RecType='FTE' THEN 1 WHEN RecType='RegPay' THEN 2 WHEN RecType='OthPay' THEN 3 ELSE 4 END, " + 
                "      2 AS Sort2, FiscalYear, FiscalPeriod, Employee_Name, 'Total' AS Employee_Id, NULL AS EmpChanged, NULL AS ReconAssignCd, NULL AS ReconStatusCd, " + 
                "      PositionTitleCategory, NULL AS PositionTitleCd, NULL AS PositionTitleCdTitle, NULL AS DeptCd, NULL AS ProjectManagerCd, NULL AS ProjectManagerTitleCd, " + 
                "      NULL AS ProjectUseShort, NULL AS ProjectCd, NULL AS FundCd, NULL AS FunctionCd, NULL AS FlexCd, NULL AS DeptTitle, NULL AS ProjectTitle, NULL AS FundTitle, NULL AS FunctionTitle, " + 
                "      SUM(M01), SUM(M02), SUM(M03), SUM(M04), SUM(M05), SUM(M06), SUM(M07), SUM(M08), SUM(M09), SUM(M10), SUM(M11), SUM(M12) " + 
                "    FROM SOM_AA_EmployeeListRolling AS X " + 
                "    WHERE SessionUserid= ? AND Sort2 = 1 " + 
                "    GROUP BY RecType, FiscalYear, FiscalPeriod, Employee_name, Employee_Id, PositionTitleCategory, " + 
                "      CASE WHEN RecType='FTE' THEN 1 WHEN RecType='RegPay' THEN 2 WHEN RecType='AddPay' THEN 3 ELSE 4 END ";
        employeeListRolling.modifyByQuery(connection, insertTotalIntoEmployeeListRollingQuery, sessionUserId, sesstionTime, sessionUserId);
        
        String insertZSpaceIntoEmployeeListRollingQuery = "INSERT INTO SOM_AA_EmployeeListRolling ( " + 
                " SessionUserid, SessionDate, NotVerified, RecType, Sort1, Sort2, FiscalYear, FiscalPeriod, Employee_Name, Employee_Id, EmpChanged, ReconAssignCd, ReconStatusCd, " + 
                " PositionTitleCategory, PositionTitleCd, PositionTitleCdTitle, " + 
                " DeptCd, ProjectManagerCd, ProjectManagerTitleCd, ProjectUseShort, ProjectCd, FundCd, FunctionCd, FlexCd, DeptTitle, ProjectTitle, FundTitle, FunctionTitle, " + 
                " M01, M02, M03, M04, M05, M06, M07, M08, M09, M10, M11, M12) " + 
                "    SELECT ?, ?, 0 as NotVerified, NULL AS RecType, 5 as Sort1, 1 as Sort2, FiscalYear, FiscalPeriod, Employee_Name, 'zSpace' as Employee_Id, " + 
                "      NULL AS EmpChanged, NULL AS ReconAssignCd, NULL AS ReconStatusCd, PositionTitleCategory, NULL AS PositionTitleCd, NULL AS PositionTitleCdTitle, " + 
                "      NULL AS DeptCd, NULL AS ProjectManagerCd, NULL AS ProjectManagerTitleCd, NULL AS ProjectUseShort, " + 
                "      NULL AS ProjectCd, NULL AS FundCd, NULL AS FunctionCd, NULL AS FlexCd, NULL AS DeptTitle, NULL AS ProjectTitle, NULL AS FundTitle, NULL AS FunctionTitle, " + 
                "      NULL AS S01_Jul, NULL AS S02_Aug, NULL AS S03_Sep, NULL AS S04_Oct, NULL AS S05_Nov, NULL AS S06_Dec, " + 
                "      NULL AS S07_Jan, NULL AS S08_Feb, NULL AS S09_Mar, NULL AS S10_Apr, NULL AS S11_May, NULL AS S12_Jun " + 
                "    FROM SOM_AA_EmployeeListRolling as X " + 
                "    WHERE SessionUserid = ? AND Sort2 = 1 " + 
                "    GROUP BY FiscalYear, FiscalPeriod, PositionTitleCategory, Employee_name, Employee_Id";
        employeeListRolling.modifyByQuery(connection, insertZSpaceIntoEmployeeListRollingQuery, sessionUserId, sesstionTime, sessionUserId);
        
        // add payroll line to dashboard if missing
        String addPayrollLineQuery = "IF NOT EXISTS(SELECT * FROM SOM_AA_Dashboard WHERE SessionUserid = ? AND ReconGroupTitle = 'Payroll') " + 
                " INSERT INTO SOM_AA_Dashboard(SessionUserid, SessionDate, FiscalYear, ReconGroupTitle) VALUES(?, ?, ?, 'Payroll') ";
        dashboard.modifyByQuery(connection, addPayrollLineQuery, sessionUserId, sessionUserId, sesstionTime, fy);
        
        // Get not verified payroll amounts and counts 
        String getNotVerifiedPayrollStt1000Query = "SELECT COUNT(*) AS countPayroll, SUM(M01) AS sumPayroll" + 
                " FROM SOM_AA_EmployeeListRolling WHERE SessionUserid = ? AND ReconStatusCd IN (0, 1000) AND RecType = 'RegPay'";
        HashMap<String, BigDecimal> stt1000CountAndSum = employeeListRolling.getCountAndSumM01FromByQuery(connection, getNotVerifiedPayrollStt1000Query, sessionUserId);
        BigDecimal countPayroll = stt1000CountAndSum.get("countPayroll");
        BigDecimal sumPayroll = stt1000CountAndSum.get("sumPayroll");
        if (sumPayroll == null) {
            sumPayroll = new BigDecimal(0);
        }
        // Add amounts to payroll dashboard line 
        String addAmountsToPayrollStatusAmt0DashboardQuery = "UPDATE SOM_AA_Dashboard " + 
                " SET StatusAmt0 = StatusAmt0 + ?, StatusCnt0=StatusCnt0 + ? WHERE SessionUserid = ? AND ReconGroupTitle = 'Payroll'";
        dashboard.modifyByQuery(connection, addAmountsToPayrollStatusAmt0DashboardQuery, sumPayroll, countPayroll, sessionUserId);
        
        
        
        // Get not verified payroll amounts and counts 
        String getNotVerifiedPayrollStt3000Query = "SELECT COUNT(*) AS countPayroll, SUM(M01) AS sumPayroll" + 
                " FROM SOM_AA_EmployeeListRolling WHERE SessionUserid = ? AND ReconStatusCd = 3000 AND RecType = 'RegPay'";
        HashMap<String, BigDecimal> stt3000CountAndSum = employeeListRolling.getCountAndSumM01FromByQuery(connection, getNotVerifiedPayrollStt3000Query, sessionUserId);
        countPayroll = stt3000CountAndSum.get("countPayroll");
        sumPayroll = stt3000CountAndSum.get("sumPayroll");
        if (sumPayroll == null) {
            sumPayroll = new BigDecimal(0);
        }
        // Add amounts to payroll dashboard line 
        String addAmountsToPayrollStatusAmt3DashboardQuery = "UPDATE SOM_AA_Dashboard " + 
                " SET StatusAmt3 = StatusAmt3 + ?, StatusCnt3=StatusCnt3 + ? WHERE SessionUserid = ? AND ReconGroupTitle = 'Payroll'";
        dashboard.modifyByQuery(connection, addAmountsToPayrollStatusAmt3DashboardQuery, sumPayroll, countPayroll, sessionUserId);
        
        String deleteTemp1Sql = "DROP TABLE temp1";
        String deleteTemp2Sql = "DROP TABLE temp2";
        statement.execute(deleteTemp1Sql);
        statement.execute(deleteTemp2Sql);
        statement.close();
    }
    
    private String buildSumForEmployeeData (int fp, int fy) {
        final String[] MONTH_ARRAY = {"Jul", "Jun", "May", "Apr", "Mar", "Feb", "Jan", "Dec", "Nov", "Oct", "Sep", "Aug"};
        final String[] NUMBER_ARRAY = {"12", "11", "10", "09", "08", "07", "06", "05", "04", "03", "02", "01"};
        final int ARRAY_LENGTH = 12;
        String template = " SUM(CASE WHEN FiscalYear = %s THEN [P%s_%s] ELSE 0 END) AS M%s, \n";
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int newFy = i < fp ? fy : fy - 1;
            
            // NUMBER_ARRAY[(12 - fp + i) % ARRAY_LENGTH]: to run in direct like 12, 11, 10, ..., 01
            // MONTH_ARRAY[(13 - fp + i) % 12]: to run in direct: Jul, Jun, May, ...
            // NUMBER_ARRAY[11 - i]: to run in direct: 01, 02, 03, ...
            query.append(String.format(template, newFy, NUMBER_ARRAY[(12 - fp + i) % ARRAY_LENGTH], MONTH_ARRAY[(13 - fp + i) % 12], NUMBER_ARRAY[11 - i]));
        }
        
        return query.toString();
    }

}
