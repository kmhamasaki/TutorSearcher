package tutor.searcher.TutorSearcher;

import java.util.List;

public class Tutee extends User {
	public Tutee(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType, List<TutorRequest> pendingRequests,
			List<TutorRequest> acceptedRequests, List<TutorRequest> rejectedRequests) {
		super(userId, firstName, lastName, email, phoneNumber, accountType, pendingRequests, acceptedRequests, rejectedRequests);
	}

	void sendRequest(TutorRequest request) {
		return;
	}
}
