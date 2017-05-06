package main;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 * Created on 04-May-17.
 */

public class TableHeaderMaker extends DBOperate{
 
    // db operating and returned data
    private static int numberOfRoomCategories = 0;
    private static String[] roomCategories;
    private static Map<String, Integer> numberOfRoomsInEachCategory = new HashMap<>();


//?????????????? Make List of Room-Objects and work with that or operate with DB? ???????????????

 
    public static void findInDB() {

         try {
            // opening database connection to MySQL server
            con = DBConnector.get();
 
            // getting Statement object to execute query
            stmt = con.createStatement();


            // get categories array from DB
             roomCategoriesFinder(stmt);

//            // get count of beds from DB
//            bedsCount = countRows(stmt, "hotel", "beds", "bedNumber");
//
//            System.out.print(daysCount + "days in table and" + bedsCount + " beds ");
//
//            //fill occupation in D
//            for(int i = 0; i < daysCount; i++){
//                for(int j = 0; j < bedsCount; j++) {
//                    update = "INSERT INTO hotel.occupationMap (dayNumber, bedNumber, isOccupied) \n" +
//                            " VALUES (" + (i + 1) + ", " + (j + 1) +
//                            ", 'false');";
//                    stmt.executeUpdate(update);
//                }
//            }
//
//            // check writing DB
//            addedRowsCount = countRows(stmt, "hotel", "occupationMap", "n");


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            System.out.println("Connection is closed");
        }


//    return result;
    }
    private static void roomCategoriesFinder(Statement stmt){
        int count;
        String rank;

        String query = "SELECT COUNT(*),rank FROM rooms GROUP BY rank";
        try {
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                count = rs.getInt(1);
                rank = rs.getString("rank");
                numberOfRoomsInEachCategory.put(rank, count);
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 //       System.out.println(count);
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

