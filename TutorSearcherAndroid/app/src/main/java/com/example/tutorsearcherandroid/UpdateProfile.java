package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UpdateProfile extends AppCompatActivity {

    private String UserId;
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Button updateClassButton = findViewById(R.id.update_classses_button);
        Button updateAvailabilityButton = findViewById(R.id.update_availability_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        if(AccountType.equals("Tutee")) {
            updateClassButton.setVisibility(View.GONE);
            updateAvailabilityButton.setVisibility(View.GONE);
        }
    }

    public void onUpdateClick(View view) {
        // needs to be filled in
    }

    public void onUpdateAvailabilityClick(View view) {
        System.out.println("Home.openAvailabilityActivity");
        Intent i = new Intent(this, TabbedAvailabilityActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("SourcePage", "EditProfile");
        startActivity(i);
    }

    public void onUpdateClassesClick(View view) {
        System.out.println("Home.openChooseClassesActivity");
        Intent i = new Intent(this, ChooseClasses.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("SourcePage", "Home");
        startActivity(i);
    }
}