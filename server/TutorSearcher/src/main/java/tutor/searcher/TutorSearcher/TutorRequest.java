package tutor.searcher.TutorSearcher;

import java.io.Serializable;
import java.util.Date;


public class TutorRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private int requestID;
	private int tuteeID;
	private String tuteeName;
	private String tutorName;
	private int tutorID;
	private String time;
	private int status;
	private String timeCreated;
	private String className;
	private String bio;
	private double tuteeRating;
	private double tutorRating;
	
	private int givenTuteeRating;
	private int givenTutorRating;
	
	private String email;
	private String phoneNumber;
	private String name;

	private String profilePictureBlob;

	public String getProfilePictureBlob() {
		return profilePictureBlob;
	}

	public void setProfilePictureBlob(String profilePictureBlob) {
		this.profilePictureBlob = profilePictureBlob;
	}

	public int getGivenTutorRating() {
		return givenTutorRating;
	}

	public void setGivenTutorRating(int givenTutorRating) {
		this.givenTutorRating = givenTutorRating;
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

	public int getGivenTuteeRating() {
		return givenTuteeRating;
	}

	public void setGivenTuteeRating(int givenTuteeRating) {
		this.givenTuteeRating = givenTuteeRating;
	}
	
	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}


	public TutorRequest(int requestID, int tuteeID, int tutorID, String time, int status,
			String timecreated, String className) {
		this.requestID = requestID;
		this.tuteeID = tuteeID;
		this.tutorID = tutorID;
		this.time = time;
		this.status = status;
		this.timeCreated = timecreated;
		this.className = className;
		givenTuteeRating = -1;
		givenTutorRating = -1;
	}
	
	public TutorRequest(int requestID, int tuteeID, int tutorID, String time, int status,
			String timecreated, String className, String bio) {
		this.requestID = requestID;
		this.tuteeID = tuteeID;
		this.tutorID = tutorID;
		this.time = time;
		this.status = status;
		this.timeCreated = timecreated;
		this.className = className;
		this.bio = bio;
		givenTuteeRating = -1;
		givenTutorRating = -1;
	}

	public String getTuteeName() {
		return tuteeName;
	}

	public void setTuteeName(String tuteeName) {
		this.tuteeName = tuteeName;
	}

	public String getTutorName() {
		return tutorName;
	}

	public void setTutorName(String tutorName) {
		this.tutorName = tutorName;
	}

	public int getRequestID() {
		return requestID;
	}
	
	public int getTuteeID() {
		return tuteeID;
	}
	
	public int getTutorID() {
		return tutorID;
	}

	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getTimeCreated() {
		return timeCreated;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public double getTuteeRating() {
		return tuteeRating;
	}

	public void setTuteeRating(double tuteeRating) {
		this.tuteeRating = tuteeRating;
	}


	public double getTutorRating() {
		return tutorRating;
	}

	public void setTutorRating(double tutorRating) {
		this.tutorRating = tutorRating;
	}
	
}
