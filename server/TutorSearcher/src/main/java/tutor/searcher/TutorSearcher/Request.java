package tutor.searcher.TutorSearcher;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {

	private static final long serialVersionUID = 1;

	private String requestType;
	private HashMap<String, String> attributes;
	
	
	public Request(String requestType, HashMap<String, String> attributes) {
		this.requestType = requestType;
		this.attributes = attributes;
	}

	public String getRequestType() {
		return requestType;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}
	
	
	
	
}
