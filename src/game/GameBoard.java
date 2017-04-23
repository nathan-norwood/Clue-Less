package game;

import java.util.Vector;

/* TODO: Should we shift how we do IDs? so they can be shared with cards? */
public class GameBoard {

	public Vector<Suspect> suspects;
	public Vector<Weapon> weapons;
	public Vector<Room> rooms;
	
	
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
		
		rooms = new Vector<Room>();
		rooms.add(new Room(1, "Study"));
		rooms.add(new Room(2, "Hall"));
		rooms.add(new Room(3, "Lounge"));
		rooms.add(new Room(4, "Library"));
		rooms.add(new Room(5, "Billiard Room"));
		rooms.add(new Room(6, "Dining Room"));
		rooms.add(new Room(7, "Conservatory"));
		rooms.add(new Room(8, "Ballroom"));
		rooms.add(new Room(9, "Kitchen"));
		
		
	}
	
	public GameComponent getById(GameComponentType t, int id){
		switch(t){
			case SUSPECT:
				for( Suspect s : suspects ){
					if( s.hasID(id) ){
						return s;
					}
				}
				break;
				
			case ROOM:
				for( Room r : rooms ){
					if( r.hasID(id) ){
						return r;
					}
				}
				break;
				
			case WEAPON:
				for( Weapon w : weapons ){
					if( w.hasID(id) ){
						return w;
					}
				}
				break;
			
		}
		return null;
	}
	
	
	
	
}
