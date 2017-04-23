package game;

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
