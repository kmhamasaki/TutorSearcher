package tutor.searcher.TutorSearcher;

import java.sql.Connection;
import java.sql.DriverManager;
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
	private Connection conn;
	private int numUsers = 0;
	
	public DBConnect(String SQLName, String username, String password) {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + SQLName + "?user="
					+ username + "&password=" + password + "&useSSL=false&serverTimezone=UTC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	
	int addUser(String email, String passwordHash, String firstName, String lastName, String phoneNumber,
			Boolean accountType, List<Integer> availability) {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			// Check if email already exists
			ps = conn.prepareStatement("SELECT * FROM users where email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				return -1;
			}
			
			String availString = (accountType ? availability.toString() : "");
			
			ps = conn.prepareStatement("INSERT INTO users (user_id, email, password_hash, tutor, phone_num,"
					+ "first_name, last_name, availability) VALUES (?,?,?,?,?,?,?,?)");
			ps.setInt(1, ++numUsers);
			ps.setString(2, email);
			ps.setString(3, passwordHash);
			ps.setBoolean(4, accountType);
			ps.setString(5, phoneNumber);
			ps.setString(6, firstName);
			ps.setString(7, lastName);
			ps.setString(8, availString);
			

			ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}	
		return numUsers;
				
	}
	Boolean updateRequestStatus(int requestID, int newStatus) {
		return false;
	}
	List<Tutor> searchTutors(List<Integer> times, String className) {
		return null;
	}
	
	User authenticate(String email, String passwordHash) {
		// check Database to see email and password Hash
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * FROM users where email=?");
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getString("password_hash").equals(passwordHash)) {
					//int userID, String firstName, String lastName, String email, String phoneNumber, Boolean accountType
					return new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"),
							rs.getString("email"), rs.getString("phone_num"), rs.getBoolean("tutor"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// return null if wrong login info
		return null;
	}
	Boolean addTutorToClass(int tutorID, String className) {
		return false;
	}
	Boolean removeTutorFromClass(int tutorID, String className) {
		return false;
	}
}
