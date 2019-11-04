package tutor.searcher.TutorSearcher;

import java.io.Serializable;
import java.util.List;

public class Tutee extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	public Tutee(int userID, String firstName, String lastName, String email, String phoneNumber, Boolean accountType) {
		// TODO Auto-generated constructor stub
		super(userID, firstName, lastName, email, phoneNumber, accountType);
	}

}
