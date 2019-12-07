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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "spring.", name = "not-testing")
public class Controller {
	
	@Autowired
	protected DBConnect dbConnect;
	
	private static Hashtable<RequestThread, Socket> requestThreadsSockets = new Hashtable<RequestThread, Socket>();

	int testing_port = 6780;
	int port = 6789;
	private static ServerSocket ss = null;
	boolean run = true;
	
	@Value("${spring.datasource.url}")
	private String url;

	public void useTestingPort() {
		port = testing_port;
	}

	@PostConstruct
	public void startController() {
		System.out.println("Launching --Controller--");
		System.out.println("using url: " + url);
		System.out.println();
		
		try {
			System.out.println("Attempting to bind to port " + port);
			ss = new ServerSocket(port);
			System.out.println("Successfully bound to port " + port);
			System.out.println();

		} catch (IOException e) {

//			System.out.println("Unable to bind to port " + port);
//			System.out.println();
		}

		while (this.run) {
			Socket controllerThreadsSocket = null;
			try {
				controllerThreadsSocket = ss.accept();
			} catch (IOException e) {
//				e.printStackTrace();
			}

			// Keep track of the sockets and threads to effectively log out user
			RequestThread rt = new RequestThread(controllerThreadsSocket, this);
			requestThreadsSockets.put(rt, controllerThreadsSocket);
		}
//
	}

	public void closeServer() throws IOException {
		this.run = false;
		ss.close();
		System.out.println("Server closed");
	}

