package tutor.searcher.TutorSearcher;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	private String requestType;
	private HashMap<String, Object> attributes;
	
	
	public Request(String requestType, HashMap<String, Object> respAttr) {
		this.requestType = requestType;
		this.attributes = respAttr;
	}


	public String getRequestType() {
		return requestType;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}
	
	public Object get(String key) {
		return attributes.get(key);
	}
	
	
	
	
}
