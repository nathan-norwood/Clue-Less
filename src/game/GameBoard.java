package game;

import java.util.Vector;

/* TODO: Should we shift how we do IDs? so they can be shared with cards? */
public class GameBoard {

	public Vector<Suspect> suspects;
	public Vector<Weapon> weapons;
	public Vector<Location> locations;
	
	
	public GameBoard(){
		suspects = new Vector<Suspect>();
		suspects.add(new Suspect(1, "Miss Scarlet"));
		suspects.add(new Suspect(2, "Colonel Mustard"));
		suspects.add(new Suspect(3, "Professor Plum"));
		suspects.add(new Suspect(4, "Mr. Green"));
		suspects.add(new Suspect(5, "Mrs. White"));
		suspects.add(new Suspect(6, "Mrs. Peacock"));
		
		weapons = new Vector<Weapon>();
		weapons.add(new Weapon(1, "Rope"));
		weapons.add(new Weapon(2, "Lead Pipe"));
		weapons.add(new Weapon(3, "Knife"));
		weapons.add(new Weapon(4, "Wrench"));
		weapons.add(new Weapon(5, "Candlestick"));
		weapons.add(new Weapon(6, "Revolver"));
		
		locations = new Vector<Location>();
		locations.add(new Room(1, "Study"));
		locations.add(new Room(2, "Hall"));
		locations.add(new Room(3, "Lounge"));
		locations.add(new Room(4, "Library"));
		locations.add(new Room(5, "Billiard Room"));
		locations.add(new Room(6, "Dining Room"));
		locations.add(new Room(7, "Conservatory"));
		locations.add(new Room(8, "Ballroom"));
		locations.add(new Room(9, "Kitchen"));
		
		
	}
	
	
	
	
}
