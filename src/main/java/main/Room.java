package main;

import java.util.List;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 * Created on 03-17.
 */
public class Room {
	short number; //from door tablet;
	short countOfBeds;
	List<Bed> beds;
	short cost; //for one day;
	short floor;
	Raiting raiting;
	
	public Room(short number, short countOfBeds, List<Bed> beds, short cost, short floor, Raiting raiting){
		this.number = number;
		this.countOfBeds = countOfBeds;
		this.beds = beds;
		this.cost = cost;
		this.floor = floor;
		this.raiting = raiting;
	}
}
