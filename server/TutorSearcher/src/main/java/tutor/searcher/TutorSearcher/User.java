package tutor.searcher.TutorSearcher;
import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int UserID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Boolean accountType;
	private String passwordHash;
	private List<TutorRequest> pendingRequests;
	private List<TutorRequest> acceptedRequests;
	private List<TutorRequest> rejectedRequests;
	
	public User(int userId, String firstName, String lastName,
			String email, String phoneNumber, String passwordHash, Boolean accountType, List<TutorRequest> pendingRequests,
			List<TutorRequest> acceptedRequests, List<TutorRequest> rejectedRequests) {
		this.passwordHash = passwordHash;
		this.UserID = userId;
		this.firstName = firstName;
		this.lastName = firstName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.accountType = accountType;
		this.pendingRequests = pendingRequests;
		this.acceptedRequests = acceptedRequests;
		this.rejectedRequests = rejectedRequests;
	}
	
	
	public User(int userID, String firstName, String lastName, String email, String phoneNumber, String passwordHash, Boolean accountType) {
		super();
		UserID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.accountType = accountType;
		this.passwordHash = passwordHash;
	}

	public User(int userID, String firstName, String lastName, String email, String phoneNumber, Boolean accountType) {
		super();
		UserID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.accountType = accountType;
	}

	public int getUserId() {
		return UserID;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getFirstName() {
		return firstName;

	}
	
	public String getPasswordHash() {
		return passwordHash;
	}


	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}


	public String getLastName() {
		return lastName;
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
