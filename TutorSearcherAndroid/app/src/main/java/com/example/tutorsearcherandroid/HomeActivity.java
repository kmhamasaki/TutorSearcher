package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, MyAdapter.OnTutorClickListener{

    private String UserId;
    private String AccountType;
    private String Class;

    //Recyler View Variables
    private List<Tutor> TutorList;
    private RecyclerView recyclerView;
    private MyAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;

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

        //Recycler Initiation
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
            if(AccountType.equals("Tutee")){
                Request response = (Request) extras.get("TutorList");
                TutorList = (List<Tutor>) response.get("results");
                Class = extras.getString("ClassName");
            }
        }

        //Generate Recycler Information
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new MyAdapter(TutorList);

        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setAdapter(rAdapter);

        rAdapter.setOnTutorClickListener(this);


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

    }

    @Override
    public void onTutorClick(int position) {
        Intent i = new Intent(this, TutorTimeActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        Tutor tutor = TutorList.get(position);
        i.putExtra("Tutor", tutor);
        System.out.println(tutor.getMatchingAvailabilities().get(0));
        i.putExtra("ClassName",Class);
        startActivity(i);
    }

}
