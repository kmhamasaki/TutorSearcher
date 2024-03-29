package com.example.tutorsearcherandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RadioButton rb;
    private HashMap<String,Integer> timeCode;
    private RadioButton previouslySelected;

    // for push notifs
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAv9Gs5hI:APA91bHkTOdmyZoxFOhqvw8pobPuswXOBF9ef2W-e-wWUsirl5RGKh5zi1aPAnUYsWZiaS8J4NL0qtM-Pmjiq8ke1l5og2ww_d3FdNgZ_qA5UGJR6nyo-diWGcj_zmbHYqo4JYIpHtqY";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    Application app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        app = (Application)getApplicationContext();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_time);

        //Save information from intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
            SelectedTutor = (Tutor) extras.get("Tutor");
            this.MatchedAvailability = SelectedTutor.getMatchingAvailabilities();
        }

        ArrayList<String> times = generateTimesForward();
        timeCode = generateTimesBackward(times);

        rg = new RadioGroup(this);
        rg.setId(R.id.radio_group);

        for(int i = 0; i < MatchedAvailability.size(); i ++){
            RadioButton rb = new RadioButton(this);
            rb.setText(times.get(MatchedAvailability.get(i)));
            rb.setOnClickListener(this);
            rg.addView(rb);
        }

        LinearLayout layout = findViewById(R.id.buttonLayout);
        layout.addView(rg);

        //Set Onclick Listener for Buttons
        Button requestButton = findViewById(R.id.sendRequest);
        requestButton.setOnClickListener(this);
    }

    public void onClick(View v){
        //Id of Currently Selected Radio Button
        Integer selectedId = rg.getCheckedRadioButtonId();

        //Called if no radio buttons were previously selected
        if(previouslySelected == null){
            rb = findViewById(selectedId);
            String time = (String) rb.getText();
            SelectedTime = Integer.toString(timeCode.get(time));
            previouslySelected = rb;
        }
        //Call if change radio button
        else if(selectedId != null && previouslySelected.getId() != selectedId){
            rb = findViewById(selectedId);
            System.out.println(rb.getText());
            String time = (String) rb.getText();
            SelectedTime = Integer.toString(timeCode.get(time));
            System.out.println(timeCode.get(time));
            previouslySelected = rb;
        }
        //Call if Submit Button
        else{
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

        Client client = Client.initClient("newrequest",attr, app);
        client.execute();

        Request response = null;
        while(response == null){
            response = client.getResponse();
        }
        attr = new HashMap<>();
        attr.put("userID", tutteeId);
        client = Client.initClient("searchprevious",attr, app);
        client.execute();
        response = null;
        while(response == null){
            response = client.getResponse();
        }

        // send push notif to tutor
        String tID = Integer.toString(tutorId);
        TOPIC = "/topics/"+tID; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = "Tutor Searcher";
        NOTIFICATION_MESSAGE = "You've got a new request!";

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification);

        openHomeActivity(AccountType,UserId,response);
    }
    public void openHomeActivity(String accountType, String userId, Request response) {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("AccountType", accountType);
        i.putExtra("UserId", userId);
        i.putExtra("TutorList",response);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public static ArrayList<String> generateTimesForward(){
        ArrayList<String> times = new ArrayList<>();
        HashMap<Integer,String> amPm = new HashMap<>();
        amPm.put(9,"am");
        amPm.put(10,"am");
        amPm.put(11,"am");
        amPm.put(12,"pm");
        amPm.put(1,"pm");
        amPm.put(2,"pm");
        amPm.put(3,"pm");
        amPm.put(4,"pm");
        amPm.put(5,"pm");

        for(int i = 0; i < 57; i++){
            String temp = "";
            if(i < 8){ //8 Times/Day 9am earliest start 4pm latest start
                temp += "Monday: ";
            }else if(i < 16){
                temp += "Tuesday: ";
            }else if(i < 24){
                temp += "Wednesday: ";
            }else if(i < 32){
                temp += "Thursday: ";
            }else if(i < 40){
                temp += "Friday: ";
            }else if( i < 48){
                temp += "Saturday: ";
            }else{
                temp += "Sunday: ";
            }
            int startTime = i%8 + 9;
            int endTime = startTime + 1;
            if(startTime > 12){
                startTime -= 12;
                endTime -= 12;
            }else if(endTime > 12){
                endTime -= 12;
            }
            temp += startTime;
            temp += (":00 " + amPm.get(startTime));
            temp += (" - " + endTime + ":00 " + amPm.get(endTime));
            times.add(temp);
        }
        return times;
    }

    public HashMap<String,Integer> generateTimesBackward(ArrayList<String> times){
        HashMap<String,Integer> reverseTimes = new HashMap<>();
        for(int i = 0; i < times.size(); i ++){
            reverseTimes.put(times.get(i),i);
        }
        return reverseTimes;
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TutorTimeActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
