package servlet;

import java.io.IOException;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/socket")
public class WebSocketHandler {
	private Session session;
	@OnOpen
	public void onOpen(Session session){
		this.session = session;
	}
	@OnMessage
	public void onMessage(String message){
		System.out.println(message);
		sendMessage(message + " Server Says hi");
	}
	
	
	public void sendMessage(String message){
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}
	
}
