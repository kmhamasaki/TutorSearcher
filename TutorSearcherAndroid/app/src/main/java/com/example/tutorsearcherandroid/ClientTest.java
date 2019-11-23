package com.example.tutorsearcherandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.TutorRequest;
import tutor.searcher.TutorSearcher.Tutee;
import tutor.searcher.TutorSearcher.Tutor;
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
        } else if(incomingRequestType.equals("login")) {
            System.out.println("In LOGIN TEST");
            if(incomingAttributes.get("email").equals("invalidEmail@usc.edu")){
                returnRequest = new Request("Error: wrong email or password", attr);
            }else if(incomingAttributes.get("email").equals("successTutor@usc.edu")){
                User user = new Tutor(1, "John", "Trojan",
                        "successTutor@usc.edu", "12344567890", "password",
                        true,"1 2 3 4", 1.0);
                attr.put("User",user);
                returnRequest = new Request("success",attr);
            }else{
                User user = new Tutee(1, "John", "Trojan",
                        "successTutee@usc.edu", "12344567890", false, 1.0);
                attr.put("User",user);
                returnRequest = new Request("success",attr);
            }
        } else if(incomingRequestType.equals("searchprevious") || incomingRequestType.equals("search")){
            List<Tutor> results = new ArrayList<>();
            Tutor t1 = new Tutor(1, "John", "Trojan",
                    "successTutor@usc.edu", "12344567890", "password",
                    true,"0 1 2 3 4", 1.0);

            t1.setMatchingAvailabilities((ArrayList<Integer>) t1.getTimeAvailabilities());
            results.add(t1);
            Tutor t2 = new Tutor(1, "Jane", "Doe",
                    "successTutor@usc.edu", "12344567890", "password",
                    true,"0 1 2 3 4", 1.0);
            t2.setMatchingAvailabilities((ArrayList<Integer>) t2.getTimeAvailabilities());
            results.add(t2);
            attr.put("results",results);
            returnRequest = new Request("results",attr);
        }
        return null;
    }
}
