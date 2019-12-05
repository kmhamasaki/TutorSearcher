package com.example.tutorsearcherandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.User;

public class UpdateProfile extends AppCompatActivity {

    Client client;

    Application app;
    private String UserId;
    private String AccountType;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = (Application)getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Button updateClassButton = findViewById(R.id.update_classses_button);
        Button updateAvailabilityButton = findViewById(R.id.accepted_requests_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        if(AccountType.equals("Tutee")) {
            updateClassButton.setVisibility(View.GONE);
            updateAvailabilityButton.setVisibility(View.GONE);
        }

        try {
            HashMap<String, Object> attr = new HashMap<>();
            attr.put("userID", Integer.parseInt(UserId));
            client = Client.initClient("getuserinfo", attr, app);

            // Pass all inputs to backend
            client.execute().get();
            Request response = client.getResponse();
            user = (User) response.get("user");

            EditText firstName = findViewById(R.id.firstName);
            EditText lastName = findViewById(R.id.lastName);
            EditText phoneNumber = findViewById(R.id.phoneNumber);

            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            phoneNumber.setText(user.getPhoneNumber());
            //bio.setText(user.getBio());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void onUpdateClick(View view) {
        String fname = ((EditText)findViewById(R.id.firstName)).getText().toString();
        String lname = ((EditText)findViewById(R.id.lastName)).getText().toString();
        String phonenumber = ((EditText)findViewById(R.id.phoneNumber)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();

        //FirstName Empty
        if(fname.equals("")){
            fname = user.getFirstName();
        }
        user.setFirstName(fname);

        //LastName Empty
        if(lname.equals("")){
            lname = user.getLastName();
        }
        user.setLastName(lname);

        //PhoneNumber Empty
        if(phonenumber.equals("")){
            phonenumber = user.getPhoneNumber();
        }
        user.setPhoneNumber(phonenumber);

        //Password Empty
        if(password.equals("")) {
            password = user.getPasswordHash();
        } else {
            password = SignupActivity.hashPassword(password);
        }
        user.setPasswordHash(password);


        System.out.println(user.getFirstName());
        try {
            HashMap<String, Object> attr = new HashMap<>();
            attr.put("user", user);
            client = Client.initClient("updateinfo", attr, app);

            // Pass all inputs to backend
            client.execute().get();
        } catch(Exception e) {
            e.printStackTrace();
        }
        // CLient stuff here to connect to db
        Toast t = Toast.makeText(this, "Successfully updated profile information!",
                Toast.LENGTH_LONG);
        t.show();
    }

    public void onUpdateAvailabilityClick(View view) {
        Intent i = new Intent(this, TabbedAvailabilityActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("SourcePage", "UpdateProfile");
        startActivity(i);
    }

    public void onUpdateClassesClick(View view) {
        Intent i = new Intent(this, ChooseClasses.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("SourcePage", "UpdateProfile");
        startActivity(i);
    }

    public void onUpdateBioClick(View view) {
        Intent i = new Intent(this, BioActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("SourcePage", "UpdateProfile");
        startActivity(i);
    }

    public void backToHomeClick(View view) {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        if(AccountType.equals("Tutee")){
            //Get most recent search
            HashMap<String,Object> attr = new HashMap<>();
            attr.put("userID",Integer.parseInt(UserId));
            client = Client.initClient("searchprevious",attr, app);
            client.execute();
            Request response = null;
            while(response == null){
                response = client.getResponse();
            }
            i.putExtra("TutorList",response);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}