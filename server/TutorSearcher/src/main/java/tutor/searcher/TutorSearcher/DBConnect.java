package tutor.searcher.TutorSearcher;

import java.util.List;

public class DBConnect {
	private int numUsers = 0;
	
	int getUserID(String email) {
		return 0;
	}
	String getPassword(String email) {
		return "pw";
	}
	int getAccountType(String email) {
		return 0;
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
