package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.view.View;
import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;


public class RatingActivity extends AppCompatActivity {

    private String UserId;
    private String AccountType;
    private String Name;

    private int GivenRating;
    Application app;
    private int RequestId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        app = (Application)getApplicationContext();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            RequestId = extras.getInt("RequestId");
            AccountType = extras.getString("AccountType");
            GivenRating = extras.getInt("GivenRating");
            Name = extras.getString("Name");
        }

        TextView message = findViewById(R.id.rate_message);
        message.setText("Rate " + Name);

        RatingBar rating_bar = findViewById(R.id.ratingBar);
        rating_bar.setStepSize((float)1.0);
        if(GivenRating != -1) {
            rating_bar.setRating((float)GivenRating);
        }
    }

    public void onClick(View view) {
        RatingBar rating_bar = findViewById(R.id.ratingBar);
        int rating = (int)rating_bar.getRating();
        if(rating > 0) {
            try {
                HashMap<String,Object> attr = new HashMap<String,Object>();
                attr.put("requestID", RequestId);
                attr.put("rating", rating);
                Client client;
                if(AccountType.equals("Tutee")) {
                    client = Client.initClient("updatetutorrating", attr, app);
                } else {
                    client = Client.initClient("updatetuteerating", attr, app);
                }
                client.execute();
                Toast t = Toast.makeText(this, "Your rating has been saved.",
                        Toast.LENGTH_LONG);
                t.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast t = Toast.makeText(this, "Please select your rating.",
                    Toast.LENGTH_LONG);
            t.show();
        }
    }

    public void onHomeButtonClick(View view) {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(AccountType.equals("Tutee")){
            //Get most recent search
            HashMap<String,Object> attr = new HashMap<>();
            attr.put("userID",Integer.parseInt(UserId));
            Client client = Client.initClient("searchprevious",attr, app);
            client.execute();
            Request response = null;
            while(response == null){
                response = client.getResponse();
            }
            i.putExtra("TutorList",response);
        }
        startActivity(i);
    }
}
