package game;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import java.util.Random;
import java.util.Vector;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


public class Game {

	private int unique_id;
	private String name;
	private GameBoard board;
	
	private Vector<Card> card_deck;
	private Vector<Card> case_file;
	private Vector<Player> players;
	private HashMap<Integer, String> available_suspects;
	
	
	/* TODO: Order of play is:
	 * scarlet, mustard, white, green, peacock, plum
	 * How should this be represented?
	 * 
	 */
	private static final Logger logger = LogManager.getLogger(Game.class);
	
	/* State variables */
	private boolean openGame = false;
	private Player current_player;
	private Player disproving_player = null;
	
	
	
	public Game(int id, String n, int h_id, int s_id){
		logger.info("Game Constructor");
		unique_id = id;
		name = n;
		board = new GameBoard();
		
		initCardDeck();	// Must happen after board is initialized
		initCaseFile();	// Must happen after card_deck is initialized
		players = new Vector<Player>();
		
		available_suspects = board.getSuspects();
		addPlayer(h_id, s_id);
		
		openGame = true;
		
		//TODO Set Current Player
	}
	
	public int getId() {
		return unique_id;
	}
	public String getName() {
		return name;
	}
	public Vector<Player> getPlayers() {
		return players;
	}
	public HashMap<Integer, String> getAvailableSuspects(){
		return available_suspects;
	}
	public GameBoard getGameBoard() {
		return board;
	}
	private void initCardDeck(){
		card_deck = new Vector<Card>();
		int id = 0;
		
		/*TODO: Note that instead of getting the id/name, we could just
		 * use the object... Decide later.
		 */
		for(Entry<Integer, String> e: board.getSuspects().entrySet()){	
			card_deck.add(new Card(GameComponentType.SUSPECT, e.getKey().intValue(), e.getValue()));
		}
		
		for(Entry<Integer, String> e: board.getWeapons().entrySet()){
			card_deck.add(new Card(GameComponentType.WEAPON, e.getKey().intValue(), e.getValue()));
			
		}
		for(Entry<Integer, String> e: board.getRooms().entrySet()){
			card_deck.add(new Card(GameComponentType.ROOM, e.getKey().intValue(), e.getValue()));
			
		}
		
	}
	 
	private void initCaseFile(){
		case_file = new Vector<Card>();
		case_file.add(getNextCardOfType(GameComponentType.SUSPECT));
		case_file.add(getNextCardOfType(GameComponentType.WEAPON));
		case_file.add(getNextCardOfType(GameComponentType.ROOM));
		
	}
	

	/*TODO: Get cards and assign them to players... once assigned, deck is empty */
	public Card getNextCard(){
		
		Random randomDeck = new Random();
		/*TODO: validate nextInt doesnt over reach index */
		if(card_deck.size() > 0){
			int next = randomDeck.nextInt(card_deck.size());
			Card card = card_deck.remove(next);
			return card;
		}else{
			return null;
		}
	}
	
	/* TODO: Use this function to select the case file */
	public Card getNextCardOfType(GameComponentType t){
		
		Random randomDeck = new Random();
		
		int next = randomDeck.nextInt(card_deck.size());
		while(card_deck.get(next).getType() != t){
			next = randomDeck.nextInt(card_deck.size());		
		}
		Card card = card_deck.remove(next);
		return card;
	}

	public boolean addPlayer(int playerID, int suspectID){
		/*TODO: what do we need to send in to add a player to this game?
		 * 1. Player ID - where GameServelet keeps ID + socket map,
		 * 	and game keeps ID + player object?
		 */
		if(players.size()< 6){ 
			players.add(new Player(playerID, suspectID));
			available_suspects.remove(suspectID);
			if(players.size() == 6){
				/* close game once max reached */
				openGame = false;
			}
			return true;
		}
		else{
			return false;
		}
		/*TODO: currently players are added in order of join making
		 * the host the first player always - if we want them in a different
		 * order then we need to do an additional step here. 
		 */
	
	}	
	
	public void dealCards(){
		/* TODO: Base logic is here, Validate this works! */
		boolean cont = true;
		do{
			for(Player p: players){
				Card c = getNextCard();
				if(c != null){
					p.addCard(c);
				}else{
					cont = false;
					break;
				}
			}
		}while(cont);
			
	}
	
	public boolean startGame(){
		//Should never happen, validation on front end
		if(players.size()>=2){
			openGame = false;
			
			System.out.println("Game Started!");
			dealCards();
			
						/* start the game... */
			// set current_player
			// call getNextMove() and send JSON to player
			// on player callback, makeMove(). 
			
			
			return true;
		}else{
			/* not enough people to start the game */
			return false;
		}
	}
	
	public boolean makeSuggestion(int s, int w, int r){
		boolean successful = false;
		/* TODO:
		 * 0. Only if disproving_player == null, can make a suggestion
		 * 1. Update board, move game components
		 * 2. Update state of game components (isOccupied = false for hallway starting point)
		 * 3. set disproving_player, send msg.
		 * 4. Start loop
		 * */
		
		return successful;
	}
	
