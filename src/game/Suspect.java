package game; 

public class Suspect extends GameComponent{

	private Location currentLocation;
	
	public Suspect(int i, String s) {

		super(i, s);
	}

	public void setLocation(Location l) {
		currentLocation = l;
	}
	public Location getLocation(){
		return currentLocation;
	}

	

}
