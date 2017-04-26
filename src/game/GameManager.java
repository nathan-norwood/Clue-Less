package game;

import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.Session;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/* Singleton that manages games */
public class GameManager {

	private Vector<Game> games;
	private static GameManager instance;
	private int id = 0;
	private BiMap<Session, Integer> playerSessions = HashBiMap.create();
	
	private GameManager(){
		games = new Vector<Game>();
	}
	
	public static GameManager getInstance(){
		if(instance == null){
			instance = new GameManager();
		}
		return instance;
	}
	
	public void newGame(String name, int hostPlayerId, int suspectId){
		/*Create a new game*/
		
		/*TODO: Instead of creating the game and deleting it if the host doesn't join,
		 * this function only creates a game with the host player joined to it. 
		 * Update later if time allows
		 */
		games.add(new Game(id, name, hostPlayerId, suspectId));
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
	public void addSession(Session session, int id){
		this.playerSessions.put(session,id);
		System.out.println("Added to BiMap" + playerSessions.get(session));
	}

	public void handleMessage(Session session, String message) {
		
		System.out.println(message);
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject input = reader.readObject();
		reader.close();
		
		//Check to see what the "type" is and perform operations accordingly
		
		try {
			session.getBasicRemote().sendText("From Game Manager");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
