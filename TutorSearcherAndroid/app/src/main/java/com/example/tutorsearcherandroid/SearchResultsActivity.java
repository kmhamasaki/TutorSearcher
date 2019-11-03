package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tutor.searcher.TutorSearcher.Tutor;
import tutor.searcher.TutorSearcher.TutorRequest;

public class SearchResultsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager rLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();

        rLayoutManager = new LinearLayoutManager(this);

        ArrayList<Tutor> myDataset = new ArrayList<>();
        List<TutorRequest> requests = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        classes.add("CSCI 104");
        classes.add("CSCI 103");
        Tutor temp = new Tutor(1,"Cameron","Haseyama","haseyama",
                "8082287860","password",true,
                requests,requests,requests,"1 3 10 15 22 25 28 30 49 50",classes);
        Tutor temp2 = new Tutor(1,"John","Smith","haseyama",
                "8082287860","password",true,
                requests,requests,requests,"2 4 9 23 22 49 50",classes);
        Tutor temp3 = new Tutor(1,"Jane","Doe","haseyama",
                "8082287860","password",true,
                requests,requests,requests,"49 40 41 52",classes);
        for(int i = 0; i < 8; i++) {
            myDataset.add(temp);
            myDataset.add(temp2);
            myDataset.add(temp3);
        }
        rAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(rAdapter);

        recyclerView.setLayoutManager(rLayoutManager);
    }

    public void openHomeActivity(String accountType) {
        Intent i = new Intent(this, HomeActivity.class);
//        i.putExtra("AccountType", accountType);
        startActivity(i);
        finish();
    }


}

