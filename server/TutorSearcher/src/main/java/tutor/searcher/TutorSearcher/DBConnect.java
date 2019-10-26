package tutor.searcher.TutorSearcher;

import java.util.List;

public class DBConnect {
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
		return 0;
	}
	Boolean updateRequestStatus(int requestID, int newStatus) {
		return false;
	}
	List<Tutor> searchTutors(List<List<Boolean>> times, String className) {
		return null;
	}
	User authenticate(String email, String passwordHash) {
		return null;
	}
	Boolean addTutorToClass(int tutorID) {
		return false;
	}
	Boolean removeTutorFromClass(int tutorID, String className) {
		return false;
	}
}
