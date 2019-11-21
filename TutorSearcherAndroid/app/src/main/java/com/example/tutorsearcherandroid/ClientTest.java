package com.example.tutorsearcherandroid;

import java.util.ArrayList;
import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;
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
        }
        return null;
    }
}