	public boolean disproveSuggestion(int id){
		boolean successful = false;
		/* TODO:
		 * 0. Only if disproving_player != null, can disprove
		 * 1. compare input to "Suggestion"
		 * 2. Notify players based on response
		 * 3. move to next player unless suggesting player reached
		 * 4. if reached, set disproving player to null
		 */
		
		 /* If disprove successful OR reached end of loop, send back option to accuse */
		 JsonObject json= Json.createObjectBuilder().add("type", "TURN").add("options", "{}").build();
		 //TODO: Notify current_player of their next turn
		 
		return successful;

	}
	public boolean makeAccusation(JsonObject turn){
		boolean successful = false;
		/* TODO:
		 * 0. Only if disproving_player == null, can make a accusation
		 * 1. Compare accusation against case file
		 * 2. if valid, end game, notify players of win
		 * 3. if invalid, deactivate player, notify players of deactivation
		 */
		
		//All turns end with accusation - get next player
		current_player = nextPlayer();
		return successful;

	}
	
	/*TODO: where are available moves identified... it changes based on
	 * game state...
	 */
	public JsonObject makeMove(JsonObject turn){
		JsonObject json= null;
		// { location: 3, suggestion:{suspect:2, weapon 4, location: 6}}

		if(disproving_player == null){
			/*TODO:
			 * 0. Only if disproving_player == null, can make a move
			 * 1. Update board with move
			 * 2. Notify all players
			 * 3. If suggestion made, call make suggestion.
			 */	
			Suspect cur_suspect = board.getSuspectById(current_player.getSuspectId());
			int l_id=cur_suspect.getLocation().getId();
			if(turn.containsKey("location")){
				l_id = turn.getInt("location");
				boolean valid = false;
				for(Location l: cur_suspect.getLocation().getAvailableLocationMoves()){
					if(l_id == l.getId()){
						valid = true;
					}
				}
				if(valid){
					if(!cur_suspect.getLocation().isRoom()){
						cur_suspect.getLocation().setOccupied(false);
					}
					cur_suspect.setLocation(board.getLocationById(l_id));
					if(!cur_suspect.getLocation().isRoom()){
						cur_suspect.getLocation().setOccupied(true);
					}
					//TODO: Notify Players of board
				}else{
					//TODO Error!
				}
			}
			if(turn.containsKey("suggestion")){
				JsonObject suggestion = turn.getJsonObject("suggestion");
				
				if(suggestion.containsKey("location") && 
						suggestion.containsKey("suspect") &&
						suggestion.containsKey("weapon") &&
						l_id == suggestion.getInt("location")){
					Suspect s= board.getSuspectById(suggestion.getInt("suspect"));
					s.getLocation().setOccupied(false);
					s.setLocation(board.getLocationById(l_id));
					if(!s.getLocation().isRoom()){
						s.getLocation().setOccupied(true);
					}
					Weapon w = board.getWeaponById(suggestion.getInt("weapon"));
					w.setLocation(board.getLocationById(l_id));
					
					//TODO Notify Players of board updates & suggestion
					//TODO Start Disprove Process
					
				}else{
					//TODO Error!

				}
			}else{

				/* If no suggestion made, send back option to accuse */
				 json= Json.createObjectBuilder().add("type", "TURN").add("options", "{}").build();
				 //TODO: Notify current_player of their next turn
			}

		}else{
			//TODO Error!
		}
				
		return json;
	
	}
	
	

	/*First Move
	 * check to see if suspect is in room
	 *  check to see if player is there by suggestion
	 *  check to see if hallways occupied
	 *  options: move to empty hallway/room
	 *  		make suggestion (if there by suggestion)
	 *  		make accusation
	 * check to see if suspect is in hallway
	 * 	options: move to available rooms, make suggestion
	 * 			make accusation
	 * 
	 *Second Move
	 * Make accusation
	 * 
	 */

	public JsonObject getNextMove(){
		Suspect s = board.getSuspectById(current_player.getSuspectId());

		//JSON: { type: TURN, options:{ location:[{id:1, name:"H1", room:false}, ...], suggestion:true} //accusation is always true

		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder array = Json.createArrayBuilder();
		for(Location l:s.getLocation().getAvailableLocationMoves()){
				array.add(Json.createObjectBuilder().add("id", l.getId()).add("name", l.getName()).add("room", l.isRoom()));
		}
		builder.add("location", array);
		builder.add("suggestion", (s.getLocation().isRoom() && s.wasMovedBySuggestion()) );
		
		JsonObject json = Json.createObjectBuilder().add("type", "TURN").add("options", builder).build();
		return json;
		
	}
	
	public Player nextPlayer(){
		/* TODO: Move current player to next player & notify them*/
		int index = players.indexOf(current_player);
		return players.get( (index+1)%players.size() );
	}
	public boolean isOpen(){
		return openGame;
	}

	
	
}
