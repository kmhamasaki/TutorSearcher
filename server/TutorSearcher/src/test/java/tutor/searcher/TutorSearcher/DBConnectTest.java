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
//		
//		PreparedStatement ps = null;
//		try {
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/TutorSearcherTest?user="
//					+ "root&password=password&useSSL=false&serverTimezone=UTC");
//			ps = conn.prepareStatement("DELETE from users");
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}
	@Test
	void addTutorToClassTest() {
		int tutorID = 15;
//		ArrayList<String> classes = new ArrayList<>();
//		classes.add("CSCI 201");
//		classes.add("CSCI 270");
//		classes.add("CSCI 356");
//		dbConnect.addTutorToClass(tutorID, classes);
		
	}
	@Test
	void addRequest() {
		int tuteeID = 4;
		int tutorID = 15;
		String className = "CSCI 401";
		String time = "15";
		int status = 0;
		//dbConnect.addRequest(tuteeID, tutorID, className, time, status);
	}
	
	@Test
	void getRequestsTuteeApprovedTest() {
		System.out.println("tutee approved");
		int tuteeID = 4;
		List<TutorRequest> requests = dbConnect.getRequestsTuteeApproved(tuteeID);
		for (int i = 0; i < requests.size(); i++) {
			System.out.println("tutor: " + requests.get(i).getTutorName());
			System.out.println("class: " + requests.get(i).getClassName());
		}
	}
	
	@Test
	void getRequestsTutorApprovedTest() {
		System.out.println("tutorapproved");
		int tutorID = 2;
		List<TutorRequest> requests = dbConnect.getRequestsTutorApproved(tutorID);
		for (int i = 0; i < requests.size(); i++) {
			System.out.println("tutor: " + requests.get(i).getTuteeName());
			System.out.println("class: " + requests.get(i).getClassName());
		}
	}
	
	@Test
	void getRequestsTutorUnapprovedTest() {
		System.out.println("tutor pending");
		int tutorID = 1;
		List<TutorRequest> requests = dbConnect.getRequestsTutorUnapproved(tutorID);
		for (int i = 0; i < requests.size(); i++) {
			System.out.println("tutor: " + requests.get(i).getTuteeName());
			System.out.println("class: " + requests.get(i).getClassName());
		}
	}
	
	@Test
	void searchTutorTest() {
		ArrayList<Integer> times = new ArrayList<>();
		times.add(1);
		times.add(2);
		times.add(3);
		times.add(4);
		String className = "CSCI 103";
		List<Tutor> results = dbConnect.searchTutors(times, className);
		for (int i = 0; i < results.size(); i++) {
			System.out.println(results.get(i).getEmail());
		}
		
	}
	@Test
	void updateTutorAvailability() {
		int userID = 6;
		List<Integer> availability = Arrays.asList(5, 9, 10);
		dbConnect.updateTutorAvailability(userID, availability);
	}
//	@Test
//	void addUser() {
//		List<Integer> avail = Arrays.asList(1, 2, 3);
//		// Normal Insertion
//		int UserID = dbConnect.addUser("tommy@usc.edu", "skjfakksjdg", "Tommy", "Trojan", 
//				"8081234567", true);
//		assertEquals(UserID, 1);
//		
//		// Repeat email
//		UserID = dbConnect.addUser("tommy@usc.edu", "skjfakksjdg", "Tommy", "Trojan", 
//				"8081234567", true);
//		assertEquals(UserID, -1);
//	}
//	
//	@Test
//	void authenticate() {
//		List<Integer> avail = Arrays.asList(1, 2, 3);
//		int UserID = dbConnect.addUser("tommy@usc.edu", "skjfakksjdg", "Tommy", "Trojan", 
//				"8081234567", true);
//		
//		// Correct Login
//		User user = dbConnect.authenticate("tommy@usc.edu", "skjfakksjdg");
//		assertTrue(user != null);
//		
//		// Wrong password
//		user = dbConnect.authenticate("tommy@usc.edu", "ksjdfsa");
//		assertTrue(user == null);
//		
//		// Email doesn't exist
//		user = dbConnect.authenticate("tommy2@usc.edu", "ksjdfsa");
//		assertTrue(user == null);
//	}

}
