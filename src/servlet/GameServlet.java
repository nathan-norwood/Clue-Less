package servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import game.GameManager;

/**
 * Servlet implementation class GameServlet
 */

public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private GameManager game_manager;
	
	/* PlayerID, Socket for Player */
	//private HashMap<Integer, Socket> players;
    
	/**
     * Default constructor. 
     */
    public GameServlet() {
    	super();
    	game_manager = GameManager.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * On Get - send back the base page. Should have the ability to add
		 * game or join game.
		 * 
		 */
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
