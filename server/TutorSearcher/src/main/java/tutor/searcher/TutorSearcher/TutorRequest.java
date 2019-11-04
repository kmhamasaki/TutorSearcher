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
	private Date timeCreated;
	private String className;
	
	
	public TutorRequest(int requestID, int tuteeID, int tutorID, String time, int status,
			Date timecreated, String className) {
		this.requestID = requestID;
		this.tuteeID = tuteeID;
		this.tutorID = tutorID;
		this.time = time;
		this.status = status;
		this.timeCreated = timecreated;
		this.className = className;
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
	
	public Date getTimeCreated() {
		return timeCreated;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
