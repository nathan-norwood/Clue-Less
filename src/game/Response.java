package game;
import javax.json.JsonObject;

public class Response {

	private int session_id; //session_id=0 means all players
	
	public int getSession_id() {
		return session_id;
	}
	public void setSession_id(int session_id) {
		this.session_id = session_id;
	}
	
	private JsonObject msgs;
	
	public JsonObject getMsgs() {
		return msgs;
	}
	public void setMsgs(JsonObject msgs) {
		this.msgs = msgs;
	}
	
	
}
