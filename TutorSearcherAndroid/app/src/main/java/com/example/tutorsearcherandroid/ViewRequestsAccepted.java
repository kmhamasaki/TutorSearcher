package com.example.tutorsearcherandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.AcceptedTutorRequest;
import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.TutorRequest;
import tutor.searcher.TutorSearcher.User;


public class ViewRequestsAccepted extends AppCompatActivity
        implements AcceptedRequestAdapter.OnButtonClickListener {

    private String UserId;
    private String AccountType;
    Application app;

    private RecyclerView recyclerView;
    private AcceptedRequestAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;
    private List<TutorRequest> requestList = new ArrayList<TutorRequest>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Application)getApplicationContext();
        setContentView(R.layout.view_accepted_requests);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        System.out.println("ACCOUNT TYPE = " + AccountType);

        loadRequests();
        if(!requestList.isEmpty()) {
            findViewById(R.id.noRequestsFound).setVisibility(View.GONE);
        }
    }

    private void loadRequests() {

        try {
            HashMap<String,Object> attributes = new HashMap<String,Object>();
            attributes.put("userID", Integer.parseInt(UserId));
            if(AccountType.equals("Tutor")) {
                attributes.put("viewrequeststype", "tutorapproved");
            } else if(AccountType.equals("Tutee")) {
                attributes.put("viewrequeststype", "tuteeapproved");
            }

            Client client = Client.initClient("viewrequests", attributes, app);
            client.execute().get();
            Request response = client.getResponse();

            requestList = (List<TutorRequest>) response.get("requests");

            int userID;
            User user;
            HashMap<String,Object> attr = new HashMap<String,Object>();
            for(TutorRequest req : requestList) {
                if(AccountType.equals("Tutor")) {
                    userID = req.getTuteeID();
                } else {
                    userID = req.getTutorID();
                }
                attr.put("userID", userID);
                client = Client.initClient("getuserinfo", attr, app);
                client.execute().get();
                response = client.getResponse();
                user = (User)response.get("user");
                req.setEmail(user.getEmail());
                req.setPhoneNumber(user.getPhoneNumber());
                req.setName(user.getFirstName());
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new AcceptedRequestAdapter(requestList, AccountType);

        recyclerView.setLayoutManager(rLayoutManager);
        recyclerView.setAdapter(rAdapter);

        rAdapter.setOnButtonClickListener(this);
    }

    public void onHomeClick(View view) {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(AccountType.equals("Tutee")){
            //Get most recent search
            HashMap<String,Object> attr = new HashMap<>();
            attr.put("userID",Integer.parseInt(UserId));
            Client client = Client.initClient("searchprevious",attr, app);
            client.execute();
            Request response = null;
            while(response == null){
                response = client.getResponse();
            }
            i.putExtra("TutorList",response);
        }
        startActivity(i);
    }

    @Override
    public void onButtonClick(int position, Boolean accept) {
        System.out.println("ViewRequestsAccepted.onButtonClick");
        int RequestId = requestList.get(position).getRequestID();
        Intent i = new Intent(this, RatingActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        i.putExtra("RequestId", RequestId);
        if(AccountType.equals("Tutor")) {
            i.putExtra("GivenRating", requestList.get(position).getGivenTuteeRating());
            i.putExtra("Name", requestList.get(position).getName());
        } else {
            i.putExtra("GivenRating", requestList.get(position).getGivenTutorRating());
            i.putExtra("Name", requestList.get(position).getName());
        }

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }


    public void onClick(View view) {

    }
}
