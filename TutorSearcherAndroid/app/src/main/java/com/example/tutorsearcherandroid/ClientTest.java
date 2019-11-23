package com.example.tutorsearcherandroid;

import java.util.ArrayList;
import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.TutorRequest;
import tutor.searcher.TutorSearcher.User;

public class ClientTest extends Client {

    ClientTest() {}

    ClientTest(String incomingRequestType, HashMap<String,Object> incomingAttributes) {
        this.incomingAttributes = incomingAttributes;
        this.incomingRequestType = incomingRequestType;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HashMap<String, Object> attr = new HashMap<>();

        if(incomingRequestType.equals("signup")) {
            if(incomingAttributes.get("firstName").equals("Success")) {
                attr.put("userID", "1");
                returnRequest = new Request("success", attr);
            } else if(incomingAttributes.get("firstName").equals("Duplicate")) {
                returnRequest = new Request( "Error: email exists", attr);
            }

        } else if(incomingRequestType.equals("getuserinfo")) {
            if((int) incomingAttributes.get("userID") == 1) {
                User user = new User(1, "John", "Trojan",
                        "trojan@usc.edu", "12344567890", "password",
                        false, 1.0);
                attr.put("user", user);
                returnRequest = new Request("success", attr);
            } else if((int) incomingAttributes.get("userID") == 2) {
                User user = new User(1, "John", "Trojan",
                        "trojan@usc.edu", "12344567890", "password",
                        false, 1.0);
                attr.put("user", user);
                returnRequest = new Request("success", attr);
            }
        } else if(incomingRequestType.equals("updateinfo")) {
            returnRequest = new Request("success", attr);
        } else if(incomingRequestType.equals("getclasses")) {
            ArrayList<String> classes = new ArrayList<String>();
            classes.add("CSCI 103");
            attr.put("classes", classes);
            returnRequest = new Request("success", attr);
        } else if(incomingRequestType.equals("viewrequests")) {
            if((int) incomingAttributes.get("userID") == 1) {
                ArrayList<TutorRequest> requests = new ArrayList<TutorRequest>();
                // TutorRequest(int requestID, int tuteeID, int tutorID, String time, int status,
                //			String timecreated, String className)
                TutorRequest tr = new TutorRequest(0, 1, 2, "3", 0,
                        "TIME", "CSCI 103");
                tr.setTuteeName("TuteeName1");
                requests.add(tr);
                tr = new TutorRequest(0, 1, 2, "9", 0,
                        "TIME", "CSCI 102");
                tr.setTuteeName("TuteeName2");
                requests.add(tr);
                attr.put("requests", requests);
                returnRequest = new Request("success", attr);
            } else if((int) incomingAttributes.get("userID") == 2) {
                ArrayList<TutorRequest> requests = new ArrayList<TutorRequest>();
                // TutorRequest(int requestID, int tuteeID, int tutorID, String time, int status,
                //			String timecreated, String className)
                TutorRequest tr = new TutorRequest(0, 1, 2, "3", 0,
                        "TIME", "CSCI 103");
                tr.setTuteeName("TuteeName1");
                requests.add(tr);
                attr.put("requests", requests);
                returnRequest = new Request("success", attr);
            } else if((int) incomingAttributes.get("userID") == 3) {
                ArrayList<TutorRequest> requests = new ArrayList<TutorRequest>();
                attr.put("requests", requests);
                returnRequest = new Request("success", attr);
            }
        } else if(incomingRequestType.equals("updaterequeststatus")) {
            returnRequest = new Request("success", attr);
        }
        return null;
    }
}
