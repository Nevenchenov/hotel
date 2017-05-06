package main;

/**
 * @author Y.Nevenchenov
 *
 *
 *
 * Created on 03-17.
 */
public class Bed {
	int bedNumber;
	boolean occupied = false;

	public Bed(int bedNumber){
		this.bedNumber = bedNumber;
	}

	public void settling(){
		occupied = true;
	}
	public void unSettling(){
		occupied = false;
	}

	public boolean isOccupied(){
		return occupied;
	}

}
