package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DBConnectTest {

	private DBConnect dbConnect;
	private Connection conn;
	
	public DBConnectTest() {
		dbConnect = new DBConnect("TutorSearcherTest", "root", "password");
		
		PreparedStatement ps = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/TutorSearcherTest?user="
					+ "root&password=password&useSSL=false&serverTimezone=UTC");
			ps = conn.prepareStatement("DELETE from users");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void addUser() {
		List<Integer> avail = Arrays.asList(1, 2, 3);
		// Normal Insertion
		int UserID = dbConnect.addUser("tommy@usc.edu", "skjfakksjdg", "Tommy", "Trojan", 
				"8081234567", true, avail);
		assertEquals(UserID, 1);
		
		// Repeat email
		UserID = dbConnect.addUser("tommy@usc.edu", "skjfakksjdg", "Tommy", "Trojan", 
				"8081234567", true, avail);
		assertEquals(UserID, -1);
	}
	
	@Test
	void authenticate() {
		List<Integer> avail = Arrays.asList(1, 2, 3);
		int UserID = dbConnect.addUser("tommy@usc.edu", "skjfakksjdg", "Tommy", "Trojan", 
				"8081234567", true, avail);
		
		// Correct Login
		User user = dbConnect.authenticate("tommy@usc.edu", "skjfakksjdg");
		assertTrue(user != null);
		
		// Wrong password
		user = dbConnect.authenticate("tommy@usc.edu", "ksjdfsa");
		assertTrue(user == null);
		
		// Email doesn't exist
		user = dbConnect.authenticate("tommy2@usc.edu", "ksjdfsa");
		assertTrue(user == null);
	}

}
