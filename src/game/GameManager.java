package game;

import java.util.Vector;

/* Singleton that manages games */
public class GameManager {

	private Vector<Game> games;
	private static GameManager instance;
	private int id = 0;
	
	private GameManager(){
		games = new Vector<Game>();
	}
	
	public static GameManager getInstance(){
		if(instance == null){
			instance = new GameManager();
		}
		return instance;
	}
	
	public void newGame(int hostPlayerId, int suspectId){
		/*Create a new game*/
		
		games.add(new Game(id, hostPlayerId, suspectId));
		id++;
		
	}
	
	public Game getGameById(int id){
		/* When a specific game is selected, use it */
		
		for(Game g: games){
			if(g.getId() == id){
				return g;
			}
		}
		return null;
	}
	
	public Vector<Game> getOpenGames(){
		/* Provide a list of open games */
		Vector<Game> openGames = new Vector<Game>();
		for(Game g:games){
			if(g.isOpen()){
				openGames.add(g);
			}
		}
		return openGames;
		
	}
}
