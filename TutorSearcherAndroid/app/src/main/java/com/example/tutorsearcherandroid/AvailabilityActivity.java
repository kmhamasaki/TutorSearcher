package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AvailabilityActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        Bundle extras = getIntent().getExtras();
        // find out if user is coming into this page from search page (tutee) or profile page (tutor)
        String sourcePage = extras.getString("SourcePage");
        if(sourcePage.equals("SearchTutor")){
            setTitle(R.string.title_activity_search_tutor);
        }
        else if(sourcePage.equals("EditProfile")){
            setTitle("replace later");
        }


        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // handle when user clicks "sign in" at the start
            case R.id.submitButton:
                openNextActivity();
        }
    }

    public void openNextActivity(){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
        finish();
    }
}
