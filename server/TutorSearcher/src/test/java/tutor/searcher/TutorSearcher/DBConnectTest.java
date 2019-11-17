package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.jdbc.JdbcTestUtils;


@SpringBootTest
class DBConnectTest {

	@Autowired
	private DBConnect dbConnect;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	public void DBConnectTestSetUp() {
		//call SQL script to set up tables
		InputStreamResource resource;
		
		File file = new File("src/main/resources/tutorsearchertest.sql");
		FileSystemResource fileSystemResource = new FileSystemResource(file);
		try {
			ScriptUtils.executeSqlScript(dataSource.getConnection(), fileSystemResource);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	@BeforeAll
//	private static void setUp() {
//		
//	}
	
	@AfterEach
	private void cleanUp() {
		//delete all data from tables 
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users", "classes", "requests");
	}

	
	@Test
	public void simpleTest() {
		System.out.println("SIMPLE TEST");
		int userID = dbConnect.addUser("alicesle@usc.edu", "password", "Alice", "Lee", "1231231234", true);
		assertEquals(userID, 1);
		System.out.println("userID: " + userID);
	}
	
	@Test
	public void addTutorTest() {
		System.out.println("Add New Tutor");
		String email = "jenniekim@usc.edu";
		String password = "blink";
		String firstName = "Jennie";
		String lastName = "Kim";
		String phoneNumber = "1112223333";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		User user = dbConnect.getUserInformation(userID);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPasswordHash(), password);
		assertEquals(user.getFirstName(), firstName);
		assertEquals(user.getLastName(), lastName);
		assertEquals(user.getAccountType(), accountType);
		
		Boolean DBaccountType = dbConnect.getAccountType(email);
		assertEquals(DBaccountType, accountType);
		System.out.println("userID: " + userID);
		System.out.println(firstName+" " +lastName+" in your area");
	}
	
	@Test
	public void addTuteeTest() {
		System.out.println("Add New Tutee");
		String email = "tutee@usc.edu";
		String password = "tuteepass";
		String firstName = "Tutee";
		String lastName = "The";
		String phoneNumber = "2221113333";
		Boolean accountType = false;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		User user = dbConnect.getUserInformation(userID);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getPasswordHash(), password);
		assertEquals(user.getFirstName(), firstName);
		assertEquals(user.getLastName(), lastName);
		assertEquals(user.getAccountType(), accountType);
		
		Boolean DBaccountType = dbConnect.getAccountType(email);
		assertEquals(DBaccountType, accountType);
		System.out.println("userID: " + userID);
		System.out.println(firstName+" " +lastName+" in your area");
	}
	
	@Test
	public void addUserAlreadyExist() {
		System.out.println("Add Tutor that already exists");
		String email = "jessica@usc.edu";
		String password = "illinois";
		String firstName = "Jessica";
		String lastName = "Only Child";
		String phoneNumber = "3332221111";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		String email2 = "jessica@usc.edu";
		String password2 = "p";
		String firstName2 = "a";
		String lastName2 = "b";
		String phoneNumber2 = "911";
		Boolean accountType2 = false;
		int userID2 = dbConnect.addUser(email2, password2, firstName2, lastName2, phoneNumber2, accountType2);
		assertEquals(userID2, -1);
		System.out.println(firstName+" " +lastName+" in your area");
		System.out.println(firstName2+" " +lastName2+" failed to be added");
	}
	
	@Test
	public void searchTutorsSingleTutorTest() {
		int userID = dbConnect.addUser("tutor@usc.edu", "password", "tutorfirst", "tutorlast", "1231231234", true);
		ArrayList<Integer> availability = new ArrayList<>();
		availability.add(0);
		availability.add(1);
		availability.add(2);
		dbConnect.updateTutorAvailability(userID, availability);
		ArrayList<String> classes = new ArrayList<>();
		classes.add("CSCI 103");
		classes.add("CSCI 104");
		dbConnect.addTutorToClass(userID, classes);
		
		int userID2 = dbConnect.addUser("tutor1@usc.edu", "password", "tutor1first", "tutor1last", "1231231234", true);
		ArrayList<Integer> availability2 = new ArrayList<>();
		availability2.add(4);
		availability2.add(5);
		dbConnect.updateTutorAvailability(userID2, availability2);
		dbConnect.addTutorToClass(userID2, classes);
		
		int tuteeID = dbConnect.addUser("tutee@usc.edu", "password", "tuteefirst", "tuteelast", "1231231234", false);
		
		ArrayList<Integer> times = new ArrayList<>();
		times.add(0);
		times.add(1);
		times.add(2);
		List<Tutor> tutors = dbConnect.searchTutors(tuteeID, times, "CSCI 103");
		
		assertEquals(tutors.size(), 1);
		Tutor tutor = tutors.get(0);
		assertEquals(tutor.getClass(), "CSCI 103");
		assertEquals(tutor.getUserId(), userID);
		assertEquals(tutor.getFirstName(), "tutorfirst");
		assertEquals(tutor.getLastName(), "tutorlast");
		assertEquals(tutor.getPhoneNumber(), "1231231234");
		assertEquals(tutor.getAccountType(), true);
		assertEquals(tutor.getMatchingAvailabilities().get(0), 0);
		assertEquals(tutor.getMatchingAvailabilities().get(1), 1);
		assertEquals(tutor.getMatchingAvailabilities().get(2), 2);

	}
	

}
