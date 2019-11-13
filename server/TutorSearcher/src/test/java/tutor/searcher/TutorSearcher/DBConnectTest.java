package tutor.searcher.TutorSearcher;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
