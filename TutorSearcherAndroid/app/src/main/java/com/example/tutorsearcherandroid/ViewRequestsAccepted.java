package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView recyclerView;
    private AcceptedRequestAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;
    private List<AcceptedTutorRequest> requestList = new ArrayList<AcceptedTutorRequest>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_accepted_requests);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        System.out.println("ACCOUNT TYPE = " + AccountType);

        loadRequests();
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

            Client client = new Client("viewrequests", attributes);
            client.execute().get();
            Request response = client.getResponse();

            List<TutorRequest> tutorRequests = (List<TutorRequest>) response.get("requests");

            int userID;
            User user;
            HashMap<String,Object> attr = new HashMap<String,Object>();
            for(TutorRequest req : tutorRequests) {
                if(AccountType.equals("Tutor")) {
                    userID = req.getTuteeID();
                } else {
                    userID = req.getTutorID();
                }
                attr.put("userID", userID);
                client = new Client("getuserinfo", attributes);
                client.execute().get();
                response = client.getResponse();
                user = (User)response.get("user");
                requestList.add(new AcceptedTutorRequest(user.getEmail(), user.getPhoneNumber(),
                        user.getFirstName(), req.getClassName() + " " +
                        TutorTimeActivity.generateTimesForward().get(Integer.parseInt(req.getTime())), 0));
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
        startActivity(i);
    }

    @Override
    public void onButtonClick(int position, Boolean accept) {
        System.out.println("ViewRequestsAccepted.onButtonClick");
        // James intent here
    }
    public void onClick(View view) {

    }
}
