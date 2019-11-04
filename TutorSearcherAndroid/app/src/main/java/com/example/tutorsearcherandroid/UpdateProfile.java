package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UpdateProfile extends AppCompatActivity {

    private String UserId;
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

    public void onUpdateClick(View view) {
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

    public void backToHomeClick(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        startActivity(i);
    }


}