	Request processRequest(Request request, RequestThread requestThread) throws IOException {
//		System.out.println("Process Request Type: " + request.getRequestType());
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
			String bio = (String)request.get("bio");

			int userID = dbConnect.addUser(email, passwordHash, firstName, lastName, phoneNumber, accountType, bio);

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
		} else if (request.getRequestType().equals("updateinfo")) {
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
		 * 	int userID - tutee makign the search
		 * 	String availability - availability as a string, separated by spaces
		 *  String className
		 * outgoing attributes:
		 * 	String responseType "Success" 
		 * 	List<Tutor> results 
		 */
		else if (request.getRequestType().equals("search")) {
			ArrayList<Integer> times = (ArrayList<Integer>)request.getAttributes().get("availability");
			String className = (String)request.getAttributes().get("className");
			List<Tutor> tutors = dbConnect.searchTutors((int)request.get("userID"), times, className);
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
		 *  int requestID (-1 if cannot make request for that time) 
		 */
		else if (request.getRequestType().equals("newrequest")) {
			int tuteeID = (int)request.getAttributes().get("tuteeID");
			int tutorID = (int)request.getAttributes().get("tutorID");
			String className = (String)request.getAttributes().get("className");
			String time = (String)request.getAttributes().get("time");
			//int status = (int)request.getAttributes().get("status");
			int requestID = -1;
			try {
				requestID = dbConnect.addRequest(tuteeID, tutorID, className, time, 0);

			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				respType = "Error " + e.getMessage();
			}
			respType = "Success";
			respAttr.put("requestID", requestID);
			
		} 
		/**
		 * requestType "viewrequests"
		 * incoming attributes
		 * 	int userID
		 * String viewrequeststype - tuteeapproved, tuteerejected, tutorpending, tutorapproved
		 *  outgoing attributes
		 *  String respType 
		 *  List<TutorRequest> requests
		 */
		else if (request.getRequestType().equals("viewrequests")) {
			int userID = (int)request.getAttributes().get("userID");
			String type = (String)request.get("viewrequeststype");
			List<TutorRequest> requests = null;
			if (type.equals("tuteeapproved")) {
				requests = dbConnect.getRequestsTuteeApproved(userID);
			}
			else if (type.equals("tutorpending")) {
				requests = dbConnect.getRequestsTutorUnapproved(userID);
			}
			else if (type.equals("tutorapproved")) {
				requests = dbConnect.getRequestsTutorApproved(userID);
			}
			else if (type.equals("tuteerejected")) {
				requests = dbConnect.getRequestsTuteeRejected(userID);
			}
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
			respType = "Success";
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
			respType = "Success";
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
			respType = "Success";
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
			respType = "Success";
		}
		/**
		 * requestType "getavailability"
		 * incoming attributes
		 * 	int tutorID
		 * outgoing attributes
		 * 	ArrayList<Integer> availability
		 */
		else if (request.getRequestType().equals("getavailability")) {
			ArrayList<Integer> availability = dbConnect.getTutorAvailability((int)request.get("tutorID"));
			respAttr.put("availability", availability);
			respType = "Success";
		}
		/**
		 * requestType "getclasses"
		 * gets tutor's classes
		 * incoming attributes
		 * 	int tutorID
		 * outgoing attributes
		 * 	ArrayList<String> classes
		 */
		else if (request.getRequestType().equals("getclasses")) {
			ArrayList<String> classes = dbConnect.getTutorClasses((int)request.get("tutorID"));
			respAttr.put("classes", classes);
			respType = "Success";
		}
		
		/**
		 * requestType "searchprevious"
		 * gets user's last search preferences and performs search
		 * incoming attributes
		 * 	int userID
		 * outgoing attributes
		 * 	List<Tutor> results
		 */
		else if (request.getRequestType().equals("searchprevious")) {
			List<Tutor> results = dbConnect.searchTutorsPrevious((int)request.get("userID"));
			respAttr.put("results", results);
			respType = "Success";
		}
		/**
		 * requestType "getuserinfo"
		 * gets user's information
		 * incoming attributes
		 * 	int userID
		 * outoing attributes
		 * 	User user
		 */
		else if (request.getRequestType().equals("getuserinfo")) {
			User user = dbConnect.getUserInformation((int)request.get("userID"));
//			System.out.println("userid" + (int)request.get("userID"));
//			System.out.println("got user info for " + user.getFirstName());

			respAttr.put("user", user);
			respType = "Success";
		}
		
		/**
		 * requestType "addrating"
		 * adds rating
		 * incoming attributes
		 * 	int userID - userID of the person to be rated
		 * 	double rating - new rating
		 * 
		 */
//		else if (request.getRequestType().equals("addrating")) {
//			dbConnect.addRating((int)request.get("userID"), (double)request.get("rating"));
//			respType = "Success";
//		}
		/**
		 * requestType "updatetutorrating"
		 * this is the TUTEE giving TUTOR a rating
		 * incoming attributes
		 * 	int requestID
		 * 	int rating - new rating
		 */
		else if (request.getRequestType().equals("updatetutorrating")) {
			int requestID = (int)request.get("requestID");
			int rating = (int)request.get("rating");
			dbConnect.updateRating(requestID, rating, true);
		}
		/**
		 * requestType "updatetuteerating"
		 * this is the TUTOR giving TUTEE a rating
		 * incoming attributes
		 * 	int requestID
		 * 	int rating - new rating
		 */
		else if (request.getRequestType().equals("updatetuteerating")) {
			int requestID = (int)request.get("requestID");
			int rating = (int)request.get("rating");
			dbConnect.updateRating(requestID, rating, false);

		}
		/**
		 * requestType "gettotalrating"
		 * get the user's rating
		 * incoming attributes
		 * 	int userID 
		 * 	boolean accountType (0 if tutee 1 if tutor)
		 * returns
		 * 	double rating
		 */
		else if (request.getRequestType().equals("gettotalrating")) {
			int userID = (int)request.get("userID");
			boolean accountType = (boolean)request.get("accountType");
			double rating = 0;
			if (accountType) {
				rating = dbConnect.getTotalTutorRating(userID);

			}
			else {
				rating = dbConnect.getTotalTuteeRating(userID);
			}
			
			respAttr.put("rating", rating);
			respType = "Success";
		}

		/**
		 * requestType "addbio"
		 * adds/updates bio (overwrites it)
		 * incoming attribute
		 * 	String bio
		 */
		else if (request.getRequestType().equals("addbio")) {
			String bio = (String)request.get("bio");
			int userID = (int)request.get("userID");
			dbConnect.addBio(bio, userID);
			respType = "Success";
		}
		/**
		 * requestType "getbio"
		 * gets bio 
		 * incoming attribute
		 * 	int userID
		 */
		else if (request.getRequestType().equals("getbio")) {
			int userID = (int)request.get("userID");
			String bio = dbConnect.getBio(userID);
			respAttr.put("bio", bio);
			respType = "Success";
		}
		/**
		 * requestType "updateProfilePicture"
		 * Updates profile picture
		 * incoming attribute
		 * 	int userID
		 * 	byte[] profilePicBlob
		 */
		else if (request.getRequestType().equals("updateProfilePicture")) {
			int userID = (int)request.get("userID");
			byte[] profilePicBlob = (byte[]) request.get("profilePicBlob");

			dbConnect.updateProfilePicture(userID, profilePicBlob);

			respType = "Success";
		}
//		System.out.println(respType);

		Request response = new Request(respType, respAttr);
		requestThread.sendResponse(response);

		return response;
	}

}