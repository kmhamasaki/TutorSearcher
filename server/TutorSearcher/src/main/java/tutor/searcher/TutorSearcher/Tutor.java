package tutor.searcher.TutorSearcher;

import java.util.List;

public class Tutor extends User {
	List<List<Boolean>> timeAvailabilities;
	List<String> classesTutoring;
	
	public Tutor(int userId, String name, String email, Boolean accountType, List<TutorRequest> pendingRequests,
			List<TutorRequest> acceptedRequests, List<TutorRequest> rejectedRequests, List<List<Boolean>> 
			timeAvailabilities, List<String> classesTutoring) {
		super(userId, name, email, accountType, pendingRequests, acceptedRequests, rejectedRequests);
		this.timeAvailabilities = timeAvailabilities;
		this.classesTutoring = classesTutoring;
		return;
	}
	
	void updateAvailability(List<List<Boolean>> times) {
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
