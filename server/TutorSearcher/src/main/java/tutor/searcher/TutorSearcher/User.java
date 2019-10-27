package tutor.searcher.TutorSearcher;
import java.util.List;

public class User {
	private int UserID;
	private String name;
	private String email;
	private Boolean accountType;
	private List<TutorRequest> pendingRequests;
	private List<TutorRequest> acceptedRequests;
	private List<TutorRequest> rejectedRequests;
	
	public User(int userId, String name, String email, Boolean accountType, List<TutorRequest> pendingRequests,
			List<TutorRequest> acceptedRequests, List<TutorRequest> rejectedRequests) {
		this.UserID = userId;
		this.name = name;
		this.email = email;
		this.accountType = accountType;
		this.pendingRequests = pendingRequests;
		this.acceptedRequests = acceptedRequests;
		this.rejectedRequests = rejectedRequests;
	}
	public int getUserId() {
		return UserID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Boolean getAccountType() {
		return accountType;
	}
	
	public List<TutorRequest> getPendingRequests() {
		return pendingRequests;
	}
	
	public List<TutorRequest> getAcceptedRequests() {
		return acceptedRequests;
	}
	
	public List<TutorRequest> getRejectedRequests() {
		return rejectedRequests;
	}

	
}
