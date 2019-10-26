package tutor.searcher.TutorSearcher;

import java.util.Date;
import java.util.List;


public class TutorRequest {
	private int requestID;
	private int tuteeID;
	private int tutorID;
	private int date;
	private List<Integer> time;
	private int status;
	private Date timecreated;
	
	
	public TutorRequest(int requestID, int tuteeID, int tutorID, int date, List<Integer> time, int status,
			Date timecreated) {
		this.requestID = requestID;
		this.tuteeID = tuteeID;
		this.tutorID = tutorID;
		this.date = date;
		this.time = time;
		this.status = status;
		this.timecreated = timecreated;
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
	
	public int getDate() {
		return date;
	}
	
	public void setDate(int date) {
		this.date = date;
	}
	
	public List<Integer> getTime() {
		return time;
	}
	
	public void setTime(List<Integer> time) {
		this.time = time;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Date getTimecreated() {
		return timecreated;
	}
}
