package tutor.searcher.TutorSearcher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tutor extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	List<Integer> timeAvailabilities;
	String timeAvailability;
	List<String> classesTutoring;
	ArrayList<Integer> matchingAvailabilities;

	public Tutor(int userId, String firstName, String lastName, String email, String phoneNumber, String passwordHash, Boolean accountType,
			String availability, double rating) {
		super(userId, firstName, lastName, email, phoneNumber, passwordHash, accountType, rating);
		this.timeAvailability = availability;
		if (availability != null && !availability.equals("")) {
			String[] timesStr = availability.split(" ");
			this.timeAvailabilities = new ArrayList<>();
			for (int i = 0; i < timesStr.length; i++) {
				this.timeAvailabilities.add(Integer.parseInt(timesStr[i]));
			}
		}
		
		
	}
	
	public Tutor(int userId, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
			String availability, double rating) {
		super(userId, firstName, lastName, email, phoneNumber, accountType, rating);
		this.timeAvailability = availability;
		if (availability != null && !availability.equals("")) {
			String[] timesStr = availability.split(" ");
			this.timeAvailabilities = new ArrayList<>();
			for (int i = 0; i < timesStr.length; i++) {
				this.timeAvailabilities.add(Integer.parseInt(timesStr[i]));
			}
		}
		
		
	}

	// Availability represented as String of numbers deliminated by spaces
	// ex: "1 2 3 5 6 7"
	// Monday 9am-10am is 1, 10am 2, 11am 3, 12pm 4, 1pm 5, 2pm 6, 3pm 7, 4pm 8
	// Tuesday 9am is 9, 4pm 16
	// Wednesday 9am is 17, 4pm 24
	// Thurs 25 - 32
	// Fri 33 = 40
	// Sat 41 - 48
	// Sun 49 - 56

	public Tutor(int userID, String firstName, String lastName, String email, String phoneNumber, Boolean accountType,
			String availability, double rating, String bio) {
		// TODO Auto-generated constructor stub
		super(userID, firstName, lastName, email, phoneNumber,  accountType, rating, bio);
		this.timeAvailability = availability;
		if (availability != null && !availability.equals("")) {
			String[] timesStr = availability.split(" ");
			this.timeAvailabilities = new ArrayList<>();
			for (int i = 0; i < timesStr.length; i++) {
				this.timeAvailabilities.add(Integer.parseInt(timesStr[i]));
			}
		}
		
	}

	public List<Integer> getTimeAvailabilities() {
		return timeAvailabilities;
	}

	public void setTimeAvailabilities(List<Integer> timeAvailabilities) {
		this.timeAvailabilities = timeAvailabilities;
	}

	public ArrayList<Integer> getMatchingAvailabilities() {
		return matchingAvailabilities;
	}

	public void setMatchingAvailabilities(ArrayList<Integer> matchingAvailabilities) {
		this.matchingAvailabilities = matchingAvailabilities;
	}
	
}
