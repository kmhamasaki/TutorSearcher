package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;

public class TutorTimeActivity extends AppCompatActivity implements View.OnClickListener{
    //Recyler View Variables
    private List<Integer> MatchedAvailability;

    //User Variables
    private String UserId;
    private String AccountType;
    private Tutor SelectedTutor;
    private String SelectedTime;
    private RadioGroup rg;
    private HashMap<String,Integer> timeCode;

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
            MatchedAvailability = SelectedTutor.getMatchingAvailabilities();
        }

        ArrayList<String> times = new ArrayList<>();
        HashMap<String,Integer> reverseTimes = new HashMap<>();
        for(int i = 0; i < 57; i++){
            String temp = "";
            if(i < 9){
                temp += "Monday: ";
                temp += Integer.toString(i%9 + 8);
                temp += ":00";
                times.add(temp);
                reverseTimes.put(temp,i);
            }else if(i < 18){
                temp += "Tuesday: ";
                temp += Integer.toString(i%9 + 8);
                temp += ":00";
                times.add(temp);
                reverseTimes.put(temp,i);
            }else if(i < 27){
                temp += "Wednesday: ";
                temp += Integer.toString(i%9 + 8);
                temp += ":00";
                times.add(temp);
                reverseTimes.put(temp,i);
            }else if(i < 36){
                temp += "Thursday: ";
                temp += Integer.toString(i%9 + 8);
                temp += ":00";
                times.add(temp);
                reverseTimes.put(temp,i);
            }else if(i < 45){
                temp += "Friday: ";
                temp += Integer.toString(i%9 + 8);
                temp += ":00";
                times.add(temp);
                reverseTimes.put(temp,i);
            }else if( i < 54){
                temp += "Saturday: ";
                temp += Integer.toString(i%9 + 8);
                temp += ":00";
                times.add(temp);
                reverseTimes.put(temp,i);
            }else{
                temp += "Sunday: ";
                temp += Integer.toString(i%9 + 8);
                temp += ":00";
                times.add(temp);
                reverseTimes.put(temp,i);
            }
        }
        timeCode = reverseTimes;

        rg = new RadioGroup(this);
        for(int i = 0; i < MatchedAvailability.size(); i ++){
            RadioButton rb = new RadioButton(this);
            rb.setText(times.get(MatchedAvailability.get(i)));
            rg.addView(rb);
        }

        LinearLayout layout = findViewById(R.id.buttonLayout);
        layout.addView(rg);

        //Set Onclick Listener for Buttons
        Button requestButton = findViewById(R.id.sendRequest);
        requestButton.setOnClickListener(this);
    }

    public void onClick(View v){
        System.out.println("Button Click");
        if(SelectedTime != null){
            sendRequest();
            System.out.println("Send Click");
        }
    }
    public void sendRequest(){
        //Create Client
        Bundle extras = getIntent().getExtras();
        int tutteeId = Integer.parseInt(UserId);
        int tutorId = SelectedTutor.getUserId();
        String className = extras.getString("ClassName");
        String time = SelectedTime;

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
