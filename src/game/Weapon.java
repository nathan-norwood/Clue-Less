package game;

public class Weapon extends GameComponent{

	private Location currentLocation;

	public Weapon(int i, String s) {
		super(i, s);
	}

	public void setLocation(Location locationById) {
		currentLocation = locationById;
	}



}
