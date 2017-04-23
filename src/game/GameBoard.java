package game;

import java.util.Vector;

/* TODO: Should we shift how we do IDs? so they can be shared with cards? */
public class GameBoard {

	public Vector<Suspect> suspects;
	public Vector<Weapon> weapons;
	public Vector<Location> locations;
	
	
	public GameBoard(){
		/* Create Game Components */
		locations = new Vector<Location>();
		suspects = new Vector<Suspect>();
		
		setupBoard();

		weapons = new Vector<Weapon>();
		weapons.add(new Weapon(1, "Rope"));
		weapons.add(new Weapon(2, "Lead Pipe"));
		weapons.add(new Weapon(3, "Knife"));
		weapons.add(new Weapon(4, "Wrench"));
		weapons.add(new Weapon(5, "Candlestick"));
		weapons.add(new Weapon(6, "Revolver"));
		
		
		
		/*TODO: Layout Game Components on board */
		/* Suspects go in defined rooms
		 * Weapons are laid out randomly
		 * 
		 */
		
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
				for( Location r : locations ){
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
	
	private void setupBoard(){
		
		/* TODO: I found it easier to use a single Location with a few flags
		 * to differentiate behavior between rooms/hallways - we can shift this 
		 * later as needed.
		 */
		
		/* TODO: somehow we will need to associate physical board with these
		 * locations - we can give them a position # or we can assign the board 
		 * IDs... either way.
		 */
		int id = 0;
		Location study = new Location(id++, "Study");
		study.setIsRoom();
		
		Location hall  = new Location(id++, "Hall");
		hall.setIsRoom();
		
		Location lounge = new Location(id++, "Lounge");
		lounge.setIsRoom();
		
		Location library = new Location(id++, "Library");
		library.setIsRoom();
		
		Location billiard = new Location(id++, "Billiard Room");
		billiard.setIsRoom();
		
		Location dining = new Location(id++, "Dining Room");
		dining.setIsRoom();
		
		Location conservatory = new Location(id++, "Conservatory");
		conservatory.setIsRoom();
		
		Location ballroom = new Location(id++, "Ballroom");
		ballroom.setIsRoom();
		
		Location kitchen = new Location(id++, "Kitchen");
		kitchen.setIsRoom();
		
		Location h1 = new Location(id++, "H1");
		Location h2 = new Location(id++, "H2");
		Location h3 = new Location(id++, "H3");
		Location h4 = new Location(id++, "H4");
		Location h5 = new Location(id++, "H5");
		Location h6 = new Location(id++, "H6");
		Location h7 = new Location(id++, "H7");
		Location h8 = new Location(id++, "H8");
		Location h9 = new Location(id++, "H9");
		Location h10 = new Location(id++, "H10");
		Location h11 = new Location(id++, "H11");
		Location h12 = new Location(id++, "H12");
		
		study.addLocation(h1);
		study.addLocation(h3);
		study.addLocation(kitchen);  //TODO: Handle secret passageways differently?
		locations.add(study);
		
		h1.addLocation(study);
		h1.addLocation(hall);
		locations.add(h1);
		
		hall.addLocation(h1);
		hall.addLocation(h2);
		hall.addLocation(h4);
		locations.add(hall);
		
		h2.addLocation(hall);
		h2.addLocation(lounge);
		locations.add(h2);
		
		lounge.addLocation(h2);
		lounge.addLocation(h5);
		lounge.addLocation(conservatory);
		locations.add(lounge);
		
		h3.addLocation(study);
		h3.addLocation(library);
		locations.add(h3);
		
		h4.addLocation(hall);
		h4.addLocation(billiard);
		locations.add(h4);
		
		h5.addLocation(lounge);
		h5.addLocation(dining);
		locations.add(h5);
		
		library.addLocation(h3);
		library.addLocation(h6);
		library.addLocation(h8);
		locations.add(library);
		
		h6.addLocation(library);
		h6.addLocation(billiard);
		locations.add(h6);
		
		billiard.addLocation(h4);
		billiard.addLocation(h6);
		billiard.addLocation(h7);
		billiard.addLocation(h9);
		locations.add(billiard);
		
		h7.addLocation(billiard);
		h7.addLocation(dining);
		locations.add(h7);
		
		dining.addLocation(h5);
		dining.addLocation(h7);
		dining.addLocation(h10);
		locations.add(dining);
		
		h8.addLocation(library);
		h8.addLocation(conservatory);
		locations.add(h8);
		
		h9.addLocation(billiard);
		h9.addLocation(ballroom);
		locations.add(h9);
		
		h10.addLocation(dining);
		h10.addLocation(kitchen);
		locations.add(h10);
		
		conservatory.addLocation(h8);
		conservatory.addLocation(h11);
		conservatory.addLocation(lounge);
		locations.add(conservatory);
		
		h11.addLocation(conservatory);
		h11.addLocation(ballroom);
		locations.add(h11);

		ballroom.addLocation(h9);
		ballroom.addLocation(h11);
		ballroom.addLocation(h12);
		locations.add(ballroom);
		
		h12.addLocation(ballroom);
		h12.addLocation(kitchen);
		locations.add(h12);
		
		kitchen.addLocation(h10);
		kitchen.addLocation(h12);
		kitchen.addLocation(study);
		locations.add(kitchen);
		
		Suspect scarlet = new Suspect(1, "Miss Scarlet"); 
		scarlet.setLocation(h2);
		suspects.add(scarlet);
		
		Suspect mustard = new Suspect(2, "Colonel Mustard");
		mustard.setLocation(h5);
		suspects.add(mustard);
		
		Suspect plum = new Suspect(3, "Professor Plum");
		plum.setLocation(h3);
		suspects.add(plum);
		
		Suspect green = new Suspect(4, "Mr. Green");
		green.setLocation(h11);
		suspects.add(green);
		
		Suspect white = new Suspect(5, "Mrs. White");
		white.setLocation(h12);
		suspects.add(white);
		
		Suspect peacock = new Suspect(6, "Mrs. Peacock");
		peacock.setLocation(h8);
		suspects.add(peacock);
	}
	
	
}
