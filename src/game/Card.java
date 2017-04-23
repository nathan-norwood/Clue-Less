package game;

/* It seems like it would be useful to keep the friendly names
 * for each of these in the GamePieces object, and use an ID to 
 * reference them... this way the same suspect ID used here to 
 * reference the name of the suspect could be used in Player to
 * reference the suspect being played... 
 */

public class Card {
	private CardType type;
	private int unique_id;
	private String name;
	
	private static Integer cnt;
	
	public Card(CardType t, int i, String n){
		setId(i);
		setType(t);
		setName(n);
	}

	public CardType getType() {
		return type;
	}

	private void setType(CardType type) {
		this.type = type;
	}

	public int getId() {
		return unique_id;
	}

	private void setId(int unique_id) {
		this.unique_id = unique_id;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

}
