package tutor.searcher.TutorSearcher;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class DBConnect {
	
	@Autowired
	private JdbcTemplate jdbc;
	private int numUsers = 0;
	
	int getUserID(String email) {
		return 0;
	}
	String getPassword(String email) {
		String query = "SELECT password_hash FROM users WHERE users.email = '" + email + "'";
		String result = jdbc.queryForObject(query, String.class);
		//String resultString jdbc.query
//		jdbc.query(query, 
//				new PreparedStatementSetter() {
//					public void setValues(PreparedStatement preparedStatement) throws SQLException {
//						preparedStatement.setString(1,  email);
//					}
//				}, 
//				 new ResultSetExtractor<String>() {
//		            public String extractData(ResultSet resultSet) throws SQLException,
//		              DataAccessException {
//		                if (resultSet.next()) {
//		                	System.out.println("result");
//		                	System.out.println(resultSet.toString());
//		                    return resultSet.getObject(1).toString();
//		                }
//		                return null;
//		            }
//          });
		return result;
	}
	boolean getAccountType(String email) {
		String query = "SELECT tutor FROM users WHERE users.email = '" + email + "'";
		Boolean result = jdbc.queryForObject(query, Boolean.class);
		return result;
	}
	List<TutorRequest> getRequests(int userID) {
		return null; 
	}
	
	int addUser(String email, String passwordHash, String name, Boolean accountType) {
		
		// add stuff to db, make sure to increment numUsers, which is the UserID
		
		//if user email, already exists,
		// return -1;
		return numUsers;
				
	}
	Boolean updateRequestStatus(int requestID, int newStatus) {
		return false;
	}
	List<Tutor> searchTutors(String times, String className) {
		return null;
	}
	User authenticate(String email, String passwordHash) {
		// check db to see email and password Hash
		
		//return null if wrong login info
		return null;
	}
	Boolean addTutorToClass(int tutorID, String className) {
		return false;
	}
	Boolean removeTutorFromClass(int tutorID, String className) {
		return false;
	}
}
