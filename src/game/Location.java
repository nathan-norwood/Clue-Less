package game;

import java.util.Vector;

public class Location extends GameComponent{
	private Vector<Location> reachableLocations;
	private boolean occupied = false;
	private boolean isRoom = false;
	
	public Location(int i, String s){
		super(i,s);
		reachableLocations = new Vector<Location>();
	}

	public void setIsRoom(){
		isRoom = true;
	}
	
	public void addLocation(Location l){
		reachableLocations.add(l);
	}

}
