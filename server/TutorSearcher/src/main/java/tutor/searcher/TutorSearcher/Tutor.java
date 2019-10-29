package tutor.searcher.TutorSearcher;

import java.util.List;

public class Tutor extends User {
	String timeAvailabilities;
	List<String> classesTutoring;
	
	public Tutor(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType, List<TutorRequest> pendingRequests,
			List<TutorRequest> acceptedRequests, List<TutorRequest> rejectedRequests, String 
			timeAvailabilities, List<String> classesTutoring) {
		super(userId, firstName, lastName, email, phoneNumber, accountType, pendingRequests, acceptedRequests, rejectedRequests);
		this.timeAvailabilities = timeAvailabilities;
		this.classesTutoring = classesTutoring;
		return;
	}
	
	public Tutor(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
			String availability) {
		super(userId, firstName, lastName, email, phoneNumber, accountType);
		this.timeAvailabilities = availability;
		
	}
	
	// Availability represented as String of numbers deliminated by spaces
	// ex: "1 2 3 5 6 7"
	// Monday 9am-10am is 1, 10am 2, 11am 3, 12pm 4, 1pm 5, 2pm 6, 3pm 7, 4pm 8
	// Tuesday 9am is 9, 4pm 16
	// Wednesday 9am is 17, 4pm 24
	// Thurs 25 - 32
	// Fri 33 = 40
	// Sat 41 - 48
	// Sun 49 - 56
	void updateAvailability(String times, int userID) {
		return;
	}
	void updateClasses(List<String> classes) {
		return;
	}
	void acceptRequest(TutorRequest request) {
		return;
	}
	void rejectRequest(TutorRequest request) {
		return;
	}
	
}
