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


	public String getFirstName() {
		return firstName;

	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;

	} 
	
	public String getLastName() {
		return lastName;
	}
	
	
	public void setLastName(String lastName) {
		this.lastName = lastName;

	} 
	public String getPasswordHash() {
		return passwordHash;
	}


	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}


	public String getEmail() {
		return email;
	}
	
	public Boolean getAccountType() {
		return accountType;
	}
	

	
}
