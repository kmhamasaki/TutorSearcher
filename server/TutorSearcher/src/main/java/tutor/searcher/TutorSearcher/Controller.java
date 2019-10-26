package tutor.searcher.TutorSearcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;

public class Controller {
	private DBConnect dbConnect;
	private static Hashtable<RequestThread, Socket> requestThreadsSockets = new Hashtable<RequestThread, Socket>();

	int port = 6789;

	private static ServerSocket ss = null;

	Controller() {
		System.out.println("Launching --Controller--");
		System.out.println();

		try {

			System.out.println("Attempting to bind to port " + port);
			ss = new ServerSocket(port);
			System.out.println("Successfully bound to port " + port);
			System.out.println();

		} catch (IOException e) {

			System.out.println("Unable to bind to port " + port);
			System.out.println();
		}

		while (true) {
			Socket controllerThreadsSocket = null;
			try {
				controllerThreadsSocket = ss.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Keep track of the sockets and threads to effectively log out user
			RequestThread rt = new RequestThread(controllerThreadsSocket, this);
			requestThreadsSockets.put(rt, controllerThreadsSocket);
		}
	}

	void processRequest(Request request, RequestThread requestThread) {

		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("hello", "world");

		Request request2 = new Request("sendBackFromServer!!!!", attributes);

		requestThread.sendResponse(request2);

		requestThreadsSockets.remove(requestThread);
		if(request.getRequestType() == "signup") {
			
			// return User object to send back
			int userID = dbConnect.addUser(request.get("email"), request.get("passwordHash"), 
					request.get("name"), request.get("accountType") == "tutor" ? true : false);
			
			// if not successful in adding it
			if(userID == -1) {
				// send error message to the frontend
				return;
			}
			
			// if tutor add classes, expecting classes as strings separated by spaces
			if(request.get("accountType") == "tutor") {
				String[] classes = request.get("classes").split(" "); 
        for (String className : classes) {
        	dbConnect.addTutorToClass(userID, className);
        }
			}
		} else if (request.getRequestType() == "login") {
			dbConnect.authenticate(request.get("email"), request.get("passwordHash"));
		} else if (request.getRequestType() == "updateinfo") {
			
		} else if (request.getRequestType() == "search") {
			
		} else if (request.getRequestType() == "newrequest") {
			
		} else if (request.getRequestType() == "viewrequests") {
			
		}
		return;
	}

}