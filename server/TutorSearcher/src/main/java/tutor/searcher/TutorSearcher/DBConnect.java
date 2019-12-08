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
import java.util.function.Predicate;

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
	
	double getTotalTutorRating(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, "
				+ "requests.time, requests.status, requests.time_created, requests.tutor_rating " +
				"FROM requests " +
				"WHERE requests.tutor_id = ? AND requests.status = 1";
		List<Integer> requests = jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  userID);
			}
		}, 
		 new ResultSetExtractor<List<Integer>>() {
            public List<Integer> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
            	ArrayList<Integer> result = new ArrayList<>();
                while (resultSet.next()) {                	
					int tutorRating = resultSet.getInt("tutor_rating");
					result.add(tutorRating);
                }
                return result;
            }
		});
		double sum = 0;
		double num = 0;
		for (Integer i : requests) {
			if (i != -1) {
				sum += i;
				num++;
			}
		}
		if (num == 0) {
			return -1;
		}
		return sum / num;
	}
	
	double getTotalTuteeRating(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, "
				+ "requests.time, requests.status, requests.time_created, requests.tutee_rating " +
				"FROM requests " +
				"WHERE requests.tutee_id = ? AND requests.status = 1";
		List<Integer> requests = jdbc.query(query, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  userID);
			}
		}, 
		 new ResultSetExtractor<List<Integer>>() {
            public List<Integer> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
            	ArrayList<Integer> result = new ArrayList<>();
                while (resultSet.next()) {                	
					int tuteeRating = resultSet.getInt("tutee_rating");
					result.add(tuteeRating);
					System.out.println("adding rating " + tuteeRating);
                }
                return result;
            }
		});
		double sum = 0;
		double num = 0;
		for (Integer i : requests) {
			if (i != -1) {
				sum += i;
				num++;
			}
		}
		if (num == 0) {
			return -1;
		}
		return sum / num;
		
	}
	List<TutorRequest> getRequestsTuteeApproved(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, "
				+ "requests.time, requests.status, requests.time_created, requests.tutee_rating, requests.tutor_rating, "
				+ "usersTutor.first_name, usersTutee.first_name, usersTutor.rating, usersTutee.rating " +
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
				

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					

					int givenTutorRating = resultSet.getInt("tutor_rating");
					int givenTuteeRating = resultSet.getInt("tutee_rating");
					tutorRequest.setGivenTuteeRating(givenTuteeRating);
					tutorRequest.setGivenTutorRating(givenTutorRating);
					
					double tutorRating = getTotalTutorRating(tutorID);
					//double tutee_rating = getTotalTuteeRating(tuteeID);
					tutorRequest.setTutorRating(tutorRating);
					
					result.add(tutorRequest);
                }
                return result;
            }
		});
		return result; 
	}
	
	List<TutorRequest> getRequestsTuteeRejected(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, requests.time, "
				+ "requests.status, requests.tutee_rating, requests.tutor_rating, requests.time_created, usersTutor.first_name, usersTutee.first_name, usersTutor.rating, usersTutee.rating " + 
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
					

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					
					int givenTutorRating = resultSet.getInt("tutor_rating");
					int givenTuteeRating = resultSet.getInt("tutee_rating");
					tutorRequest.setGivenTuteeRating(givenTuteeRating);
					tutorRequest.setGivenTutorRating(givenTutorRating);
					
					double tutorRating = getTotalTutorRating(tutorID);
					tutorRequest.setTuteeRating(tutorRating);

					result.add(tutorRequest);
                }
                return result;
            }
		});
		return result; 
	}
	
	List<TutorRequest> getRequestsTutorUnapproved(int userID) {
		String query = "SELECT requests.id, requests.tutee_id, requests.tutor_id, requests.class, requests.time, "
				+ "requests.status, requests.tutee_rating, requests.tutor_rating, requests.time_created, usersTutor.first_name, usersTutee.first_name, usersTutor.rating, usersTutee.rating " + 
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
					

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					
					int givenTutorRating = resultSet.getInt("tutee_rating");
					int givenTuteeRating = resultSet.getInt("tutor_rating");

					tutorRequest.setGivenTuteeRating(givenTuteeRating);
					tutorRequest.setGivenTutorRating(givenTutorRating);
					
					double tuteeRating = getTotalTuteeRating(tuteeID);
					System.out.println("here1" + tuteeRating);
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
				+ "usersTutor.rating, usersTutee.rating, requests.tutee_rating, requests.tutor_rating " + 
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
					

					TutorRequest tutorRequest = new TutorRequest(requestID, tuteeID, tutorID, time, status, timeCreated, className);
					tutorRequest.setTuteeName(tuteeName);
					tutorRequest.setTutorName(tutorName);
					System.out.println("tutee: " + tuteeName);
					System.out.println("tutor: " + tutorName);
					System.out.println("tuteeID db" + tuteeID);
					System.out.println("tuteeid tr" + tutorRequest.getTuteeID());
					System.out.println("tutorID db" + tutorID);
					System.out.println("tutoRID tr" + tutorRequest.getTutorID());
					
					double tuteeRating = getTotalTuteeRating(tuteeID);
					tutorRequest.setTuteeRating(tuteeRating);
					
					int givenTutorRating = resultSet.getInt("tutor_rating");
					int givenTuteeRating = resultSet.getInt("tutee_rating");
					tutorRequest.setGivenTuteeRating(givenTuteeRating);
					tutorRequest.setGivenTutorRating(givenTutorRating);

					result.add(tutorRequest);
                }
                return result;
            }
		});
		return result; 
	}
	
	
	//add user, checks if email already exists, otherwise add user
	// accountType 
	// true = tutor
	// false = tutee
	int addUser(String email, String passwordHash, String firstName, String lastName, String phoneNumber,
			Boolean accountType, String bio) {
		
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
						return resultSet.next();
					}
				});
		
		if (exists) {
			return -1;
		}
		
		String insertQuery = "INSERT INTO users (email, password_hash, tutor, phone_number,"
			+ "first_name, last_name, rating, num_ratings, bio, profile_picture_blob) VALUES (?,?,?,?,?,?,?, ?, ?, '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')";
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
			        ps.setString(9, bio);
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
		String checkQuery = "SELECT * FROM requests WHERE tutee_id=? AND (time=? OR class=?) AND status=1";
		Boolean exists = jdbc.query(checkQuery, 
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setInt(1,  tuteeID);
						preparedStatement.setString(2, time);
						preparedStatement.setString(3, className);
					}
				}, 
				 new ResultSetExtractor<Boolean>() {
		            public Boolean extractData(ResultSet resultSet) throws SQLException,
		              DataAccessException {
						return resultSet.next();
					}
				});
		
		if (exists) {
			return -1;
		}
		
		String query = "INSERT INTO requests (tutee_id, tutor_id, class, time, status, time_created, tutee_rating, tutor_rating) "
				+ "VALUES (?, ?, ?, ?, ?, ?, -1, -1)";
		
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
	//account type is the ACCOUNT TYPE THAT IS RECEIVING THE RATING 
	void updateRating(int requestID, int rating, boolean accountType) {
		if (accountType == true) { //UPDATE TUTOR'S RATING
			final String query = "UPDATE requests SET requests.tutor_rating=? WHERE requests.id=?";
			jdbc.update(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setInt(1, rating);
						ps.setInt(2, requestID);
						return ps;
					}
				}
			);
			
		}
		else {
			final String query = "UPDATE requests SET requests.tutee_rating=? WHERE requests.id=?";
			jdbc.update(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(query);
						ps.setInt(1, rating);
						ps.setInt(2, requestID);
						return ps;
					}
				}
			);
		}
		
	}
	
	//user ID is for the person getting rated 
	/*
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
	} */
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
				String query = "UPDATE users SET password_hash=?, phone_number=?, first_name=?, last_name=?, bio=? WHERE user_id=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, user.getPasswordHash());
				ps.setString(2, user.getPhoneNumber());
				ps.setString(3, user.getFirstName());
				ps.setString(4, user.getLastName());
				ps.setInt(5, user.getUserId());
				ps.setString(6, user.getBio());
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
                	String profilePic = resultSet.getString("profile_picture_blob");
                	double rating = 0;
                	if (accountType) {
                		rating = getTotalTutorRating(userID);
                	}
                	else {
                		rating = getTotalTuteeRating(userID);
                	}
                	String bio = resultSet.getString("bio");
                	if (availability != null) {
                    	System.out.println("adding " + email);
                    	Tutor tutor = new Tutor(userID, firstName, lastName, email, phoneNumber, accountType, availability, rating, bio);
                    	tutor.setProfilePictureBlob(profilePic);
                    	result.add(tutor);

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
	ArrayList<Object> searchTutorsPrevious(int userID) {
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
		
		ArrayList<Object> pairthing = new ArrayList<>();
		pairthing.add(searchTutors(userID, times, result.get(1)));
		pairthing.add(result.get(1));
		return pairthing;
	}


	public void updateProfilePicture(int userID, String profilePicBlob) {
		System.out.println("updateProfilePicture " + userID);
		jdbc.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String query = "UPDATE users SET profile_picture_blob=? WHERE user_id=?";
				PreparedStatement ps = connection.prepareStatement(query);
				ps.setString(1, profilePicBlob);
				ps.setInt(2, userID);
				return ps;
			}
		});
	}

	public String getProfilePicBlob(int userID) {
		String query = "SELECT * FROM users WHERE user_id=?";
		String profile_picture_blob = jdbc.query(query,
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setInt(1,  userID);
					}
				},
				new ResultSetExtractor<String>() {
					public String extractData(ResultSet resultSet) throws SQLException,
							DataAccessException {
						String profile_picture_blob = "";
						if (resultSet.next()) {
							profile_picture_blob = resultSet.getString("profile_picture_blob");
						}
						return profile_picture_blob;
					}
				});
		return profile_picture_blob;
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
		
		tutors.removeIf(new NoMatchTimesPredicate<Tutor>());
		
		return tutors;
	}
	
	class NoMatchTimesPredicate<T> implements Predicate<T> {

		@Override
		public boolean test(T t) {
			// TODO Auto-generated method stub
			Tutor tutor = (Tutor)t;
			return tutor.getMatchingAvailabilities().isEmpty();
		}
		
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
			                	String bio = resultSet.getString("bio");
			                	if (accountType) {
			                		return new Tutor(userID, firstName, lastName, email, phoneNumber, accountType, availability, rating, bio);
			                	}
			                	else {
			                		return new Tutee(userID, firstName, lastName, email, phoneNumber, accountType, rating, bio);
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
		Boolean deleteSucc = jdbc.execute(deleteQuery, new PreparedStatementCallback<Boolean> () {
			@Override
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setInt(1, tutorID);
				
				return ps.execute();
			}
		});
		
		if(className.size()==0) {
			return deleteSucc;
		}
		
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
		
		System.out.println("updating availability: ");
		for (Integer i : availability) {
			System.out.println(i);
		}
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
		
		//now need to go through *pending* requests that don't match tutor's current availabilit yand set to rejected
		String queryRequests = "SELECT * FROM requests WHERE tutor_id=?";
		ArrayList<TutorRequest> requests = jdbc.query(queryRequests, 
		new PreparedStatementSetter() {
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setInt(1,  userID);
			}
		}, 
		 new ResultSetExtractor<ArrayList<TutorRequest>>() {
            public ArrayList<TutorRequest> extractData(ResultSet resultSet) throws SQLException,
              DataAccessException {
            	ArrayList<TutorRequest> result = new ArrayList<>();
                while (resultSet.next()) {

                	//(int requestID, int tuteeID, int tutorID, String time, int status,
        			//String timecreated, String className)
                	TutorRequest tutorRequest = new TutorRequest(resultSet.getInt("id"), resultSet.getInt("tutee_id"), 
                			resultSet.getInt("tutor_id"), resultSet.getString("time"), resultSet.getInt("status"),
                			resultSet.getString("time_created"), resultSet.getString("class"));
                	if (tutorRequest.getStatus() == 0) {
                		if (!availability.contains(tutorRequest.getTime())) {
                			//this means that new availability doesn't contain the time
                			// that was previously requested anymore
                			//so we need to delete it
                			result.add(tutorRequest);
            			}
                	}
                	
                	
                }
                return result;
            }
		});
		
		if (!requests.isEmpty()) {
			String requestIDs = "";
			for (int i = 0 ; i < requests.size(); i++) {
				if (i == requests.size() - 1) {
					requestIDs += requests.get(i).getRequestID();
				}
				else {
					requestIDs += requests.get(i).getRequestID() + ",";
				}
			}
			//the reuqests are requests that need to be deleted, so let's delete it all at once
			String updateQuery = "UPDATE requests SET status=2 WHERE id IN (" + requestIDs + ")";
			System.out.println("executing " + updateQuery);
			jdbc.update(updateQuery);
		}
		
		
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

                	// Need to add
                	// , resultSet.getString("profile_picture_blob")

                	//	(int userID, String firstName, String lastName, String email, String phoneNumber, String passwordHash, Boolean accountType) {
                	return new User(resultSet.getInt("user_id"), resultSet.getString("first_name"), resultSet.getString("last_name"),
                			resultSet.getString("email"), resultSet.getString("phone_number"), resultSet.getString("password_hash"),
                			resultSet.getBoolean("tutor"), resultSet.getDouble("rating"), resultSet.getString("bio"), resultSet.getString("profile_picture_blob"));
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
	
	public void addBio(String bio, int userID) {
		final String query = "UPDATE users SET users.bio=? WHERE user_id=?";
		jdbc.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(query);
					ps.setString(1, bio);
					ps.setInt(2, userID);
					return ps;
				}
			}
		);
	}
	
	public String getBio(int userID) {
		String query = "SELECT * FROM users WHERE user_id=?";
		String bio = jdbc.query(query, 
				new PreparedStatementSetter() {
					public void setValues(PreparedStatement preparedStatement) throws SQLException {
						preparedStatement.setInt(1,  userID);
					}
				}, 
				 new ResultSetExtractor<String>() {
		            public String extractData(ResultSet resultSet) throws SQLException,
		              DataAccessException {
		            	String bio = "";
		            	if (resultSet.next()) {
		            		bio = resultSet.getString("bio");
		            	}
						return bio;
					}
				});
		return bio;
	}
}
