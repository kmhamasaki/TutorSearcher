package com.example.tutorsearcherandroid;

import java.util.HashMap;
import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.User;

import javax.inject.Inject;
import javax.inject.Named;


public class ClientTest extends Client {
    @Inject
   // @Named("Client")
    public void ClientTest() {}

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
            if(incomingAttributes.get("UserId").equals("1") {
                User user()
            }
        }
        return null;
    }
}
