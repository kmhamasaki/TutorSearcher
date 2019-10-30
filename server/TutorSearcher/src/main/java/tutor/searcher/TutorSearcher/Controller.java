package tutor.searcher.TutorSearcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "spring.", name = "not-testing")
public class Controller {
	
	@Autowired
	private DBConnect dbConnect;
	
	private static Hashtable<RequestThread, Socket> requestThreadsSockets = new Hashtable<RequestThread, Socket>();

	int port = 6789;

	private static ServerSocket ss = null;

	@PostConstruct
	void startController() {
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

	@SuppressWarnings("unchecked")
	void processRequest(Request request, RequestThread requestThread) {

		HashMap<String, Object> respAttr = new HashMap<String, Object>();
		String respType = "";
		requestThreadsSockets.remove(requestThread);
		
		/** 
		 * Signup
		 * Incoming requestType: "email" 
		 * Incoming attributes -- (Should this be a User object?)
		 * 	String "email"
		 *	String "passwordHash"
		 *	String "firstName"
		 *	String "lastName"
		 *	String "phoneNumber"
		 *	String "accountType" - ("tutor" or "tutee")
		 *	List<Integer> "availability"
		 * Outgoing requestTypes
		 *	"Error: email exists"
		 *	"Error: not USC email"
		 *	"Success"
		 * Outgoing attributes
		 *  String "userID"
		 */		
		if(request.getRequestType().equals("signup")) {
			// If not a USC email, return error
			if(((String)request.get("email")).indexOf("@usc.edu") == -1) {
				respType = "Error: not USC email";
				System.out.println((String)request.get("email"));
			} else {
				// get UserID to send back
				int userID = dbConnect.addUser((String)request.get("email"), 
						(String)request.get("passwordHash"), 
						(String)request.get("firstName"), 
						(String)request.get("lastName"), 
						(String)request.get("phoneNumber"),
						request.get("accountType") == "tutor" ? true : false,
						(List<Integer>)request.get("availability"));
				
				// if not successful in adding it
				if(userID == -1) {
					respType = "Error: email exists";
				} else {
					respType = "Success";
					respAttr.put("userID", Integer.toString(userID));
					if(request.get("accountType") == "tutor") {
				    for (String className : (List<String>) request.get("classes")) {
				        dbConnect.addTutorToClass(userID, className);
				    }
					}
				}
			}
		} 
		
		/** 
		 * Login
		 * Incoming requestType: "login"
		 * Incoming attributes
		 *  String "email"
		 *  String "passwordHash"
		 * Outgoing requestTypes:
		 *  "Error: wrong email or password"
		 *  "Success"
		 */
		else if (request.getRequestType() == "login") {
			User user = dbConnect.authenticate((String)request.get("email"), 
					request.get("passwordHash").toString());
			if(user == null) {
				respType = "Error: wrong email or password";
			} else {
				respType = "Success";
				respAttr.put("User", user);
			}
		} else if (request.getRequestType() == "updateinfo") {
			
			
		} else if (request.getRequestType() == "search") {

		} else if (request.getRequestType() == "newrequest") {
			
		} else if (request.getRequestType() == "viewrequests") {
			
		}
		System.out.print(respType);
		requestThread.sendResponse(new Request(respType, respAttr));

		return;
	}

}