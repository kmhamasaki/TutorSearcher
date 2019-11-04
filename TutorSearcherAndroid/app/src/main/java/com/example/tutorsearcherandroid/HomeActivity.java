package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private String UserId;
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button viewRequestButton = findViewById(R.id.view_requests_button);
        Button searchButton = findViewById(R.id.search_button);
        Button updateProfileButton = findViewById(R.id.update_profile_button);
        Button logoutButton = findViewById(R.id.logout_button);
        Button updateClassButton = findViewById(R.id.update_classses_button);
        Button updateAvailabilityButton = findViewById(R.id.update_availability_button);

        viewRequestButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        updateProfileButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        updateClassButton.setOnClickListener(this);
        updateAvailabilityButton.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        System.out.println(AccountType);
        if(AccountType.equals("Tutor")) {
            searchButton.setVisibility(View.GONE);
        } else {
            updateClassButton.setVisibility(View.GONE);
            updateAvailabilityButton.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        System.out.println("Home.onCLick");
        switch (v.getId()) {
            case R.id.view_requests_button:
                openViewRequestActivity();
                break;
            case R.id.search_button:
                openSearchActivity();
                break;
            case R.id.update_profile_button:
                openUpdateProfileActivity();
                break;
            case R.id.logout_button:
                openMainActivity();
                break;
            case R.id.update_availability_button:
                openAvailabilityActivity();
            case R.id.update_classses_button:
                openChooseClassesActivity();

        }
    }
    public void openViewRequestActivity() {
        //redirects to search screens
        Intent i = new Intent(this, ViewRequests.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }

    public void openSearchActivity() {
        //redirects to search screens
        Intent i = new Intent(this, SearchTutor.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }

    public void openUpdateProfileActivity() {
        //redirects to requests screen
    }

    public void openMainActivity() {
        //logs user out and redirects to login page
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }

    public void openAvailabilityActivity() {
        System.out.println("Home.openAvailabilityActivity");
        Intent i = new Intent(this, TabbedAvailabilityActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("SourcePage", "EditProfile");
        startActivity(i);
    }

    public void openChooseClassesActivity() {
        System.out.println("Home.openChooseClassesActivity");
        Intent i = new Intent(this, ChooseClasses.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("SourcePage", "Home");
        startActivity(i);
    }
}
