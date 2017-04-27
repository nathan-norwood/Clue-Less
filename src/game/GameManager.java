package game;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.websocket.Session;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/* Singleton that manages games */
public class GameManager {

	private Vector<Game> games;
	private GameBoard game_board;
	private static GameManager instance;
	private int id = 0;
	private BiMap<Session, Integer> playerSessions = HashBiMap.create();
	
	private GameManager(){
		games = new Vector<Game>();
		game_board = new GameBoard();
		games.add(new Game(0 , "test", 1, 27));
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
	
	public void addSession(Session session, int id){
		this.playerSessions.put(session,id);
	}

	public void handleMessage(Session session, String message) {
		
		System.out.println("Game Manager handling: "+message + "from player " + playerSessions.get(session) );
		JsonReader reader = Json.createReader(new StringReader(message));
		JsonObject input = reader.readObject();
		
		//System.out.println("Game Manager JSON: "+input.getString("type"));
		
		reader.close();
		
		//Check to see what the "type" is and perform operations accordingly
		if(input.getString("type").equals("GET_SETUP")){
			JsonObjectBuilder obuilder = Json.createObjectBuilder();
			JsonArrayBuilder abuilder = Json.createArrayBuilder();
			obuilder.add("type","SETUP");
			
			for(Game g:games){
				if(g.isOpen()){
					abuilder.add(Json.createObjectBuilder().add("id",g.getId()).add("name", g.getName()));
				}
			}
			obuilder.add("games", abuilder);
			abuilder = Json.createArrayBuilder();
			
			for(Entry<Integer, String> e: game_board.getSuspects().entrySet()){	
				abuilder.add(Json.createObjectBuilder().add("id",e.getKey()).add("name", e.getValue()));
				
			}
			obuilder.add("suspects", abuilder);
			abuilder = Json.createArrayBuilder();

			for(Entry<Integer, String> e: game_board.getWeapons().entrySet()){
				abuilder.add(Json.createObjectBuilder().add("id",e.getKey()).add("name", e.getValue()));
				
			}
			obuilder.add("weapons", abuilder);
			abuilder = Json.createArrayBuilder();

			for(Entry<Integer, String> e: game_board.getRooms().entrySet()){
				abuilder.add(Json.createObjectBuilder().add("id",e.getKey()).add("name", e.getValue()));
				
			}
			obuilder.add("rooms", abuilder);

			try {
				session.getBasicRemote().sendText(obuilder.build().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(input.getString("type").equals("CREATE")){
			games.add(new Game(id++, input.getString("name"), playerSessions.get(session), input.getInt("suspect")));
			//System.out.println(input.getInt("suspect"));
			
		}else if(input.getString("type").equals("GET_SUSPECTS")){
			Game g = games.get(Integer.parseInt(input.getString("game")));
			
			System.out.println(g.getAvailableSuspects());
			JsonObjectBuilder obuilder = Json.createObjectBuilder();
			JsonArrayBuilder abuilder = Json.createArrayBuilder();
			obuilder.add("type","AVAIL_SUSPECTS");
			for (Entry<Integer,String> e : g.getAvailableSuspects().entrySet()){
				abuilder.add(Json.createObjectBuilder().add("id",e.getKey()).add("name", e.getValue()));
				
			}
			obuilder.add("suspects", abuilder);
			//System.out.println(obuilder.build().toString());
			try {
				session.getBasicRemote().sendText(obuilder.build().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else if(input.getString("type").equals("JOIN")){
			Game g = games.get(Integer.parseInt(input.getString("game")));
			g.addPlayer(playerSessions.get(session), input.getInt("suspect"));
			
		}else if(input.getString("type").equals("MOVE")){
			// Send 'game' with each msg.
			Game g = games.get(Integer.parseInt(input.getString("game")));
			g.makeMove();
		}else if(input.getString("type").equals("DISPROVE")){
			
		}else if(input.getString("type").equals("ACCUSE")){
			
		}else{
			System.out.println("Bad JSON"  + input);
		}
		
		
	}
}
