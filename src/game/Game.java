package game;

public class Game {

	private int unique_id;

	public Game(int id){
		unique_id = id;
	}
	
	public int getId() {
		return unique_id;
	}


	public void addPlayer(int playerID){
		/*TODO: what do we need to send in to add a player to this game?
		 * 1. Player ID - where GameServelet keeps ID + socket map,
		 * 	and game keeps ID + player object?
		 * */
		
	}	
	
	public void configureGame(){
		
	}
	
	public void startGame(){
		/* this would be a state update */
		
	}
	
	public boolean makeSuggestion(){
		boolean successful = false;
		
		
		return successful;
	}
	
	public boolean disproveSuggestion(){
		boolean successful = false;
		
		
		return successful;

	}
	public boolean makeAccusation(){
		boolean successful = false;
		
		
		return successful;

	}
	public void makeMove(){
		
	}
	public void nextPlayer(){
		
	}

	
	
	
}
