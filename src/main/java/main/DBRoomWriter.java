package main;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Y.Nevenchenov
 *
 * Writes room date in DB 'rooms' table and adds beds into 'beds' DB table
 *
 *
 */

public class DBRoomWriter extends DBOperate{


    // data writing indicator
    public static String isWrote = "<strong>Fill form <br>and press \"OK\"</strong>";
 
    public static void sendToDB(Map<String, String> room) {

        String updateRooms = "INSERT INTO hotel.rooms (roomNumber, floor, bedsCount, rank, minPrice, maxPrice) \n" +
                            " VALUES (" + room.get("roomNumber") + ", " +
                            room.get("floor") + ", " +
                            room.get("bedsCount") + ", '" +
                            room.get("rank") + "', " +
                            room.get("minPrice") + ", " +
                            room.get("maxPrice") + ");";
        int bedsCount = Integer.parseInt(room.get("bedsCount"));

        String queryRoom = "select * FROM `hotel`.`rooms` ";

        try {
            // opening database connection to MySQL server
            con = DBConnector.get();
 
            // getting Statement object to execute query
            stmt = con.createStatement();

//working with room
            // executing insertion to DB
            stmt.executeUpdate(updateRooms);

            // checking appearance of room added in DB
            rs = stmt.executeQuery(queryRoom);
            while (rs.next()) {
                if(rs.getInt("roomNumber") == Integer.parseInt(room.get("roomNumber"))){
                System.out.print("DB is updated by room; ");
                isWrote = "Room is added.";
                break;
                }
            }

//working with beds
            //find max bed number in 'beds' DB table
            rs = stmt.executeQuery("SELECT MAX(bedNumber) FROM beds;");
            rs.next();
            int lastBedNumberInDB = rs.getInt("MAX(bedNumber)");

            //insert beds in 'beds' DB table setting that numbers
            for(int i = 1; i <= bedsCount; i++){
                stmt.executeUpdate("INSERT INTO hotel.beds (bedNumber, whatRoom) \n" +
                        " VALUES (" + (lastBedNumberInDB + i) + ", " +
                        room.get("roomNumber") + ");");
            }

            // checking appearance of beds added in DB
            rs = stmt.executeQuery("SELECT MAX(bedNumber) FROM beds;");
            rs.next();
            int newLastBedNumberInDB = rs.getInt("MAX(bedNumber)");

                if (newLastBedNumberInDB == (lastBedNumberInDB + bedsCount)) {
                    System.out.print("DB is updated by beds; ");
                    isWrote += " " + bedsCount + "bed(s) is(are) added.";
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

