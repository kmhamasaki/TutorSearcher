package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import tutor.searcher.TutorSearcher.Request;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onClick(View view) {
        HashMap<String, Object> attr = new HashMap<>();

        attr.put("email", ((android.widget.TextView)findViewById(R.id.email)).getText().toString());
        attr.put("passwordHash",  ((android.widget.TextView)findViewById(R.id.password)).getText().toString());
        attr.put("firstName",  ((android.widget.TextView)findViewById(R.id.first_name)).getText().toString());
        attr.put("lastName",  ((android.widget.TextView)findViewById(R.id.last_name)).getText().toString());
        attr.put("phoneNumber",  ((android.widget.TextView)findViewById(R.id.phone)).getText().toString());
        android.widget.RadioGroup rg = findViewById(R.id.tutorTuteeRadioSelector);

        //System.out.println(((android.widget.RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString());

        attr.put("accountType", true);
        System.out.println("Sending from SignupButtonClick");
        Client client = new Client("signup", attr);

        // Pass all inputs to backend
        client.execute();

        Request response = client.getResponse();

        System.out.println(response.getRequestType());

        // Error if not all inputs set

        // Error if email already exists
        if(response.getRequestType().equals("Error: email exists")) {
            System.out.println("James put error message her");
        }

        // Error not usc email
        else if(response.getRequestType().equals("Error: not USC email")) {
            System.out.println("James put error message her");
        }

        // Success, tutee, go to home page
        //else if()
        // Success, tutor, go to availability page.
    }
}

