package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseClasses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_classes);
    }

    public void onClick(View view) {
        openAvailabilityActivity();
    }

    public void openAvailabilityActivity() {
        Intent i = new Intent(this, TabbedAvailabilityActivity.class);
        i.putExtra("SourcePage","Signup");
        // this destroys all activities before this page
        /* we are doing this because once the tutor has filled in the sign up page, he has technically
        already signed up and his account details are in our database. so he should not be able to press
        the back button
         */
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
