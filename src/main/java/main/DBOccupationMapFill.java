package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 *
 */

public class DBOccupationMapFill {
 
    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    // db operating and returned data
    public static int daysCount = 0;
    public static int bedsCount = 0;
    public static int addedRowsCount = 0;
    public static int[] result = new int[3];


 
    public static int[] fillDB() {

        String update;

         try {
            // opening database connection to MySQL server
            con = DBConnector.get();
 
            // getting Statement object to execute query
            stmt = con.createStatement();


            // get count of days from DB
            daysCount = countRows(stmt, "hotel", "days", "dayNumber");

            // get count of beds from DB
            bedsCount = countRows(stmt, "hotel", "beds", "bedNumber");

            System.out.print(daysCount + "days in table and" + bedsCount + " beds ");

            //fill occupation in D
            for(int i = 0; i < daysCount; i++){
                for(int j = 0; j < bedsCount; j++) {
                    update = "INSERT INTO hotel.occupationMap (dayNumber, bedNumber, isOccupied) \n" +
                            " VALUES (" + (i + 1) + ", " + (j + 1) +
                            ", 'false');";
                    stmt.executeUpdate(update);
                }
            }

            // check writing DB
            addedRowsCount = countRows(stmt, "hotel", "occupationMap", "n");


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            System.out.println("Connection is closed");
        }
        result[0] = daysCount;
        result[1] = bedsCount;
        result[2] = addedRowsCount;

    return result;
    }


    public static int countRows(Statement stmt, String dbName, String tableName, String columnName) {
        int count=0;
        String query = "select COUNT(" + columnName + ") FROM `" + dbName + "`.`" + tableName + "`";
        try {
            rs = stmt.executeQuery(query);

        while (rs.next()) {
             count = rs.getInt(1);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}

