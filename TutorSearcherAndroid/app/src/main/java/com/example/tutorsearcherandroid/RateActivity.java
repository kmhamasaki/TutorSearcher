package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

public class RateActivity extends AppCompatActivity {

    private String UserId;
    private String AccountType;
    Application app;
    private int TutorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        app = (Application)getApplicationContext();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            TutorId = extras.getInt("TutorId");
            AccountType = extras.getString("AccountType");
        }
    }
}
