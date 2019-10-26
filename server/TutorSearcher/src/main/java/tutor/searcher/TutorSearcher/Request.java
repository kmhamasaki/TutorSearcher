package tutor.searcher.TutorSearcher;

import java.io.Serializable;
import java.util.HashMap;

public class Request implements Serializable {
    private String requestType;
    private HashMap<String, Object> attributes;

    Request(String requestType, HashMap<String, Object> attributes) {
        this.requestType = requestType;
        this.attributes = attributes;
    }
}