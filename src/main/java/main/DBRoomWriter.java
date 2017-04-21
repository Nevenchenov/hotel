package main;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 *
 */

public class DBRoomWriter {
 
    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    // data writting indicator
    public static String isWrote = "<strong>Fill form <br>and press \"OK\"</strong>";
 
    public static void sendToDB(Map<String, String> room) {

        String update = "INSERT INTO hotel.rooms (roomNumber, floor, bedsCount, rank, minPrice, maxPrice) \n" +
                            " VALUES (" + room.get("roomNumber") + ", " +
                            room.get("floor") + ", " +
                            room.get("bedsCount") + ", '" +
                            room.get("rank") + "', " +
                            room.get("minPrice") + ", " +
                            room.get("maxPrice") + ");";

        String query = "select * FROM `hotel`.`rooms` ";

        try {
            // opening database connection to MySQL server
            con = DBConnector.get();
 
            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing insertion to DB
            stmt.executeUpdate(update);

            // checking appearance of room added in DB
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                if(rs.getInt("roomNumber") == Integer.parseInt(room.get("roomNumber"))){
                System.out.print("DB is updated; ");
                isWrote = "Writing Complete!";
                break;
                }
            }

 
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            System.out.println("Connection is closed");
        }
    }
 
}

