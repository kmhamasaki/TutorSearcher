package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.TutorRequest;

public class ViewRequests extends AppCompatActivity
        implements PendingRequestAdapter.OnButtonClickListener {

    private String UserId;
    private String AccountType;

    private RecyclerView recyclerView;
    private PendingRequestAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;
    private List<TutorRequest> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_requests);

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
            attributes.put("viewrequeststype", "tutorpending");

            Client client = new Client("viewrequests", attributes);
            client.execute().get();
            Request response = client.getResponse();

            requestList = (List<TutorRequest>) response.get("requests");

        } catch(Exception e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.hasFixedSize();
        rLayoutManager = new LinearLayoutManager(this);
        rAdapter = new PendingRequestAdapter(requestList);

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
        System.out.println("onButtonClick");
        if(accept) {
            try {
                System.out.println("Accept");
                int requestID = requestList.get(position).getRequestID();
                HashMap<String,Object> attributes = new HashMap<String,Object>();
                attributes.put("requestID", requestID);
                attributes.put("newStatus", 1);

                Client client = new Client("updaterequeststatus", attributes);
                client.execute().get();
                Request response = client.getResponse();

            } catch(Exception e) {
                e.printStackTrace();
            }
            Intent i = new Intent(this, ViewRequestsAccepted.class);
            i.putExtra("UserId", UserId);
            i.putExtra("AccountType", AccountType);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else {
            try {
                System.out.println("Reject");
                int requestID = requestList.get(position).getRequestID();
                HashMap<String,Object> attributes = new HashMap<String,Object>();
                attributes.put("requestID", requestID);
                attributes.put("newStatus", 2);

                Client client = new Client("updaterequeststatus", attributes);
                client.execute().get();
                Request response = client.getResponse();

            } catch(Exception e) {
                e.printStackTrace();
            }
            loadRequests();
        }
    }
    public void onClick(View view) {

    }
}
