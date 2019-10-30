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
        Intent i = new Intent(this, Signup.class);
        startActivity(i);
        finish();
    }
}
