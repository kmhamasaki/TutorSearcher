package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.os.Bundle;

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
        Button acceptedRequestsButton = findViewById(R.id.accepted_requests_button);

        viewRequestButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        updateProfileButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
        acceptedRequestsButton.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        System.out.println(AccountType);
        if(AccountType.equals("Tutor")) {
            searchButton.setVisibility(View.GONE);
        } else {
            viewRequestButton.setVisibility(View.GONE);
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
            case R.id.accepted_requests_button:
                openUpdateProfileActivity();
                break;
            case R.id.logout_button:
                openMainActivity();
                break;

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
        Intent i = new Intent(this, UpdateProfile.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }

    public void openMainActivity() {
        //logs user out and redirects to login page
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }

    public void openAcceptedRequestActivity() {
        // need to link to acceptedrequest
    }

}
