package tutor.searcher.TutorSearcher;

import java.util.List;

public class Tutee extends User {
	public Tutee(int userId, String name, String email, Boolean accountType, List<TutorRequest> pendingRequests,
			List<TutorRequest> acceptedRequests, List<TutorRequest> rejectedRequests) {
		super(userId, name, email, accountType, pendingRequests, acceptedRequests, rejectedRequests);
	}

	void sendRequest(TutorRequest request) {
		return;
	}
}
