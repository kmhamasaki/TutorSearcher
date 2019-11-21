package com.example.tutorsearcherandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;

public class SearchResultsActivity extends AppCompatActivity implements MyAdapter.OnTutorClickListener,View.OnClickListener {
    //Recyler View Variables
    private List<Tutor> TutorList;

    private RecyclerView recyclerView;
    private MyAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;

    //User Variables
    private String UserId;
    private String AccountType;
    private String Class;
    Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Application)getApplicationContext();

        setContentView(R.layout.activity_search_results);

        //Save information from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
            Request response = (Request) extras.get("TutorList");
            TutorList = (List<Tutor>) response.get("results");
            Class = extras.getString("ClassName");

        }

        //Generate Recycler Information
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        rLayoutManager = new LinearLayoutManager(this);

        if(TutorList.isEmpty()){
            Toast t = Toast.makeText(this, "No matching tutors. Please go back and edit your search.",
                    Toast.LENGTH_LONG);
            t.show();
        }
        rAdapter = new MyAdapter(TutorList);

        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setAdapter(rAdapter);

        rAdapter.setOnTutorClickListener(this);

//        //Set Onclick Listener for Buttons
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);
    }


    public void onClick(View v){
        //Home Activity
        HashMap<String,Object> attr = new HashMap<>();
        attr.put("userID", Integer.parseInt(UserId));
        Client client = Client.initClient("searchprevious", attr, app);
        client.execute();
        Request response = null;
        while(response == null){
            response = client.getResponse();
        }
        openHomeActivity(AccountType, UserId,response);
    }

    public void openHomeActivity(String accountType, String userId, Request response) {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("AccountType", accountType);
        i.putExtra("UserId", userId);
        i.putExtra("TutorList",response);
        finish();
        startActivity(i);
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

