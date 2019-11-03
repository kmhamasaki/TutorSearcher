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
		ArrayList<String> classes = new ArrayList<>();
		classes.add("CSCI 201");
		classes.add("CSCI 270");
		classes.add("CSCI 356");
		dbConnect.addTutorToClass(tutorID, classes);
		
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
