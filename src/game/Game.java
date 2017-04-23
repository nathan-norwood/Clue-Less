package game;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Vector;

public class Game {

	private int unique_id;
	private String name;
	private Vector<Card> card_deck;
	private Vector<Card> case_file;
	private Vector<Player> players;
	private GameBoard board;
	
	/* TODO: Order of play is:
	 * scarlet, mustard, white, green, peacock, plum
	 * How should this be represented?
	 * 
	 */
	
	
	/* State variables */
	private boolean openGame;
	private Player current_player;
	private HashMap<Integer, String> available_suspects;
	
	
	public Game(int id, String n, int h_id, int s_id){
		unique_id = id;
		name = n;
		board = new GameBoard();
		available_suspects = board.getSuspects();
		initCardDeck();	// Must happen after board is initialized
		initCaseFile();	// Must happen after card_deck is initialized
		
		addPlayer(h_id, s_id);
		
		openGame = true;

	}
	
	public int getId() {
		return unique_id;
	}

	private void initCardDeck(){
		card_deck = new Vector<Card>();
		int id = 0;
		
		/*TODO: Note that instead of getting the id/name, we could just
		 * use the object... Decide later.
		 */
		for(Entry<Integer, String> e: available_suspects.entrySet()){	
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
		int next = randomDeck.nextInt(card_deck.size());
		Card card = card_deck.remove(next);
		return card;
		
		
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
		 * order then we need to do an additional step here 
		 */
	
	}	
	
	public void configureGame(){
		/* TODO: assign cards to players */
		
		
	}
	
	public boolean startGame(){
		
		if(players.size()>=2){
			openGame = false;
			
			/* start the game... */
			
			return true;
		}else{
			/* not enough people to start the game */
			return false;
		}
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

	public boolean isOpen(){
		return openGame;
	}
	
	
	
}
