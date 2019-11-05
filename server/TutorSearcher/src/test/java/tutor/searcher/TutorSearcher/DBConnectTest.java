package tutor.searcher.TutorSearcher;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DBConnectTest {

	@Autowired
	private DBConnect dbConnect;
	private Connection conn;
	
	public DBConnectTest() {

	}
	
//	@Test
//	void addTutorToClassTest() {
//		int tutorID = 15;
//		ArrayList<String> classes = new ArrayList<>();
//		classes.add("CSCI 201");
//		classes.add("CSCI 270");
//		classes.add("CSCI 356");
//		dbConnect.addTutorToClass(tutorID, classes);
//		
//	}
//	@Test
//	void addRequest() {
//		int tuteeID = 4;
//		int tutorID = 15;
//		String className = "CSCI 401";
//		String time = "15";
//		int status = 0;
//		//dbConnect.addRequest(tuteeID, tutorID, className, time, status);
//	}
//	
	@Test
	void getRequestsTuteeApprovedTest() {
		System.out.println("tutee approved");
		int tuteeID = 18;
		List<TutorRequest> requests = dbConnect.getRequestsTuteeApproved(tuteeID);
		for (int i = 0; i < requests.size(); i++) {
			System.out.println("tutor: " + requests.get(i).getTutorName());
			System.out.println("class: " + requests.get(i).getClassName());
			System.out.println("tutor rating: " + requests.get(i).getTutorRating());
			System.out.println("tutee rating: " + requests.get(i).getTuteeRating());
		}
	}
	
	@Test
	void getRequestsTutorApprovedTest() {
		System.out.println("tutorapproved");
		int tutorID = 17;
		List<TutorRequest> requests = dbConnect.getRequestsTutorApproved(tutorID);
		for (int i = 0; i < requests.size(); i++) {
			System.out.println("tutor: " + requests.get(i).getTuteeName());
			System.out.println("class: " + requests.get(i).getClassName());
			System.out.println("tutor rating: " + requests.get(i).getTutorRating());
			System.out.println("tutee rating: " + requests.get(i).getTuteeRating());
		}
	}
	
	@Test
	void getRequestsTutorUnapprovedTest() {
		System.out.println("tutor pending");
		int tutorID = 17;
		List<TutorRequest> requests = dbConnect.getRequestsTutorUnapproved(tutorID);
		for (int i = 0; i < requests.size(); i++) {
			System.out.println("tutor: " + requests.get(i).getTuteeName());
			System.out.println("class: " + requests.get(i).getClassName());
			System.out.println("tutor rating: " + requests.get(i).getTutorRating());
			System.out.println("tutee rating: " + requests.get(i).getTuteeRating());
		}
	}
//	
//	@Test
//	void searchTutorTest() {
//		System.out.println("SEARCH TUTOR TEST!!!!");
//		ArrayList<Integer> times = new ArrayList<>();
//		times.add(1);
//		times.add(2);
//		times.add(3);
//		times.add(4);
//		String className = "CSCI 103";
//		int userID = 4;
//		List<Tutor> results = dbConnect.searchTutors(userID, times, className);
//		for (int i = 0; i < results.size(); i++) {
//			System.out.println(results.get(i).getEmail());
//		}
//		
//	}
	
	@Test
	void addRatingTest() {
		int userID = 17;
		double rating = 5; 
		dbConnect.addRating(userID, rating);
	}
//	@Test
//	void updateRequestStatusTest() {
//		int requestID = 2;
//		int newStatus = 1;
//		dbConnect.updateRequestStatus(requestID, newStatus);
//	}
//	
//	@Test
//	void updateTutorAvailability() {
//		int userID = 6;
//		List<Integer> availability = Arrays.asList(5, 9, 10);
//		dbConnect.updateTutorAvailability(userID, availability);
//	}
//	
//	@Test
//	void searchTutorsPreviousTest() {
//		System.out.println("SEARCH TUTORS PREVIOUS TEST!!");
//		int userID = 4;
//		List<Tutor> results = dbConnect.searchTutorsPrevious(userID);
//		for (Tutor t : results) {
//			System.out.println(t.getEmail());
//		}
//	}
//	
//	@Test
//	void getUserInformationTest() {
//		System.out.println("GETU SER INFO TEST ?????????????????????????????????????");
//		int userID = 1;
//		User user = dbConnect.getUserInformation(userID);
//		System.out.println(user.getEmail());
//		System.out.println(user.getFirstName() + " " + user.getLastName());
//	}
	


}
