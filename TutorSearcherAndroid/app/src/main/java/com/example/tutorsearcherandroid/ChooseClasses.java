package com.example.tutorsearcherandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tutor.searcher.TutorSearcher.Request;

public class ChooseClasses extends AppCompatActivity {

    private static final int[] CHECKBOX_ID = new int[]{R.id.CSCI102,R.id.CSCI103,R.id.CSCI104,
            R.id.CSCI109,R.id.CSCI170,R.id.CSCI201,R.id.CSCI270,R.id.CSCI310, R.id.CSCI356,
            R.id.CSCI360,R.id.CSCI401};
    private static final Map<String, Integer> classToIndex = new HashMap<String, Integer>()
    {
        {
            int num = 0;
            put("CSCI 102", num++);
            put("CSCI 103", num++);
            put("CSCI 104", num++);
            put("CSCI 109", num++);
            put("CSCI 170", num++);
            put("CSCI 201", num++);
            put("CSCI 270", num++);
            put("CSCI 310", num++);
            put("CSCI 356", num++);
            put("CSCI 360", num++);
            put("CSCI 401", num);
        };
    };
    private ArrayList<String> selectedClasses = new ArrayList<>();
    private String sourcePage;
    private String UserId;
    private String AccountType;

    Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_classes);
        app = (Application)getApplicationContext();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sourcePage = extras.getString("SourcePage");
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        // If coming from home, pre-click already chosen classes
        if(sourcePage.equals("UpdateProfile")) {
            Button b = (Button) findViewById(R.id.continueButton);
            b.setText("Update");
            try {
                HashMap<String, Object> attr = new HashMap<>();
                attr.put("tutorID", Integer.parseInt(UserId));
                Client client = Client.initClient("getclasses", attr, app);
                client.execute().get();
                Request response = client.getResponse();

                ArrayList<String> classes = (ArrayList<String>)response.getAttributes().get("classes");
                System.out.println("SIZE CLASSES" + classes.size());
                for(int i = 0; i < classes.size(); i++) {
                    CheckBox cb = findViewById(CHECKBOX_ID[classToIndex.get(classes.get(i))]);
                    cb.setChecked(true);
                    System.out.println(classes.get(i));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        // Check which classes was clicked
        for(int i=0;i<CHECKBOX_ID.length;i++) {
            CheckBox cb = findViewById(CHECKBOX_ID[i]);
            boolean checked = cb.isChecked();
            if(cb.isChecked()) {
                selectedClasses.add(cb.getText().toString());
            }
        }

        if(selectedClasses.isEmpty()){
            Toast t = Toast.makeText(this, "Please select at least one class to tutor.",
                    Toast.LENGTH_LONG);
            t.show();
        }else {
            try {
                HashMap<String, Object> attr = new HashMap<>();
                attr.put("className", selectedClasses);
                attr.put("tutorID", Integer.parseInt(UserId));
                Client client = Client.initClient("addclass", attr, app);
                client.execute().get();
                Request response = client.getResponse();
                System.out.println(response.getRequestType());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (sourcePage.equals("Signup"))
                openAvailabilityActivity();

            else
                openUpdateProfile();
        }
    }

    public void openAvailabilityActivity() {
        Intent i = new Intent(this, TabbedAvailabilityActivity.class);
        i.putExtra("SourcePage","Signup");
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        // this destroys all activities before this page
        /* we are doing this because once the tutor has filled in the sign up page, he has technically
        already signed up and his account details are in our database. so he should not be able to press
        the back button
         */
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    public void openUpdateProfile() {
        Intent i = new Intent(this, UpdateProfile.class);
        i.putExtra("SourcePage", "Home");
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}
