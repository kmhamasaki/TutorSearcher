package tutor.searcher.TutorSearcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class DBConnect {
	
	@Autowired
	private JdbcTemplate jdbc;
	private Connection conn;
	private int numUsers = 0;
	
	public DBConnect() {
		
	}
	public DBConnect(JdbcTemplate jdbc) {
		
		this.jdbc = jdbc;
	}
//	
//	public DBConnect() {
//		try {
//			conn = DriverManager.getConnection("jdbc:mysql://localhost/" + "TutorSearcher" + "?user="
//					+ "root" + "&password=" + "password" + "&useSSL=false&serverTimezone=UTC");
//			PreparedStatement ps = null;
//
//			ps = conn.prepareStatement("DELETE from users");
//			ps.executeUpdate();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	int getUserID(String email) {
		return 0;
	}
	String getPassword(String email) {
		String query = "SELECT password_hash FROM users WHERE users.email = '" + email + "'";
		String result = jdbc.queryForObject(query, String.class);
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
		String query = "SELECT * FROM requests, users WHERE requests.tutee_id=? AND requests.tutor_id=users.user_id";
		List<TutorRequest> result = jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  userID);
			}
		}, 
		 new ResultSetExtractor<List<TutorRequest>>() {
            public List<TutorRequest> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
            	ArrayList<TutorRequest> result = new ArrayList<>();
                while (resultSet.next()) {
                	System.out.println("result");
//                	(int requestID, int tuteeID, int tutorID, String time, int status, Date timecreated)
                	int requestID = resultSet.getInt("id");
                	int tuteeID = resultSet.getInt("tutee_id");
                	int tutorID = resultSet.getInt("tutor_id");
                	String className = resultSet.getString("class");
                	String time = resultSet.getString("time");
                	int status = resultSet.getInt("status");
                	Date timeCreated = resultSet.getDate("time_created");
                	System.out.print(requestID);
                	System.out.println(className + " " + time);
                	
                    result.add(new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className));
                }
                return result;
            }
		});
		return result; 
	}
	
	int addUser(String email, String passwordHash, String firstName, String lastName, String phoneNumber,
			Boolean accountType) {
		
		String query = "SELECT * FROM users WHERE email=?";
		Boolean exists = jdbc.query(query, 
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setString(1,  email);
					}
				}, 
				 new ResultSetExtractor<Boolean>() {
		            public Boolean extractData(ResultSet resultSet) throws SQLException,
		              DataAccessException {
		            	if (resultSet.next()) {
		            		return true;
		            	}
		            	return false;
		            }
				});
		
		if (exists) {
			return -1;
		}
		
		String insertQuery = "INSERT INTO users (email, password_hash, tutor, phone_number,"
			+ "first_name, last_name) VALUES (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(
		    new PreparedStatementCreator() {
		    	@Override
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(insertQuery, new String[] {"id"});
		            ps.setString(1,email);  
			        ps.setString(2,passwordHash);  
			        ps.setBoolean(3,accountType);  
			        ps.setString(4, phoneNumber);
			        ps.setString(5, firstName);
			        ps.setString(6, lastName);
		            return ps;
		        }
		    },
		    keyHolder);
		System.out.println(keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
		
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		System.out.println(email);
//		System.out.println(passwordHash);
//		System.out.println(firstName);
//		System.out.println(lastName);
//		System.out.println(phoneNumber);
//		System.out.println(accountType);
//		try
//		{
//			// Check if email already exists
//			ps = conn.prepareStatement("SELECT * FROM users where email=?");
//			ps.setString(1, email);
//			rs = ps.executeQuery();
//			if(rs.next()) {
//				return -1;
//			}
//			
//			String availString = (accountType ? availability.toString() : "");
//			
//			ps = conn.prepareStatement("INSERT INTO users (user_id, email, password_hash, tutor, phone_num,"
//					+ "first_name, last_name, availability) VALUES (?,?,?,?,?,?,?,?)");
//			ps.setInt(1, ++numUsers);
//			ps.setString(2, email);
//			ps.setString(3, passwordHash);
//			ps.setBoolean(4, accountType);
//			ps.setString(5, phoneNumber);
//			ps.setString(6, firstName);
//			ps.setString(7, lastName);
//			ps.setString(8, availString);
//			
//
//			ps.executeUpdate();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}	
//		return numUsers;
				
	}
	
	// what kind of error checking do we need for this? do i need to check for some stuff
	// in the backend logic?
	// to keep in mind: when a tutor accepts a tutee's request, that availability msut go off of the tutor's
	// time availability, and simultaneously tutee's requests for same class and time must die 
	int addRequest(int tuteeID, int tutorID, String className, String time, int status) {
		String query = "INSERT INTO requests (tutee_id, tutor_id, class, time, status, time_created) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
//		jdbc.execute(query, new PreparedStatementCallback<Boolean>() {
//			@Override  
//		    public Boolean doInPreparedStatement(PreparedStatement ps)  
//		            throws SQLException, DataAccessException {  
//		              
//		        ps.setInt(1,tuteeID);  
//		        ps.setInt(2,tutorID);  
//		        ps.setString(3,className);  
//		        ps.setString(4,  time);
//		        ps.setInt(5, status);
//		        ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
//		              
//		        return ps.execute();  
//		              
//		    }  
//		});
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(
		    new PreparedStatementCreator() {
		    	@Override
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps =
		                connection.prepareStatement(query, new String[] {"id"});
		            ps.setInt(1,tuteeID);  
			        ps.setInt(2,tutorID);  
			        ps.setString(3,className);  
			        ps.setString(4,  time);
			        ps.setInt(5, status);
			        ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
		            return ps;
		        }
		    },
		    keyHolder);
		System.out.println(keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}
	// status
	// 0 = waiting approval
	// 1 = approved
	// 2 = rejected
	//what is the return boolean for? 
	//untested
	Boolean updateRequestStatus(int requestID, int newStatus) {
		final String query = "UPDATE requests SET requests.status=? WHERE requests.id=?";
		jdbc.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setInt(1, newStatus);
					ps.setInt(2, requestID);
					return ps;
				}
			}
		);
		
		if (newStatus == 1) {
			//now need to find all the requests with same class / same time and reject them 
			//should it be DELETED or REJECTED?
			
			final String query2 = "SELECT * FROM requests WHERE id=?";
			TutorRequest request = jdbc.query(query2, 
					new PreparedStatementSetter() {
				public void setValues(PreparedStatement preparedStatement) throws SQLException {
					preparedStatement.setInt(1,  requestID);
				}
			}, 
			 new ResultSetExtractor<TutorRequest>() {
	            public TutorRequest extractData(ResultSet resultSet) throws SQLException,
	              DataAccessException {
	            	TutorRequest request = null;
	            	if (resultSet.next()) {
//	                	(int requestID, int tuteeID, int tutorID, String time, int status, Date timecreated)
	                	int requestID = resultSet.getInt("id");
	                	int tuteeID = resultSet.getInt("tutee_id");
	                	int tutorID = resultSet.getInt("tutor_id");
	                	String className = resultSet.getString("class");
	                	String time = resultSet.getString("time");
	                	int status = resultSet.getInt("status");
	                	Date timeCreated = resultSet.getDate("time_created");
	                	System.out.print(requestID);
	                	System.out.println(className + " " + time);
	                	
	                    request = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
	                }
	                return request;
	            }
			});
			
			//WARNING: this assumes tutoring is in 1 HR BLOCKS only 
			//this deletes requests from the same class and same tutee / same time and same tutee
			jdbc.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String query = "UPDATE requests SET requests.status=? WHERE (requests.class=? "
							+ "OR requests.time=?) AND requests.tutee_id=?";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setInt(1, 2);
					ps.setString(2, request.getClassName());
					ps.setString(3, request.getTime());
					ps.setInt(4, request.getTuteeID());
					return ps;
				}
			});
						
		}
		//still don't know what this boolean is supposed to be for. might jsut take it out 
		return true;
	}
	
	void updateUserInformation(User user) {
		jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String query = "UPDATE users SET password_hash=?, phone_number=?, first_name=?, last_name=? WHERE user_id=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, user.getPasswordHash());
				ps.setString(2, user.getPhoneNumber());
				ps.setString(3, user.getFirstName());
				ps.setString(4, user.getLastName());
				ps.setInt(5, user.getUserId());
				return ps;
			}
		});
	}
	
	List<Tutor> searchTutors(List<Integer> times, String className) {
		String query = "SELECT * FROM users, classes WHERE classes.class_name=? AND classes.tutor_id=users.user_id";
		List<Tutor> tutors = jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1,  className);
			}
		}, 
		 new ResultSetExtractor<List<Tutor>>() {
            public List<Tutor> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
            	ArrayList<Tutor> result = new ArrayList<>();
                while (resultSet.next()) {
                	System.out.println("tutor search");
//                	(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
        			//String availability)
                	
                	int userID = resultSet.getInt("user_id");
                	String firstName = resultSet.getString("first_name");
                	String lastName = resultSet.getString("last_name");
                	String email = resultSet.getString("email");
                	String phoneNumber = resultSet.getString("phone_number");
                	Boolean accountType = resultSet.getBoolean("tutor");
                	String availability = resultSet.getString("availability");
                	
                	result.add(new Tutor(userID, firstName, lastName, email, phoneNumber, accountType, availability));
                	
                }
                return result;
            }
		});
		
		
		
		List<Tutor> result = sortTutors(tutors, times);
		
		return result;
		
	}
	
	class SortTutorsByTime implements Comparator<Tutor> {
		private List<Integer> time;
		public SortTutorsByTime(List<Integer> time) {
			this.time = time;
		}
		
		public int compare(Tutor a, Tutor b) {
			List<Integer> timesA = a.getTimeAvailabilities();
			List<Integer> timesB = b.getTimeAvailabilities();
			System.out.println("comparing " + a.getEmail() + ", " + b.getEmail());
			int numA = 0;
			int numB = 0;
			for (Integer i : time) {
				if (timesA.contains(i)) {
					numA++;
				}
				if (timesB.contains(i)) {
					numB++;
				}
			}
			
			System.out.println("numa: " + numA + " numb: " + numB);
			System.out.println("numb - numa : " + (numA - numB));
			return numB - numA;
		}
		
	}
	
	private List<Tutor> sortTutors(List<Tutor> tutors, List<Integer> time) {
		PriorityQueue<Tutor> pq = new PriorityQueue<>(tutors.size(), new SortTutorsByTime(time));
		tutors.sort(new SortTutorsByTime(time));
//		for (Tutor t : tutors) {
//			pq.add(t);
//		}
////		Object[] tutors = pq.toArray();
////		for (Object t : tutors) {
////			Tutor hello = (Tutor)t;
////			System.out.println(hello.getEmail());
////		}
//		
//		ArrayList<Tutor> result = new ArrayList<>();
//		for (int i = 0; i < tutors.size(); i++) {
//			result.add(pq.poll());
//		}
		return tutors;
	}
	
	User authenticate(String email, String passwordHash) {
		// check Database to see email and password Hash
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		
//		try {
//			ps = conn.prepareStatement("SELECT * FROM users where email=?");
//			ps.setString(1, email);
//			rs = ps.executeQuery();
//			if(rs.next()) {
//				if(rs.getString("password_hash").equals(passwordHash)) {
//					//int userID, String firstName, String lastName, String email, String phoneNumber, Boolean accountType
//					return new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"),
//							rs.getString("email"), rs.getString("phone_num"), rs.getBoolean("tutor"));
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		// return null if wrong login info
//		return null;
		String query = "SELECT * FROM users WHERE email=?";
		User u = jdbc.query(query, 
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setString(1,  email);
					}
				}, 
				 new ResultSetExtractor<User>() {
		            public User extractData(ResultSet resultSet) throws SQLException,
		              DataAccessException {
		                if (resultSet.next()) {
//		                	(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
		        			//String availability)
		                	if (resultSet.getString("password_hash").equals(passwordHash)) {
		                		int userID = resultSet.getInt("user_id");
			                	String firstName = resultSet.getString("first_name");
			                	String lastName = resultSet.getString("last_name");
			                	String email = resultSet.getString("email");
			                	String phoneNumber = resultSet.getString("phone_number");
			                	Boolean accountType = resultSet.getBoolean("tutor");
			                	String availability = resultSet.getString("availability");
			                	System.out.println("authenticated");
			                	System.out.println(firstName + " " + lastName);
			                	if (accountType) {
			                		return new Tutor(userID, firstName, lastName, email, phoneNumber, accountType, availability);
			                	}
			                	else {
			                		return new Tutee(userID, firstName, lastName, email, phoneNumber, accountType);
			                	}
		                	}
		                	else {
		                		return null;
		                	}
		                	
		                	
		                	
		                }
		                return null;
		            }
				});
		
		return u;
		
	}
	
	Boolean addTutorToClass(int tutorID, String className) {
		String query = "INSERT INTO classes (class_name, tutor_id) VALUES (?,?)";
		return jdbc.execute(query,new PreparedStatementCallback<Boolean>(){  
		    @Override  
		    public Boolean doInPreparedStatement(PreparedStatement ps)  
		            throws SQLException, DataAccessException {  
		              
		        ps.setString(1, className);  
		        ps.setInt(2, tutorID);  
		              
		        return ps.execute();  
		              
		    }  
		    });  
		
	}
	
	//removes class from classes table abut also needs to remove requests that are to that tutor & class
	Boolean removeTutorFromClass(int tutorID, String className) {
		String query = "DELETE FROM classes WHERE tutor_id=? AND class_name=?";
		jdbc.execute(query, new PreparedStatementCallback<Boolean> () {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, tutorID);
				ps.setString(2, className);
				
				return ps.execute();
			}
		});
		
		String query2 = "DELETE FROM requests WHERE tutor_id=? AND class=?";
		jdbc.execute(query2, new PreparedStatementCallback<Boolean> () {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, tutorID);
				ps.setString(2, className);
				
				return ps.execute();
			}
		});
		
		
		return true;
	}
	
	
	//overwrites whatever is in there.
	//do we need separate functions for UPDATE and ADD? 
	void updateTutorAvailability(int userID, List<Integer> availability) {
		
		final String query = "UPDATE users SET availability=? WHERE user_id=?";
		jdbc.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String availabilityStr = "";
					for (int i = 0; i < availability.size(); i++) {
						availabilityStr += availability.get(i).toString() + " ";
					}
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, availabilityStr);
					ps.setInt(2, userID);
					return ps;
				}
			}
		); 
	}

	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
}
