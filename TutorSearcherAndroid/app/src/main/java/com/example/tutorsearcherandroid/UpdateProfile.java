package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UpdateProfile extends AppCompatActivity {

    private String UserId;
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
    }
}
