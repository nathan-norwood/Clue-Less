package game;

import java.util.HashMap;
import java.util.Map.Entry;
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
		
		return successful;

	}
	public boolean makeAccusation(int s, int w, int r){
		boolean successful = false;
		/* TODO:
		 * 0. Only if disproving_player == null, can make a accusation
		 * 1. Compare accusation against case file
		 * 2. if valid, end game, notify players of win
		 * 3. if invalid, deactivate player, notify players of deactivation
		 */
		
		//All turns end with accusation - get next player
		return successful;

	}
	
	/*TODO: where are available moves identified... it changes based on
	 * game state...
	 */
	public void makeMove(){
		/*TODO:
		 * 0. Only if disproving_player == null, can make a move
		 * 1. Update board with move
		 * 2. Notify all players
		 * 3. If suggestion made, call make suggestion.
		 */
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

	public void getNextMove(){
		Suspect s = board.getSuspectById(current_player.getSuspectId());

		if(s.getLocation().isRoom()){
			for(Location l:s.getLocation().getAvailableLocationMoves()){
				if(l.isRoom()){
					//add Room options
				}else{
					//add Hallway options
				}
				
				if(s.wasMovedBySuggestion()){
					//add option to Suggest in current location
				}
			}
		}
		else{ //hallway
			for(Location l:s.getLocation().getAvailableLocationMoves()){
				//add Room options
			}	
		}
		//add make accusation option
	}
	
	public void nextPlayer(){
		/* TODO: Move current player to next player & notify them*/
	}
	public boolean isOpen(){
		return openGame;
	}

	
	
}
