package tutor.searcher.TutorSearcher;

public class AcceptedTutorRequest {
	private String email;
	private String phoneNumber;
	private String name;
	private String className;
	private double rating;
	
	
	public AcceptedTutorRequest(String email, String phoneNumber, String name, String className, double rating) {
		super();
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.name = name;
		this.className = className;
		this.rating = rating;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
}
