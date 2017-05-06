package main;
import java.util.ArrayList;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 * Created on 03-17.
 */
public class Hotel {
	protected static ArrayList<Room> hotel = new ArrayList<>();
	public static void addRoom(Room room) {

		hotel.add(room);

	}
	
	public ArrayList<Room> getHotel(){
		return hotel;
	}

}
