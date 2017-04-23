package game;

import java.util.Random;
import java.util.Vector;

public class Game {

	private int unique_id;
	private Vector<Card> card_deck;
	private Vector<Card> case_file;
	private Vector<Player> players;
	
	public Game(int id){
		unique_id = id;
		initCardDeck();	
		initCaseFile();
	}
	
	public int getId() {
		return unique_id;
	}

	private void initCardDeck(){
		card_deck = new Vector<Card>();
		int id = 0;
		card_deck.add(new Card(CardType.SUSPECT, id++, "Miss Scarlet"));
		card_deck.add(new Card(CardType.SUSPECT, id++, "Colonel Mustard"));
		card_deck.add(new Card(CardType.SUSPECT, id++, "Professor Plum"));
		card_deck.add(new Card(CardType.SUSPECT, id++, "Mr. Green"));
		card_deck.add(new Card(CardType.SUSPECT, id++, "Mrs. White"));
		card_deck.add(new Card(CardType.SUSPECT, id++, "Mrs. Peacock"));
		
		card_deck.add(new Card(CardType.WEAPON, id++, "Rope"));
		card_deck.add(new Card(CardType.WEAPON, id++, "Lead Pipe"));
		card_deck.add(new Card(CardType.WEAPON, id++, "Knife"));
		card_deck.add(new Card(CardType.WEAPON, id++, "Wrench"));
		card_deck.add(new Card(CardType.WEAPON, id++, "Candlestick"));
		card_deck.add(new Card(CardType.WEAPON, id++, "Revolver"));
		
		card_deck.add(new Card(CardType.ROOM, id++, "Study"));
		card_deck.add(new Card(CardType.ROOM, id++, "Hall"));
		card_deck.add(new Card(CardType.ROOM, id++, "Lounge"));
		card_deck.add(new Card(CardType.ROOM, id++, "Library"));
		card_deck.add(new Card(CardType.ROOM, id++, "Billiard Room"));
		card_deck.add(new Card(CardType.ROOM, id++, "Dining Room"));
		card_deck.add(new Card(CardType.ROOM, id++, "Conservatory"));
		card_deck.add(new Card(CardType.ROOM, id++, "Ballroom"));
		card_deck.add(new Card(CardType.ROOM, id++, "Kitchen"));
	
		
	}
	 
	private void initCaseFile(){
		case_file = new Vector<Card>();
		case_file.add(getNextCardOfType(CardType.SUSPECT));
		case_file.add(getNextCardOfType(CardType.WEAPON));
		case_file.add(getNextCardOfType(CardType.ROOM));
		
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
	public Card getNextCardOfType(CardType t){
		
		Random randomDeck = new Random();
		
		int next = randomDeck.nextInt(card_deck.size());
		while(card_deck.get(next).getType() != t){
			next = randomDeck.nextInt(card_deck.size());		
		}
		Card card = card_deck.remove(next);
		return card;
		
		
	}

	public void addPlayer(int playerID, int suspectID){
		/*TODO: what do we need to send in to add a player to this game?
		 * 1. Player ID - where GameServelet keeps ID + socket map,
		 * 	and game keeps ID + player object?
		 */
		 
		players.add(new Player(playerID, suspectID));

		/*TODO: currently players are added in order of join making
		 * the host the first player always - if we want them in a different
		 * order then we need to do an additional step here 
		 */
	
	}	
	
	public void configureGame(){
		/* TODO: assign cards to players */
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
