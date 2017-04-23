package game;

import java.util.HashMap;

/* TODO: I dont know if we need this yet but it is here in case */
public class GamePieces {

	public HashMap<Integer,String> suspects;
	public HashMap<Integer,String> weapons;
	public HashMap<Integer,String> rooms;
	
	public static GamePieces instance;
	
	private GamePieces(){
		suspects = new HashMap<Integer,String>();
		suspects.put(1, "Miss Scarlet");
		suspects.put(2, "Colonel Mustard");
		suspects.put(3, "Professor Plum");
		suspects.put(4, "Mr. Green");
		suspects.put(5, "Mrs. White");
		suspects.put(6, "Mrs. Peacock");
		
		weapons = new HashMap<Integer,String>();
		weapons.put(1, "Rope");
		weapons.put(2, "Lead Pipe");
		weapons.put(3, "Knife");
		weapons.put(4, "Wrench");
		weapons.put(5, "Candlestick");
		weapons.put(6, "Revolver");
		
		rooms = new HashMap<Integer,String>();
		rooms.put(1, "Study");
		rooms.put(2, "Hall");
		rooms.put(3, "Lounge");
		rooms.put(4, "Library");
		rooms.put(5, "Billiard Room");
		rooms.put(6, "Dining Room");
		rooms.put(7, "Conservatory");
		rooms.put(8, "Ballroom");
		rooms.put(9, "Kitchen");
		
		
	}
	
	/*singleton for all game pieces instead of ENUMs */
	public static GamePieces getInstance(){
		if(instance == null){
			instance = new GamePieces();
		}
		return instance;
	}
	
	public HashMap<Integer,String> getSuspects(){
		return suspects;
		
	}
	public HashMap<Integer,String> getWeapons(){
		return weapons;
		
	}
	public HashMap<Integer,String> getRooms(){
		return rooms;
		
	}
	public String getSuspectByID(int id){
		return suspects.get(id);
		
	}
	public String getWeaponByID(int id){
		return weapons.get(id);
		
	}
	public String getRoomByID(int id){
		return rooms.get(id);
		
	}
}
