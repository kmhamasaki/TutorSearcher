package tutor.searcher.TutorSearcher;

import java.util.List;

public class Tutee extends User {
	public Tutee(int userId, String firstName, String lastName, String passwordHash, String email, String phoneNumber, Boolean accountType, List<TutorRequest> pendingRequests,
			List<TutorRequest> acceptedRequests, List<TutorRequest> rejectedRequests) {
		//int userID, String firstName, String lastName, String email, String phoneNumber, String passwordHash, Boolean accountType
		super(userId, firstName, lastName, email, phoneNumber, passwordHash, accountType, pendingRequests, acceptedRequests, rejectedRequests);
	}

	public Tutee(int userID, String firstName, String lastName, String email, String phoneNumber, Boolean accountType) {
		// TODO Auto-generated constructor stub
		super(userID, firstName, lastName, email, phoneNumber, accountType);
	}

	void sendRequest(TutorRequest request) {
		return;
	}
}
