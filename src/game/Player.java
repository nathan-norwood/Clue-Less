package game;

import java.util.Vector;

public class Player {
	private int unique_id;
	private int suspect_id; /*TODO Currently set in GameBoard*/
	private boolean active = false;
	
	private Vector<Card> cards;
	
	public Player (int id, int s_id){
		unique_id = id;
		suspect_id=s_id;
		active = true;
		
		cards = new Vector<Card>();
	}
	
	public void addCard( Card c ){
		cards.add(c);
		
	}

}
