package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import tutor.searcher.TutorSearcher.Request;

import java.util.HashMap;

public class Signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onClick(View view) {
        Client client = new Client();
        HashMap<String, Object> attr = new HashMap<>();

        attr.put("email", findViewById(R.id.email));
        attr.put("passwordHash", findViewById(R.id.password));
        attr.put("firstName", findViewById(R.id.first_name));
        attr.put("lastName", findViewById(R.id.last_name));
        attr.put("phoneNumber", findViewById(R.id.phone));
        attr.put("accountType", true);
        System.out.println("Sending from SignupButtonClick");
        client.attributes = attr;
        client.requestType = "signup";
        client.execute();

        //System.out.println(req.getRequestType());
        // Pass all inputs to backend

        // Error if not all inputs set

        // Error if email already exists

        // Success, tutee, go to home page

        // Success, tutor, go to availability page.
    }
}

