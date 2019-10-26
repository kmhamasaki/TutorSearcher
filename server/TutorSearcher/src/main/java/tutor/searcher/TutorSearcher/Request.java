package tutor.searcher.TutorSearcher;

import java.util.Map;

public class Request {
	private String requestType;
	private Map<String,String> attributes;
	
	
	public Request(String requestType, Map<String, String> attributes) {
		this.requestType = requestType;
		this.attributes = attributes;
	}

	public String getRequestType() {
		return requestType;
	}

	public Map<String,String> getAttributes() {
		return attributes;
	}
	
	
	
	
}
