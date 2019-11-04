package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;

public class TutorTimeActivity extends AppCompatActivity implements View.OnClickListener{
    //Recyler View Variables
    private List<Integer> MatchedAvailability;

    private RecyclerView recyclerView;
    private TimeAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;

    //User Variables
    private String UserId;
    private String AccountType;
    private Tutor SelectedTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_time);

        //Save information from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
            SelectedTutor = (Tutor) extras.get("Tutor");
            System.out.println(SelectedTutor.getLastName());
            MatchedAvailability = SelectedTutor.getTimeAvailabilities();
        }


        //Generate Recycler Information
        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.hasFixedSize();
        rLayoutManager = new LinearLayoutManager(this);
        ArrayList<String> times = new ArrayList<String>();
        for(int i = 0; i < 57; i++){
            String temp = "";
            if(i < 9){
                temp += "Monday: ";
                temp += Integer.toString(i%9);
                temp += ":00";
                times.add(temp);
            }else if(i < 18){
                temp += "Tuesday: ";
                temp += Integer.toString(i%9);
                temp += ":00";
                times.add(temp);
            }else if(i < 27){
                temp += "Wednesday: ";
                temp += Integer.toString(i%9);
                temp += ":00";
                times.add(temp);
            }else if(i < 36){
                temp += "Thursday: ";
                temp += Integer.toString(i%9);
                temp += ":00";
                times.add(temp);
            }else if(i < 45){
                temp += "Friday: ";
                temp += Integer.toString(i%9);
                temp += ":00";
                times.add(temp);
            }else if( i < 54){
                temp += "Saturday: ";
                temp += Integer.toString(i%9);
                temp += ":00";
                times.add(temp);
            }else{
                temp += "Sunday: ";
                temp += Integer.toString(i%9);
                temp += ":00";
                times.add(temp);
            }
        }
        rAdapter = new TimeAdapter(MatchedAvailability, times);

        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setAdapter(rAdapter);

        //Set Onclick Listener for Buttons
        Button requestButton = findViewById(R.id.sendRequest);
        requestButton.setOnClickListener(this);
    }

    public void onClick(View v){
        sendRequest();
    }
    public void sendRequest(){
        //Create Client
        Bundle extras = getIntent().getExtras();
        int tutteeId = Integer.parseInt(UserId);
        int tutorId = SelectedTutor.getUserId();
        String className = extras.getString("ClassName");
        String time = "1";

        HashMap<String,Object> attr = new HashMap<>();
        attr.put("tuteeID", tutteeId);
        attr.put("tutorID", tutorId);
        attr.put("className", className);
        attr.put("time", time);

        Client client = new Client("newrequest",attr);
        client.execute();

//        openHomeActivity();
    }


}
