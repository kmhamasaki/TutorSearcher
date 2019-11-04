package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;
import tutor.searcher.TutorSearcher.TutorRequest;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;

    private String UserId;
    private String AccountType;
    private List<Tutor> TutorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();

        rLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(rLayoutManager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
            Request response = (Request) extras.get("TutorList");
            TutorList = (List<Tutor>) response.get("results");
        }
        if(TutorList != null){
            rAdapter = new MyAdapter(TutorList);
            recyclerView.setAdapter(rAdapter);
        }
    }

    public void openHomeActivity(String accountType) {
        Intent i = new Intent(this, HomeActivity.class);
//        i.putExtra("AccountType", accountType);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
        finish();
    }


}

