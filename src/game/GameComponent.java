package game;

public abstract class GameComponent {

	private int unique_id;
	private String name;
	
	public GameComponent(int i, String s) {
		unique_id =i;
		name = s;
	}

	public boolean hasID(int id) {
		if(id == unique_id){
			return true;
		}
		return false;
	}
	
	public int getId(){
		return unique_id;
	}
	
	public String getName(){
		return name;
	}
}
