package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.sql.Connection;
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
			Connection con = dataSource.getConnection();
			ScriptUtils.executeSqlScript(con, fileSystemResource);
			con.close();
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
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "requests", "classes", "users");
	}

	//to delete
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
		System.out.println("hi");
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
	public void updateFirstName() {
		System.out.println("Update first name");
		String email = "jisoo@usc.edu";
		String password = "password";
		String firstName = "Ji Soo";
		String lastName = "Kim";
		String phoneNumber = "3101001000";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		User userUpdate = new User(userID, firstName, lastName, email, phoneNumber, password, accountType, 0);
		String newFirstName = "Kimmy";
		userUpdate.setFirstName(newFirstName);
		dbConnect.updateUserInformation(userUpdate);

		User user = dbConnect.getUserInformation(userID);
		assertNotEquals(user.getFirstName(), firstName);
		assertEquals(user.getFirstName(), newFirstName);
		System.out.println(firstName+" " +lastName+" is now "+newFirstName+" "+lastName);
	}
	
	@Test
	public void updateLastName() {
		System.out.println("Update last name");
		String email = "jisoo@usc.edu";
		String password = "password";
		String firstName = "Ji Soo";
		String lastName = "Kim";
		String phoneNumber = "3101001000";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		User userUpdate = new User(userID, firstName, lastName, email, phoneNumber, password, accountType, 0);
		String newLastName = "Park";
		userUpdate.setLastName(newLastName);
		dbConnect.updateUserInformation(userUpdate);
    
		User user = dbConnect.getUserInformation(userID);
		assertNotEquals(user.getLastName(), lastName);
		assertEquals(user.getLastName(), newLastName);
		System.out.println(firstName+" " +lastName+" is now "+firstName+" "+newLastName);
	}
  
	
	
	@Test
	public void updateEmail() {
		System.out.println("Update first name");
		String email = "jisoo@usc.edu";
		String password = "password";
		String firstName = "Ji Soo";
		String lastName = "Kim";
		String phoneNumber = "3101001000";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		User userUpdate = new User(userID, firstName, lastName, email, phoneNumber, password, accountType, 0);
		String newPassword = "iamveryscary";
		userUpdate.setPasswordHash(newPassword);
		dbConnect.updateUserInformation(userUpdate);

		User user = dbConnect.getUserInformation(userID);
		assertNotEquals(user.getPasswordHash(), password);
		assertEquals(user.getPasswordHash(), newPassword);
		System.out.println(password+" is now "+newPassword);
	}
	
	@Test
	public void updatePhoneNumber() {
		System.out.println("Update first name");
		String email = "jisoo@usc.edu";
		String password = "password";
		String firstName = "Ji Soo";
		String lastName = "Kim";
		String phoneNumber = "3101001000";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		User userUpdate = new User(userID, firstName, lastName, email, phoneNumber, password, accountType, 0);
		String newPhoneNumber = "6262002000";
		userUpdate.setPhoneNumber(newPhoneNumber);
		dbConnect.updateUserInformation(userUpdate);

		User user = dbConnect.getUserInformation(userID);
		assertNotEquals(user.getPhoneNumber(), phoneNumber);
		assertEquals(user.getPhoneNumber(), newPhoneNumber);
		System.out.println(phoneNumber+" is now "+newPhoneNumber);
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
		assertEquals(tutor.getUserId(), userID);
		assertEquals(tutor.getFirstName(), "tutorfirst");
		assertEquals(tutor.getLastName(), "tutorlast");
		assertEquals(tutor.getPhoneNumber(), "1231231234");
		assertEquals(tutor.getAccountType(), true);
		assertEquals(tutor.getMatchingAvailabilities().get(0), 0);
		assertEquals(tutor.getMatchingAvailabilities().get(1), 1);
		assertEquals(tutor.getMatchingAvailabilities().get(2), 2);
	}
	
	/*
	 * tests multiple results and the order based on matching availability
	 */
	@Test
	public void searchTutorsMultipleTutorsTest() {
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
		availability2.add(0);
		availability2.add(1);
		dbConnect.updateTutorAvailability(userID2, availability2);
		dbConnect.addTutorToClass(userID2, classes);
		
		int userID3 = dbConnect.addUser("tutor2@usc.edu", "password", "tutor2first", "tutor2last", "1231231234", true);
		availability2.add(3);
		availability2.add(4);
		availability2.add(5);
		dbConnect.updateTutorAvailability(userID3, availability2);
		classes.add("CSCI 310");
		dbConnect.addTutorToClass(userID3, classes);
		
		int userID4 = dbConnect.addUser("tutor3@usc.edU", "password", "tutor3first", "tutor3last", "1231231234", true);
		ArrayList<Integer> availability3 = new ArrayList<>();
		availability3.add(25);
		ArrayList<String> classes2 = new ArrayList<>();
		classes2.add("CSCI 103");
		dbConnect.addTutorToClass(userID4, classes2);
		
		int tuteeID = dbConnect.addUser("tutee@usc.edu", "password", "tuteefirst", "tuteelast", "1231231234", false);
		
		ArrayList<Integer> times = new ArrayList<>();
		times.add(0);
		times.add(1);
		times.add(2);
		times.add(3);
		times.add(4);
		times.add(5);
		List<Tutor> tutors = dbConnect.searchTutors(tuteeID, times, "CSCI 103");
		
		assertEquals(tutors.size(), 3);
		
		Tutor tutor = tutors.get(0);
		assertNotEquals(null, tutor);
		assertEquals(tutor.getUserId(), userID3);
		assertEquals(tutor.getFirstName(), "tutor2first");
		assertEquals(tutor.getLastName(), "tutor2last");
		assertEquals(tutor.getPhoneNumber(), "1231231234");
		assertEquals(tutor.getAccountType(), true);
		assertEquals(tutor.getMatchingAvailabilities().size(), 5);
		assertEquals(tutor.getMatchingAvailabilities().get(0), 0);
		assertEquals(tutor.getMatchingAvailabilities().get(1), 1);
		assertEquals(tutor.getMatchingAvailabilities().get(2), 3);
		assertEquals(tutor.getMatchingAvailabilities().get(3), 4);
		assertEquals(tutor.getMatchingAvailabilities().get(4), 5);

	
		 
		tutor = tutors.get(1);
		assertNotEquals(null, tutor);
		assertEquals(tutor.getUserId(), userID);
		assertEquals(tutor.getFirstName(), "tutorfirst");
		assertEquals(tutor.getLastName(), "tutorlast");
		assertEquals(tutor.getPhoneNumber(), "1231231234");
		assertEquals(tutor.getAccountType(), true);
		assertEquals(tutor.getMatchingAvailabilities().size(), 3);
		assertEquals(tutor.getMatchingAvailabilities().get(0), 0);
		assertEquals(tutor.getMatchingAvailabilities().get(1), 1);
		assertEquals(tutor.getMatchingAvailabilities().get(2), 2);
		
		tutor = tutors.get(2);
		assertNotEquals(null, tutor);
		assertEquals(tutor.getUserId(), userID2);
		assertEquals(tutor.getFirstName(), "tutor1first");
		assertEquals(tutor.getLastName(), "tutor1last");
		assertEquals(tutor.getPhoneNumber(), "1231231234");
		assertEquals(tutor.getAccountType(), true);
		assertEquals(tutor.getMatchingAvailabilities().size(), 2);
		assertEquals(tutor.getMatchingAvailabilities().get(0), 0);
		assertEquals(tutor.getMatchingAvailabilities().get(1), 1);
	}
	
	@Test
	void searchTutorsNoResultsTests() {
		int userID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		ArrayList<Integer> times = new ArrayList<>();
		times.add(0);
		times.add(5);
		String className = "CSCI 103";
		List<Tutor> tutors = dbConnect.searchTutors(userID, times, className);
		assertEquals(true, tutors.isEmpty());
	}
	
	/*
	 * adds multiple requests for one user 
	 */
	@Test
	void addRequestBasicTest() {
		int userID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		int requestID = dbConnect.addRequest(userID, tutorID, "CSCI 103", "0", 0);
		assertNotEquals(-1, requestID);
		
		int requestID2 = dbConnect.addRequest(userID, tutorID, "CSCI 104", "1", 0);
		assertNotEquals(-1, requestID2);
		
		List<TutorRequest> requests = dbConnect.getRequestsTutorUnapproved(tutorID);
		assertEquals(false, requests.isEmpty());
		assertEquals(2, requests.size());
		
		TutorRequest tutorRequest = requests.get(0);
		assertEquals(requestID, tutorRequest.getRequestID());
		assertEquals("CSCI 103", tutorRequest.getClassName());
		assertEquals("0", tutorRequest.getTime());
		assertEquals(userID, tutorRequest.getTuteeID());
		assertEquals(tutorID, tutorRequest.getTutorID());
		
		tutorRequest = requests.get(1);
		assertEquals(requestID2, tutorRequest.getRequestID());
		assertEquals("CSCI 104", tutorRequest.getClassName());
		assertEquals("1", tutorRequest.getTime());
		assertEquals(userID, tutorRequest.getTuteeID());
		assertEquals(tutorID, tutorRequest.getTutorID());
	}
	
	/*
	 * makes a request for the same timeslot or class as an already matched request
	 */
	@Test
	void addRequestRejectedTest() {
		//same timeslot
		int userID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		int requestID = dbConnect.addRequest(userID, tutorID, "CSCI 103", "0", 1);
		assertNotEquals(-1, requestID);
		
		int tutorID2 = dbConnect.addUser("tutor2@usc.edu", "password", "tutor2", "tutor2", "1231231234", true);
		int requestID2 = dbConnect.addRequest(userID, tutorID2, "CSCI 104", "0", 0);
		assertEquals(-1, requestID2);
		
		//same classname
		int requestID3 = dbConnect.addRequest(userID, tutorID2, "CSCI 103", "4", 0);
		assertEquals(-1, requestID3);
		
		//different tutee should work
		int tuteeID2 = dbConnect.addUser("tutee@usc.edu", "password", "tutee", "tutee", "1231231234", false);
		int requestID4 = dbConnect.addRequest(tuteeID2, tutorID, "CSCI 103", "1", 0);
		assertNotEquals(-1, requestID4);
	}
	
	@Test
	void updateRequestStatusBasicAcceptedTest() {
		int userID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		ArrayList<Integer> availability = new ArrayList<>();
		availability.add(0);
		availability.add(1);
		dbConnect.updateTutorAvailability(tutorID, availability);
		int requestID = dbConnect.addRequest(userID, tutorID, "CSCI 103", "0", 0);
		
		dbConnect.updateRequestStatus(requestID, 1);
		List<TutorRequest> tuteeRequests = dbConnect.getRequestsTuteeApproved(userID);
		assertEquals(1, tuteeRequests.size());
		
		TutorRequest tr = tuteeRequests.get(0);
		assertEquals(userID, tr.getTuteeID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(1, tr.getStatus());
		
		//now, check that tutor's availability was properly updated
		ArrayList<Integer> updatedAvailability = dbConnect.getTutorAvailability(tutorID);
		assertEquals(1, updatedAvailability.size());
		assertEquals(1, updatedAvailability.get(0));
	}
	
	@Test
	void updateRequestStatusBasicRejectedTest() {
		int userID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		ArrayList<Integer> availability = new ArrayList<>();
		availability.add(0);
		availability.add(1);
		dbConnect.updateTutorAvailability(tutorID, availability);
		int requestID = dbConnect.addRequest(userID, tutorID, "CSCI 103", "0", 0);
		
		dbConnect.updateRequestStatus(requestID, 2);
		List<TutorRequest> tuteeRequests = dbConnect.getRequestsTuteeApproved(userID);
		assertEquals(0, tuteeRequests.size());
		
		tuteeRequests = dbConnect.getRequestsTuteeRejected(userID);
		assertEquals(1, tuteeRequests.size());
		
		TutorRequest tr = tuteeRequests.get(0);
		assertEquals(userID, tr.getTuteeID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(2, tr.getStatus());
		
		//now, check that tutor's availability was not updated
		ArrayList<Integer> updatedAvailability = dbConnect.getTutorAvailability(tutorID);
		assertEquals(2, updatedAvailability.size());
		assertEquals(0, updatedAvailability.get(0));
		assertEquals(1, updatedAvailability.get(1));
	}
	
	/*
	 * this tests where when you approve one request all requests from same tutee
	 * with overlapping time will be rejected
	 */
	@Test
	void updateRequestStatusTimeOverlapTest() {
		int userID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		ArrayList<Integer> availability = new ArrayList<>();
		availability.add(0);
		availability.add(1);
		dbConnect.updateTutorAvailability(tutorID, availability);
		int requestID = dbConnect.addRequest(userID, tutorID, "CSCI 103", "0", 0);
		int requestID2 = dbConnect.addRequest(userID, tutorID, "CSCI 104", "0", 0);
		int requestID3 = dbConnect.addRequest(userID, tutorID, "CSCI 109", "0", 0);
		int requestID4 = dbConnect.addRequest(userID, tutorID, "CSCI 310", "1", 0);
		dbConnect.updateRequestStatus(requestID, 1);
		
		List<TutorRequest> requests = dbConnect.getRequestsTuteeApproved(userID);
		assertEquals(1, requests.size());
		TutorRequest tr = requests.get(0);
		assertEquals(requestID, tr.getRequestID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals(userID, tr.getTuteeID());
		
		requests = dbConnect.getRequestsTuteeRejected(userID);
		assertEquals(2, requests.size());
		tr = requests.get(0);
		assertEquals(requestID2, tr.getRequestID());
		assertEquals("CSCI 104", tr.getClassName());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals(userID, tr.getTuteeID());
		tr = requests.get(1);
		assertEquals(requestID3, tr.getRequestID());
		assertEquals("CSCI 109", tr.getClassName());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals(userID, tr.getTuteeID());
		
		ArrayList<Integer> updatedAvailability = dbConnect.getTutorAvailability(tutorID);
		assertEquals(1, updatedAvailability.size());
		assertEquals(1, updatedAvailability.get(0));
		
	}
	
	@Test
	void updateRequestStatusClassOverlapTest() {
		int userID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		int tutorID2 = dbConnect.addUser("tutor1@usc.edu", "password", "tutorone", "tutorone", "1231231234", true);
		ArrayList<Integer> availability = new ArrayList<>();
		availability.add(0);
		availability.add(1);
		dbConnect.updateTutorAvailability(tutorID, availability);
		int requestID = dbConnect.addRequest(userID, tutorID, "CSCI 103", "0", 0);
		int requestID2 = dbConnect.addRequest(userID, tutorID2, "CSCI 103", "0", 0);
		int requestID3 = dbConnect.addRequest(userID, tutorID, "CSCI 109", "3", 0);
		int requestID4 = dbConnect.addRequest(userID, tutorID, "CSCI 310", "1", 0);
		dbConnect.updateRequestStatus(requestID, 1);
		
		List<TutorRequest> requests = dbConnect.getRequestsTuteeApproved(userID);
		assertEquals(1, requests.size());
		TutorRequest tr = requests.get(0);
		assertEquals(requestID, tr.getRequestID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals(userID, tr.getTuteeID());
		
		requests = dbConnect.getRequestsTuteeRejected(userID);
		assertEquals(1, requests.size());
		tr = requests.get(0);
		assertEquals(requestID2, tr.getRequestID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(tutorID2, tr.getTutorID());
		assertEquals(userID, tr.getTuteeID());
		
		ArrayList<Integer> updatedAvailability = dbConnect.getTutorAvailability(tutorID);
		assertEquals(1, updatedAvailability.size());
		assertEquals(1, updatedAvailability.get(0));
		
	}
	
	@Test
	void getRequestsTuteeApprovedTest() {
		int tuteeID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		
		int requestID1 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 103", "0", 1);
		int requestID2 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 104", "1", 1);
		int requestID3 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 109", "2", 1);
		int requestID4 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 310", "3", 1);
		int requestID5 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 201", "5", 0);
		int requestID6 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 270", "6", 2);
		
		List<TutorRequest> requests = dbConnect.getRequestsTuteeApproved(tuteeID);
		assertEquals(4, requests.size());
		
		TutorRequest tr = requests.get(0);
		assertEquals(requestID1, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("0", tr.getTime());

		tr = requests.get(1);
		assertEquals(requestID2, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 104", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("1", tr.getTime());
		
		tr = requests.get(2);
		assertEquals(requestID3, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 109", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("2", tr.getTime());
		
		tr = requests.get(3);
		assertEquals(requestID4, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 310", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("3", tr.getTime());

	}
	
	@Test
	void getRequestsTuteeRejectedTest() { 
		int tuteeID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		
		int requestID1 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 103", "0", 2);
		int requestID2 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 104", "1", 2);
		int requestID3 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 109", "2", 2);
		int requestID4 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 310", "3", 2);
		int requestID5 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 201", "5", 0);
		int requestID6 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 270", "6", 1);
		
		List<TutorRequest> requests = dbConnect.getRequestsTuteeRejected(tuteeID);
		assertEquals(4, requests.size());
		
		TutorRequest tr = requests.get(0);
		assertEquals(requestID1, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(2, tr.getStatus());
		assertEquals("0", tr.getTime());

		tr = requests.get(1);
		assertEquals(requestID2, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 104", tr.getClassName());
		assertEquals(2, tr.getStatus());
		assertEquals("1", tr.getTime());
		
		tr = requests.get(2);
		assertEquals(requestID3, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 109", tr.getClassName());
		assertEquals(2, tr.getStatus());
		assertEquals("2", tr.getTime());
		
		tr = requests.get(3);
		assertEquals(requestID4, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 310", tr.getClassName());
		assertEquals(2, tr.getStatus());
		assertEquals("3", tr.getTime());
	}
	
	@Test
	void getRequestsTutorApprovedTest() {
		int tuteeID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		
		int requestID1 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 103", "0", 1);
		int requestID2 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 104", "1", 1);
		int requestID3 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 109", "2", 1);
		int requestID4 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 310", "3", 1);
		int requestID5 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 201", "5", 0);
		int requestID6 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 270", "6", 2);
		
		List<TutorRequest> requests = dbConnect.getRequestsTutorApproved(tutorID);
		assertEquals(4, requests.size());
		
		TutorRequest tr = requests.get(0);
		assertEquals(requestID1, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("0", tr.getTime());

		tr = requests.get(1);
		assertEquals(requestID2, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 104", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("1", tr.getTime());
		
		tr = requests.get(2);
		assertEquals(requestID3, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 109", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("2", tr.getTime());
		
		tr = requests.get(3);
		assertEquals(requestID4, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 310", tr.getClassName());
		assertEquals(1, tr.getStatus());
		assertEquals("3", tr.getTime());
	}
	
	@Test
	void getRequestsTutorUnapprovedTest() {
		int tuteeID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		
		int requestID1 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 103", "0", 0);
		int requestID2 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 104", "1", 0);
		int requestID3 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 109", "2", 0);
		int requestID4 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 310", "3", 0);
		int requestID5 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 201", "5", 1);
		int requestID6 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 270", "6", 2);
		
		List<TutorRequest> requests = dbConnect.getRequestsTutorUnapproved(tutorID);
		assertEquals(4, requests.size());
		
		TutorRequest tr = requests.get(0);
		assertEquals(requestID1, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 103", tr.getClassName());
		assertEquals(0, tr.getStatus());
		assertEquals("0", tr.getTime());

		tr = requests.get(1);
		assertEquals(requestID2, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 104", tr.getClassName());
		assertEquals(0, tr.getStatus());
		assertEquals("1", tr.getTime());
		
		tr = requests.get(2);
		assertEquals(requestID3, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 109", tr.getClassName());
		assertEquals(0, tr.getStatus());
		assertEquals("2", tr.getTime());
		
		tr = requests.get(3);
		assertEquals(requestID4, tr.getRequestID());
		assertEquals(tuteeID, tr.getTuteeID());
		assertEquals(tutorID, tr.getTutorID());
		assertEquals("CSCI 310", tr.getClassName());
		assertEquals(0, tr.getStatus());
		assertEquals("3", tr.getTime());
	}
		
	@Test
	void getRequestEmptyTest() {
		int tuteeID = dbConnect.addUser("test@usc.edu", "password", "testfirst", "testlast", "1231231234", false);
		int tutorID = dbConnect.addUser("tutor@usc.edu", "password", "tutor", "tutor", "1231231234", true);
		int tuteeID2 = dbConnect.addUser("tutee@usc.edu", "password", "tutee", "tutee", "1231231234", false);
		
		int requestID1 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 103", "0", 1);
		int requestID2 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 104", "1", 1);
		int requestID3 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 109", "2", 1);
		int requestID4 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 310", "3", 1);
		int requestID5 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 201", "5", 0);
		int requestID6 = dbConnect.addRequest(tuteeID, tutorID, "CSCI 270", "6", 2);
		
		List<TutorRequest> requests = dbConnect.getRequestsTuteeApproved(tuteeID2);
		assertEquals(0, requests.size());
		
		requests = dbConnect.getRequestsTuteeRejected(tuteeID2);
		assertEquals(0, requests.size());
		
		requests = dbConnect.getRequestsTutorApproved(tuteeID2);
		assertEquals(0, requests.size());
		
		requests = dbConnect.getRequestsTutorUnapproved(tuteeID2);
		assertEquals(0, requests.size());
	}
	
	@Test
	void authenticateBasicTest() {
		int userID = dbConnect.addUser("person@usc.edu", "password", "personfirst", "personlast", "1231231234", true);
		User user = dbConnect.authenticate("person@usc.edu", "password");
		assertNotEquals(null, user);
		assertEquals("person@usc.edu", user.getEmail());
		assertEquals("personfirst", user.getFirstName());
		assertEquals("personlast", user.getLastName());
		assertEquals("1231231234", user.getPhoneNumber());
		assertEquals(true, user.getAccountType());
		
	}
	
	@Test
	void authenticateNonexistentUserTest() {
		int userID = dbConnect.addUser("person1@usc.edu", "password", "personfirst", "personlast", "1231231234", true);
		User user = dbConnect.authenticate("person@usc.edu", "password");
		assertEquals(null, user);
	}
	
	@Test
	void authenticateWrongPasswordTest() {
		int userID = dbConnect.addUser("person@usc.edu", "password", "personfirst", "personlast", "1231231234", true);
		User user = dbConnect.authenticate("person@usc.edu", "password1");
		assertEquals(null, user);
		
		user = dbConnect.authenticate("person@usc.edu", "PASSWORD");
		assertEquals(null, user);
	}
	
	@Test
	void removeTutorFromClassBasicTest() {
		
	}
	
	@Test
	void removeTutorFromClassDeleteRequestsTest() { 
		
	}
	
	@Test
	public void updateNoClassAvailability() {
		System.out.println("Update tutor's classes with no classes");
		String email = "tutor@usc.edu";
		String password = "password";
		String firstName = "Chao";
		String lastName = "Wang";
		String phoneNumber = "1231231234";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		ArrayList<String> classes = new ArrayList<String>();
		dbConnect.addTutorToClass(userID, classes);
		
		ArrayList<String> dbClasses = dbConnect.getTutorClasses(userID);
		assertEquals(dbClasses, classes);
	}
	
	@Test
	public void updateOneClassAvailability() {
		System.out.println("Update tutor's classes with one class");
		String email = "tutor@usc.edu";
		String password = "password";
		String firstName = "Chao";
		String lastName = "Wang";
		String phoneNumber = "1231231234";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		ArrayList<String> classes = new ArrayList<String>();
		classes.add("CSCI 201");
		dbConnect.addTutorToClass(userID, classes);
		
		ArrayList<String> dbClasses = dbConnect.getTutorClasses(userID);
		assertEquals(dbClasses, classes);
	}
	
	@Test
	public void updateMultipleClassAvailability() {
		System.out.println("Update tutor's classes with multiple class");
		String email = "tutor@usc.edu";
		String password = "password";
		String firstName = "Chao";
		String lastName = "Wang";
		String phoneNumber = "1231231234";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		ArrayList<String> classes = new ArrayList<String>();
		classes.add("CSCI 201");
		classes.add("CSCI 103");
		classes.add("CSCI 104");
		classes.add("CSCI 109");
		classes.add("EE 109");
		dbConnect.addTutorToClass(userID, classes);
		
		ArrayList<String> dbClasses = dbConnect.getTutorClasses(userID);
		assertEquals(dbClasses, classes);
	}
	
	@Test
	public void updateClassAvailabilityMultipleTimes() {
		System.out.println("Update tutor's classes with multiple class");
		String email = "tutor@usc.edu";
		String password = "password";
		String firstName = "Chao";
		String lastName = "Wang";
		String phoneNumber = "1231231234";
		Boolean accountType = true;
		int userID = dbConnect.addUser(email, password, firstName, lastName, phoneNumber, accountType);
		
		ArrayList<String> classes1 = new ArrayList<String>();
		classes1.add("CSCI 360");
		ArrayList<String> classes2 = new ArrayList<String>();
		ArrayList<String> classes3 = new ArrayList<String>();
		classes3.add("CSCI 102");
		classes3.add("EE 109");
		classes3.add("MATH 226");
		classes3.add("CSCI 170");
		classes3.add("CSCI 201");
		classes3.add("CSCI 270");
		
		// iteration 1
		dbConnect.addTutorToClass(userID, classes1);
		
		ArrayList<String> dbClasses1 = dbConnect.getTutorClasses(userID);
		assertEquals(dbClasses1, classes1);
		assertNotEquals(dbClasses1, classes2);
		assertNotEquals(dbClasses1, classes3);
		System.out.println(dbClasses1);
		
		// iteration 2
		dbConnect.addTutorToClass(userID, classes2);
		ArrayList<String> dbClasses2 = dbConnect.getTutorClasses(userID);
		assertEquals(dbClasses2, classes2);
		assertNotEquals(dbClasses2, classes1);
		assertNotEquals(dbClasses2, classes3);
		System.out.println(dbClasses2);
		
		// iteration 3
		dbConnect.addTutorToClass(userID, classes3);
		
		ArrayList<String> dbClasses3 = dbConnect.getTutorClasses(userID);
		assertEquals(dbClasses3, classes3);
		assertNotEquals(dbClasses3, classes1);
		assertNotEquals(dbClasses3, classes2);
		System.out.println(dbClasses3);
		
	}
	
	@Test
	public void updateAvailability() {
		
	}
}
