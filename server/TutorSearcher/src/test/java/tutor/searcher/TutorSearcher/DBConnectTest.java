package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
}
