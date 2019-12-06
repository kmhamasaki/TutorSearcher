package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;

public class BioActivity extends AppCompatActivity {

    private String sourcePage;
    private String UserId;
    private String AccountType;
    Application app;
    TextView bio;
    TextView bioHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        app = (Application)getApplicationContext();

        bio = (TextView) findViewById(R.id.bio);
        bioHeader = (TextView) findViewById(R.id.bio_header);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sourcePage = extras.getString("SourcePage");
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }
        if(AccountType.equals("Tutor")){
            bioHeader.setText("What would you like Tutees to know about you?");
        }else if(AccountType.equals("Tutee")){
            bioHeader.setText("What would you like Tutors to know about you?");
        }

        if(sourcePage.equals("UpdateProfile")) {
            try {
                HashMap<String, Object> attr = new HashMap<>();
                attr.put("userID", Integer.parseInt(UserId));
                Client client = Client.initClient("getbio", attr, app);
                client.execute().get();
                Request response = client.getResponse();

                bio.setText((String)response.get("bio"));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        String bioText = bio.getText().toString();
        if(bioText.length()>127){
            Toast t = Toast.makeText(this, "Please shorten your bio to 127 characters.",
                    Toast.LENGTH_LONG);
            t.show();
            return;
        }

        if(bioText.length() != 0) {
            HashMap<String, Object> attr = new HashMap<>();
            attr.put("userID", Integer.parseInt(UserId));
            attr.put("bio", bioText);
            Client client = Client.initClient("addbio", attr, app);
            client.execute();
        }

        if(sourcePage.equals("UpdateProfile")) {
            openUpdateProfile();
        } else {
            openHome();
        }


    }

    public void openHome() {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("UserId",UserId);
        i.putExtra("AccountType", AccountType);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void openUpdateProfile() {
        Intent i = new Intent(this, UpdateProfile.class);
        i.putExtra("UserId",UserId);
        i.putExtra("AccountType", AccountType);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
