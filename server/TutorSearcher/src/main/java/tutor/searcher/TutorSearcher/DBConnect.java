package tutor.searcher.TutorSearcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
	
	public DBConnect() {
	}

	public DBConnect(JdbcTemplate jdbc) {
		
		this.jdbc = jdbc;
	}
	
	String getPassword(String email) {
		String query = "SELECT password_hash FROM users WHERE users.email = '" + email + "'";
		String result = jdbc.queryForObject(query, String.class);

		return result;
	}
	boolean getAccountType(String email) {
		String query = "SELECT tutor FROM users WHERE users.email = '" + email + "'";
		Boolean result = jdbc.queryForObject(query, Boolean.class);
		return result;
	}
	
	List<TutorRequest> getRequestsTuteeApproved(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, "
				+ "requests.time, requests.status, requests.time_created, usersTutor.first_name, usersTutee.first_name, usersTutor.rating, usersTutee.rating " +
				"FROM requests " +
				"JOIN users usersTutor ON usersTutor.user_id = requests.tutor_id " +
				"JOIN users usersTutee ON usersTutee.user_id = requests.tutee_id " +
				"WHERE requests.tutee_id = ? AND requests.status = 1";
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
                	String timeCreated = resultSet.getString("time_created");
                	System.out.print(requestID);
                	System.out.println(className + " " + time);

					String tuteeName = resultSet.getString("usersTutee.first_name");
					String tutorName = resultSet.getString("usersTutor.first_name");
					double tutorRating = resultSet.getDouble("usersTutor.rating");
					double tuteeRating = resultSet.getDouble("usersTutee.rating");

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					tutorRequest.setTutorRating(tutorRating);
					tutorRequest.setTuteeRating(tuteeRating);

					result.add(tutorRequest);
                }
                return result;
            }
		});
		return result; 
	}
	
	List<TutorRequest> getRequestsTuteeRejected(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, requests.time, "
				+ "requests.status, requests.time_created, usersTutor.first_name, usersTutee.first_name, usersTutor.rating, usersTutee.rating " + 
				"FROM requests JOIN users usersTutor ON usersTutor.user_id = requests.tutor_id " + 
				"				JOIN users usersTutee ON usersTutee.user_id = requests.tutee_id " + 
				"				WHERE requests.tutee_id = ? AND requests.status = 2";
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
                	String timeCreated = resultSet.getString("time_created");
                	System.out.print(requestID);
                	System.out.println(className + " " + time);

					String tutorName = resultSet.getString("usersTutor.first_name");
					String tuteeName = resultSet.getString("usersTutee.first_name");
					double tutorRating = resultSet.getDouble("usersTutor.rating");
					double tuteeRating = resultSet.getDouble("usersTutee.rating");

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					tutorRequest.setTutorRating(tutorRating);
					tutorRequest.setTuteeRating(tuteeRating);

					result.add(tutorRequest);
                }
                return result;
            }
		});
		return result; 
	}
	
	List<TutorRequest> getRequestsTutorUnapproved(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, requests.time, "
				+ "requests.status, requests.time_created, usersTutor.first_name, usersTutee.first_name, usersTutor.rating, usersTutee.rating " + 
				"FROM requests JOIN users usersTutor ON usersTutor.user_id = requests.tutor_id " + 
				"				JOIN users usersTutee ON usersTutee.user_id = requests.tutee_id " + 
				"				WHERE requests.tutor_id = ? AND requests.status = 0";
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
                	String timeCreated = resultSet.getString("time_created");
                	System.out.print(requestID);
                	System.out.println(className + " " + time);

					String tutorName = resultSet.getString("usersTutor.first_name");
					String tuteeName = resultSet.getString("usersTutee.first_name");
					double tutorRating = resultSet.getDouble("usersTutor.rating");
					double tuteeRating = resultSet.getDouble("usersTutee.rating");

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					tutorRequest.setTutorRating(tutorRating);
					tutorRequest.setTuteeRating(tuteeRating);

					result.add(tutorRequest);
                }
                return result;
            }
		});
		return result; 
	}
	
	List<TutorRequest> getRequestsTutorApproved(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, requests.time, requests.status, requests.time_created, usersTutor.first_name, usersTutee.first_name,"
				+ "usersTutor.rating, usersTutee.rating " + 
				"FROM requests JOIN users usersTutor ON usersTutor.user_id = requests.tutor_id " + 
				"				JOIN users usersTutee ON usersTutee.user_id = requests.tutee_id " + 
				"				WHERE requests.tutor_id = ? AND requests.status =1;";
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
                	String timeCreated = resultSet.getString("time_created");
                	System.out.print(requestID);
                	System.out.println(className + " " + time);

					String tutorName = resultSet.getString("usersTutor.first_name");
					String tuteeName = resultSet.getString("usersTutee.first_name");
					double tutorRating = resultSet.getDouble("usersTutor.rating");
					double tuteeRating = resultSet.getDouble("usersTutee.rating");

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					System.out.println("tutee: " + tuteeName);
					System.out.println("tutor: " + tutorName);
					System.out.println("tuteeID db" + tuteeID);
					System.out.println("tuteeid tr" + tutorRequest.getTuteeID());
					System.out.println("tutorID db" + tutorID);
					System.out.println("tutoRID tr" + tutorRequest.getTutorID());
					tutorRequest.setTutorRating(tutorRating);
					tutorRequest.setTuteeRating(tuteeRating);

					result.add(tutorRequest);
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
			+ "first_name, last_name, rating, num_ratings) VALUES (?,?,?,?,?,?,?, ?)";
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
			        ps.setDouble(7, -1);
			        ps.setInt(8, 0);
		            return ps;
		        }
		    },
		    keyHolder);
		System.out.println("adduser: " + keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
					
	}
	
	// what kind of error checking do we need for this? do i need to check for some stuff
	// in the backend logic?
	// to keep in mind: when a tutor accepts a tutee's request, that availability msut go off of the tutor's
	// time availability, and simultaneously tutee's requests for same class and time must die 
	int addRequest(int tuteeID, int tutorID, String className, String time, int status) {
		String query = "INSERT INTO requests (tutee_id, tutor_id, class, time, status, time_created) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		
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
			        Date date = new Date();
					Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String dateAsStr = formatter.format(date);

			        ps.setString(6, dateAsStr);
		            return ps;
		        }
		    },
		    keyHolder);
		System.out.println(keyHolder.getKey().intValue());
		return keyHolder.getKey().intValue();
	}
	
	//user ID is for the person getting rated 
	void addRating(int userID, double rating) {
		//need to get their current rating and average it
		final String firstQuery = "SELECT users.rating, users.num_ratings FROM users WHERE users.user_id=?";
		ArrayList<Object> currRatings = jdbc.query(firstQuery, 
				new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  userID);
			}
		}, 
		 new ResultSetExtractor<ArrayList<Object>>() {
            public ArrayList<Object> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
            	ArrayList<Object> result = new ArrayList<>();
            	if (resultSet.next()) {
//                	(int requestID, int tuteeID, int tutorID, String time, int status, Date timecreated)
            		result.add(resultSet.getDouble("rating"));
            		result.add(resultSet.getInt("num_ratings"));
                }
                return result;
            }
		});
		
		Double currRating = (Double)currRatings.get(0);
		int numRatings = (int)currRatings.get(1);
		
		
		final String query = "UPDATE users SET users.rating=?, users.num_ratings=? WHERE users.user_id=?";
		jdbc.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					double newRating = 0;
					//if (currRating == -)
					if (numRatings == 0) {
						newRating = rating;
					}
					else {
						newRating = (currRating * numRatings + rating) / (numRatings + 1);
					}
					
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setDouble(1, newRating);
					ps.setInt(2, numRatings + 1);
					ps.setInt(3, userID);
					return ps;
				}
			}
		);
	}
	// status
	// 0 = waiting approval
	// 1 = approved
	// 2 = rejected
	//what is the return boolean for? 
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
	                	String timeCreated = resultSet.getString("time_created");
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
							+ "OR requests.time=?) AND requests.tutee_id=? AND NOT requests.id=?";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setInt(1, 2);
					ps.setString(2, request.getClassName());
					ps.setString(3, request.getTime());
					ps.setInt(4, request.getTuteeID());
					ps.setInt(5, requestID);
					return ps;
				}
			});
			
			
			//NOW, need to update tutor's availability based on time that request was approved for
			
			String availabilityQuery = "SELECT users.availability FROM users WHERE user_id=?";
			String availability = jdbc.query(availabilityQuery, 
					new PreparedStatementSetter() {
						public void setValues(PreparedStatement preparedStatement) throws SQLException {
							preparedStatement.setInt(1,  request.getTutorID());
						}
					}, 
					 new ResultSetExtractor<String>() {
			            public String extractData(ResultSet resultSet) throws SQLException,
			              DataAccessException {
			                if (resultSet.next()) {
//			                	(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
			        			//String availability)
			                	
			                	return resultSet.getString("availability");
			                	
			                }
			                return null;
			            }
					});
			if (availability == null || availability.equals(""))  {
				return false;
			}
			
			jdbc.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					String[] availabilityStr = availability.split(" ");
					ArrayList<String> availabilities = new ArrayList<>(Arrays.asList(availabilityStr));
					availabilities.remove(request.getTime());
					String availabilitiesStr = "";
					for (int i = 0; i < availabilities.size(); i++) {
						availabilitiesStr += availabilities.get(i) + " ";
					}
					String query = "UPDATE users SET users.availability=? WHERE users.user_id = ?";
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, availabilitiesStr);
					ps.setInt(2, request.getTutorID());
					return ps;
				}
			});
						
		}
		//still don't know what this boolean is supposed to be for. might jsut take it out 
		return true;
	}
	
	
	void updateUserInformation(User user) {
		System.out.println("updateUserInformation " + user.getFirstName());
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
	
	List<Tutor> searchTutors(int userID, ArrayList<Integer> times, String className) {
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
                	double rating = resultSet.getDouble("rating");
                	if (availability != null) {
                    	System.out.println("adding " + email);
                    	result.add(new Tutor(userID, firstName, lastName, email, phoneNumber, accountType, availability, rating));

                	}
                	
                }
                return result;
            }
		});
		
		final String updateQuery = "UPDATE users SET tutee_search_class=?, tutee_search_times=? WHERE user_id=?";
		jdbc.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					
					PreparedStatement ps = connection.prepareStatement(updateQuery);
					ps.setString(1, className);
					String timeAvailabilities = "";
					for (int i = 0; i < times.size(); i++) {
						timeAvailabilities += times.get(i).toString() + " " ;
					}
					ps.setString(2, timeAvailabilities);
					ps.setInt(3, userID);
					return ps;
				}
			}
		); 
		
		List<Tutor> result = sortTutors(tutors, times);
		
		return result;
		
	}
	//could probably be optimized into one sql query 
	List<Tutor> searchTutorsPrevious(int userID) {
		String query = "SELECT * FROM users WHERE user_id= ?";
		String previousSearchTime = "";
		String previousSearchClass = "";
		
		//key is times, value is class
		ArrayList<String> result = jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  userID);
			}
		}, 
		 new ResultSetExtractor<ArrayList<String>>() {
            public ArrayList<String> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
                if (resultSet.next()) {
//                	(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
        			//String availability)
                	String previousSearchTime = resultSet.getString("tutee_search_times");
                	String previousSearchClass = resultSet.getString("tutee_search_class");
                	if (previousSearchTime == null || previousSearchClass == null) {
                		return new ArrayList<>();
                	}
                	ArrayList<String> result = new ArrayList<>();
                	result.add(previousSearchTime);
                	result.add(previousSearchClass);
                    return result;

                	
                }
                return new ArrayList<>();

            }
		});
		
		if (result.isEmpty()) {
			return null;
		}
		
		if (result.get(0).equals("") || result.get(1).equals("") || result.get(0) == null || result.get(1) == null) {
			return null;
		}
		
		
		ArrayList<Integer> times = new ArrayList<>();
		String[] timesStr = result.get(0).split(" ");
		for (int i = 0; i < timesStr.length; i++) {
			times.add(Integer.parseInt(timesStr[i]));
		}
		return searchTutors(userID, times, result.get(1));
	}
	
	class SortTutorsByTime implements Comparator<Tutor> {
		private ArrayList<Integer> time;
		public SortTutorsByTime(ArrayList<Integer> time) {
			this.time = time;
		}
		
		public int compare(Tutor a, Tutor b) {
			List<Integer> timesA = a.getTimeAvailabilities();
			List<Integer> timesB = b.getTimeAvailabilities();
			System.out.println("comparing " + a.getEmail() + ", " + b.getEmail());
			int numA = 0;
			int numB = 0;
			ArrayList<Integer> aMatching = null;
			if (a.getMatchingAvailabilities() == null) {
				aMatching =  new ArrayList<>();

			}	
			ArrayList<Integer> bMatching = null;	
			if (b.getMatchingAvailabilities() == null) {
				bMatching = new ArrayList<>();
			}
			
			if (aMatching != null || bMatching != null) {
				for (Integer i : time) {
					if (aMatching != null && timesA.contains(i)) {
						aMatching.add(i);
					}
					if (bMatching != null && timesB.contains(i)) {
						bMatching.add(i);
					}
				}

			}
			
			if (aMatching != null) {
				a.setMatchingAvailabilities(aMatching);
			}
			if (bMatching != null) {
				b.setMatchingAvailabilities(bMatching);
			}
			
			System.out.println("numa: " + a.getMatchingAvailabilities().size() + " numb: " + b.getMatchingAvailabilities().size());
			return b.getMatchingAvailabilities().size() - a.getMatchingAvailabilities().size();
		}
		
	}
	
	private List<Tutor> sortTutors(List<Tutor> tutors, ArrayList<Integer> time) {
		if (tutors.size() == 1) {
			if (tutors.get(0).getMatchingAvailabilities() == null) {
				tutors.get(0).setMatchingAvailabilities(new ArrayList<>());
			}
			for (Integer i : time) {
				if (tutors.get(0).getTimeAvailabilities().contains(i)) {
					tutors.get(0).getMatchingAvailabilities().add(i);
				}
			}
			
			return tutors;
		}
		tutors.sort(new SortTutorsByTime(time));
		

		return tutors;
	}
	
	User authenticate(String email, String passwordHash) {
		// check Database to see email and password Hash

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
			                	double rating = resultSet.getDouble("rating");
			                	System.out.println("authenticated");
			                	System.out.println(firstName + " " + lastName);
			                	if (accountType) {
			                		return new Tutor(userID, firstName, lastName, email, phoneNumber, accountType, availability, rating);
			                	}
			                	else {
			                		return new Tutee(userID, firstName, lastName, email, phoneNumber, accountType, rating);
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
	
	Boolean addTutorToClass(int tutorID, ArrayList<String> className) {
		//delete all classes tutor is tutoring first (want overwrite)
		String deleteQuery = "DELETE FROM classes WHERE tutor_id=?";
		jdbc.execute(deleteQuery, new PreparedStatementCallback<Boolean> () {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, tutorID);
				
				return ps.execute();
			}
		});
		
		String values = "";
		for (int i = 0; i < className.size(); i++) {
			if (i == className.size() - 1) {
				values += "(?,?);";
			}
			else {
				values += "(?,?),";
			}
			
		}
		String query = "INSERT INTO classes (class_name, tutor_id) VALUES " + values;
		return jdbc.execute(query,new PreparedStatementCallback<Boolean>(){  
		    @Override  
		    public Boolean doInPreparedStatement(PreparedStatement ps)  
		            throws SQLException, DataAccessException {  
		              
		    	for (int i = 0 ; i < className.size(); i++) {
		    		System.out.println( i * 2 + 1 + " " + className.get(i));
		    		ps.setString(i * 2 + 1, className.get(i));  
		    		System.out.println(i * 2 + 2);
			        ps.setInt(i * 2+ 2, tutorID);
		    	}
		          
		              
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
	
	//get availability
	ArrayList<Integer> getTutorAvailability(int tutorID) {
		String query = "SELECT * FROM users WHERE user_id=?";
		String availability = jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  tutorID);
			}
		}, 
		 new ResultSetExtractor<String>() {
            public String extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
                if (resultSet.next()) {
                	System.out.println("tutor search");
//                	(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
        			//String availability)
                	String availability = resultSet.getString("availability");
                	return availability;
                	
                	
                }
                return null;
            }
		});
		if (availability != null && !availability.equals("")) {
			String[] timesStr = availability.split(" ");
			ArrayList<Integer> availabilities = new ArrayList<>();
			for (int i = 0; i < timesStr.length; i++) {
				availabilities.add(Integer.parseInt(timesStr[i]));
			}
			return availabilities;
		}
		
		return new ArrayList<>();
	}
	
	//get classes
	ArrayList<String> getTutorClasses(int tutorID) {
		String query = "SELECT * FROM classes WHERE tutor_id=?";
		ArrayList<String> classes = jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  tutorID);
			}
		}, 
		 new ResultSetExtractor<ArrayList<String>>() {
            public ArrayList<String> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
            	ArrayList<String> result = new ArrayList<>();
                while (resultSet.next()) {
                	System.out.println("tutor search");
//                	(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
        			//String availability)
                	result.add(resultSet.getString("class_name"));
                	
                }
                return result;
            }
		});
		
		return classes;
	}
	
	User getUserInformation(int userID) {
		String query = "SELECT * FROM users WHERE user_id=?";
		return jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  userID);
			}
		}, 
		 new ResultSetExtractor<User>() {
            public User extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
                if (resultSet.next()) {

                	//	(int userID, String firstName, String lastName, String email, String phoneNumber, String passwordHash, Boolean accountType) {
                	return new User(resultSet.getInt("user_id"), resultSet.getString("first_name"), resultSet.getString("last_name"),
                			resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("password_hash"),
                			resultSet.getBoolean("tutor"), resultSet.getDouble("rating"));
                	
                	
                }
                return null;
            }
		});
	}

	public JdbcTemplate getJdbc() {
		return jdbc;
	}

	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
}
