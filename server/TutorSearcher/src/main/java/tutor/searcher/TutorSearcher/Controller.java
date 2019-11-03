package tutor.searcher.TutorSearcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
		System.out.println(request.getRequestType());
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
		 *	"Success"
		 * Outgoing attributes
		 *  String "userID"
		 */		
		if(request.getRequestType().equals("signup")) {
			// get UserID to send back
			String email = (String) request.get("email");
			String passwordHash = (String)request.get("passwordHash");
			String firstName = (String)request.get("firstName");
			String lastName = (String)request.get("lastName");
			String phoneNumber = (String)request.get("phoneNumber");

			Boolean accountType = (Boolean)request.get("accountType");
			int userID = dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType);

			// if not successful in adding it
			if(userID == -1) {
				respType = "Error: email exists";
			} else {
				respType = "Success";
				respAttr.put("userID", Integer.toString(userID));
				
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
		else if (request.getRequestType().equals("login")) {
			User user = dbConnect.authenticate((String)request.get("email"), 
					request.get("passwordHash").toString());
			if(user == null) {
				respType = "Error: wrong email or password";
			} else {
				respType = "Success";
				respAttr.put("User", user);
			}
		/**
		 * incoming requestType: "updateinfo"
		 * updates user profile information
		 * expects a User object filled with *all variables below* filled, as it will
		 * overwrite DB information based on what is in this user object
		 * incoming attributes
		 * 	User user
		 * inside the user object:
		 * private String firstName;
			private String lastName;
			private String phoneNumber;
			private String passwordHash;
			
			outgoing attributes:
				String error message "Error updating information"
				or String "success"
		 */
		} else if (request.getRequestType() == "updateinfo") {
			User user = (User)request.getAttributes().get("user");
			try {
				dbConnect.updateUserInformation(user);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				respType = "Error updating information";
				//return;
			}
			
			respType = "Success";
			
		} 
		/**
		 * requestType: "search"
		 * searches for tutors that match class & availability 
		 * incoming attributes:
		 * 	String availability - availability as a string, separated by spaces
		 *  String className
		 * outgoing attributes:
		 * 	String responseType "Success" 
		 * 	List<Tutor> tutors 
		 */
		else if (request.getRequestType().equals("search")) {
			String availability = (String)request.getAttributes().get("availability");
			String[] timesStr = availability.split(" ");
			List<Integer> times = new ArrayList<>();
			for (int i = 0; i < timesStr.length; i++) {
				times.add(Integer.parseInt(timesStr[i]));
			}
			String className = (String)request.getAttributes().get("className");
			List<Tutor> tutors = dbConnect.searchTutors(times, className);
			respType = "Success";
			respAttr.put("results", tutors);

		} 
		/**
		 * requestType "newrequest"
		 * incoming attributes
		 * 	int tuteeID
		 * 	int tutorID
		 * 	String className
		 * 	String time
		 *  outgoing attributes
		 *  String respType
		 */
		else if (request.getRequestType().equals("newrequest")) {
			int tuteeID = (int)request.getAttributes().get("tuteeID");
			int tutorID = (int)request.getAttributes().get("tutorID");
			String className = (String)request.getAttributes().get("className");
			String time = (String)request.getAttributes().get("time");
			//int status = (int)request.getAttributes().get("status");
			try {
				dbConnect.addRequest(tuteeID, tutorID, className, time, 0);

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				respType = "Error " + e.getMessage();
			}
			respType = "Success";
			
		} 
		/**
		 * requestType "viewrequests"
		 * incoming attributes
		 * 	int userID
		 *  outgoing attributes
		 *  String respType 
		 *  List<TutorRequest> requests
		 */
		else if (request.getRequestType().equals("viewrequests")) {
			int userID = (int)request.getAttributes().get("userID");
			List<TutorRequest> requests = dbConnect.getRequests(userID);
			respType = "Success";
			respAttr.put("requests", requests);
		} 
		/**
		 * requestType "updaterequeststatus" 
		 * incoming attributes
		 * int requestID
		 * int status ( 0 = waiting approval, 1 = approved, 2 = rejected ) 
		 * 
		 */
		else if (request.getRequestType().equals("updaterequeststatus")) {
			int requestID = (int)request.getAttributes().get("requestID");
			int newStatus = (int)request.getAttributes().get("newStatus");
			dbConnect.updateRequestStatus(requestID, newStatus);
		}
		/**
		 * requestType "updateavailability"
		 * incoming attributes
		 * 	int tutorID
		 * 	List<Integer> availability 
		 * 
		 */
		else if (request.getRequestType().equals("updateavailability")) {
			int tutorID = (int)request.getAttributes().get("tutorID");
			List<Integer> availability = (List<Integer>)request.getAttributes().get("availability");
			dbConnect.updateTutorAvailability(tutorID, availability);
			
		}
		/**
		 * requestType "addclass"
		 * incoming attributes
		 * 	int tutorID
		 * 	ArrayList<String> className
		 */
		else if (request.getRequestType().equals("addclass")) {
			int tutorID = (int)request.getAttributes().get("tutorID");
			ArrayList<String> className = (ArrayList<String>)request.get("className");
			dbConnect.addTutorToClass(tutorID, className);
		}
		/**
		 * requestType "removeclass"
		 * incoming attributes
		 * 	int tutorID
		 * 	String className
		 */
		else if (request.getRequestType().equals("removeclass")) {
			int tutorID = (int)request.getAttributes().get("tutorID");
			String className = (String)request.getAttributes().get("className");
			dbConnect.removeTutorFromClass(tutorID, className);
		}
		System.out.print(respType);
		requestThread.sendResponse(new Request(respType, respAttr));
 
		return;
	}

}