package com.example.tutorsearcherandroid;

import java.util.HashMap;
import tutor.searcher.TutorSearcher.Request;


public class ClientTest extends Client {
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

        }
        return null;
    }
}
