package main;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Y.Nevenchenov
 *
 * explore DB in matter of rooms variety and generates 'roomsSummery' DB table
 *
 * Created on 04-May-17.
 */

public class DBRoomsSummeryWriter extends DBOperate{
 
    // db operating and returned data
    private static Map<String, Integer> numberOfRoomsInEachCategory = new HashMap<>();  //key - rank; value - how much rooms;
    private static Map<String, Integer> numberOfBedsInEachCategory = new HashMap<>();  //key - rank; value - how much beds;
    private static Map<String, Map<Integer, Integer>> numberOfRoomsInEachBedsCapacityCategory = new HashMap<>();  //key - rank; value - map (key - beds in room; value - how much rooms);
    private static ArrayList<Integer> bedsCapacitiesInHotel = new ArrayList<>();    // what kind of rooms are in hotel by beds number in;


    private static String query;
    private static String update;


    public static String exploreDB() {

        String report = "No Table in DB appear yet";
         try {
            // opening database connection to MySQL server
            con = DBConnector.get();
 
            // getting Statement object to execute query
            stmt = con.createStatement();


            // get categories maps from DB
             roomCategoriesFinder(stmt);
             numberOfBedsInEachCategoryFinder(stmt);
             numberOfRoomsInEachBedsCapacityCategoryFinder(stmt);
             BedsCapacityCategoryFinder(stmt);

             // make report table
             tableCreator(stmt);
             fillRoomsSummery(stmt);

             //check writing into DB/
             report = checkWritingIntoDB(stmt);


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            System.out.println("Connection is closed");
        }
        return report;
    }

    private static void roomCategoriesFinder(Statement stmt){
        int count;
        String rank;

        query = "SELECT COUNT(*),rank FROM rooms GROUP BY rank";
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
    }

    private static void numberOfBedsInEachCategoryFinder(Statement stmt){
        int count = 0;

        for ( String key : numberOfRoomsInEachCategory.keySet() ) {
            query = "SELECT bedsCount FROM `rooms` WHERE rank = '" + key +"';";

        try {
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                count += rs.getInt(1);

             }

        } catch (SQLException e) {
            e.printStackTrace();
        }
            numberOfBedsInEachCategory.put(key, count);
        }
    }

    private static void numberOfRoomsInEachBedsCapacityCategoryFinder(Statement stmt) throws SQLException{
        int extractNumber;

        for ( String key : numberOfRoomsInEachCategory.keySet() ) {
            query = "SELECT bedsCount FROM `rooms` WHERE rank = '" + key +"';";
            Map<Integer, Integer> numberOfRoomsByBedsCapacity = new HashMap<>();

            try {
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    extractNumber = rs.getInt(1);
                    if(numberOfRoomsByBedsCapacity.containsKey(extractNumber)){
                        numberOfRoomsByBedsCapacity.put(extractNumber, numberOfRoomsByBedsCapacity.get(extractNumber) +1);
                    }
                    else{
                        numberOfRoomsByBedsCapacity.put(extractNumber, 1);
                    }

                 }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            numberOfRoomsInEachBedsCapacityCategory.put(key, numberOfRoomsByBedsCapacity);
        }
    }

    private static void BedsCapacityCategoryFinder(Statement stmt) {
        int beds;
        String query = "SELECT bedsCount FROM rooms GROUP BY bedsCount";
        try {
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                beds = rs.getInt("bedsCount");
                bedsCapacitiesInHotel.add(beds);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void tableCreator(Statement stmt){

        String roomsNumberByBedsCapacity = "";

        for(int beds : bedsCapacitiesInHotel){
            roomsNumberByBedsCapacity += ", " + beds + "bedsRooms int(4)";
        }


        update = "CREATE TABLE IF NOT EXISTS roomsSummery (rank VARCHAR(10), rankRoomsNumber int(4), rankBedsNumber int(4)"
                    + roomsNumberByBedsCapacity + ") COLLATE = utf8_general_ci;";

        try {
            stmt.executeUpdate(update);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void fillRoomsSummery(Statement stmt){

        for(Map.Entry<String, Integer> entryCategory: numberOfRoomsInEachCategory.entrySet()){
            String category = entryCategory.getKey();
            int numbOfRooms = entryCategory.getValue();
            int numbOfBeds = numberOfBedsInEachCategory.get(category);
            String numbOfRoomsByBedsCapacityQueryVariables = "";
            String numbOfRoomsByBedsCapacityQueryValues = "";
            Map<Integer, Integer> numbOfRoomsByBedsCapacity = numberOfRoomsInEachBedsCapacityCategory.get(category);
            for(Map.Entry<Integer, Integer> entryCapacity: numbOfRoomsByBedsCapacity.entrySet()){
                numbOfRoomsByBedsCapacityQueryVariables += ", " + entryCapacity.getKey() + "bedsRooms";
                numbOfRoomsByBedsCapacityQueryValues += ", " + entryCapacity.getValue();
            }



            String update = "INSERT INTO hotel.roomsSummery (rank, rankRoomsNumber, rankBedsNumber" + numbOfRoomsByBedsCapacityQueryVariables + ")" +
                    " VALUES ('" + category + "', " +
                    numbOfRooms + ", " +
                    numbOfBeds +
                    numbOfRoomsByBedsCapacityQueryValues + ");";
            try {
                stmt.executeUpdate(update);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static String checkWritingIntoDB(Statement stmt){
        int rowsCount = 0;
        int colsCount = 0;


        String queryRows = "SELECT COUNT(*) FROM roomsSummery";
        String queryCols = "SELECT Count(*) FROM INFORMATION_SCHEMA.Columns where TABLE_NAME = 'rooms'";
        try {
            rs = stmt.executeQuery(queryRows);

            while (rs.next()) {
                rowsCount = rs.getInt(1);
            }

            rs = stmt.executeQuery(queryCols);

            while (rs.next()) {
                colsCount = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ("Table is created.<br>" + rowsCount + " ranks of rooms exists and <br>" + (colsCount - 3) + "categories of room capacity have written.");
    }

}
