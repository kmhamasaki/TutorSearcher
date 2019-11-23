package com.example.tutorsearcherandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.TutorRequest;

public class ViewRequests extends AppCompatActivity
        implements PendingRequestAdapter.OnButtonClickListener {

    public String UserId;
    private String AccountType;
    Application app;
    private RecyclerView recyclerView;
    private PendingRequestAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;
    private List<TutorRequest> requestList;


    // for push notifs
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAv9Gs5hI:APA91bHkTOdmyZoxFOhqvw8pobPuswXOBF9ef2W-e-wWUsirl5RGKh5zi1aPAnUYsWZiaS8J4NL0qtM-Pmjiq8ke1l5og2ww_d3FdNgZ_qA5UGJR6nyo-diWGcj_zmbHYqo4JYIpHtqY";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (Application)getApplicationContext();

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

            Client client = Client.initClient("viewrequests", attributes, app);
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

        if(!requestList.isEmpty()) {
            findViewById(R.id.noRequestsFound).setVisibility(View.GONE);
        }
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

                Client client = Client.initClient("updaterequeststatus", attributes, app);
                client.execute().get();
                Request response = client.getResponse();

            } catch(Exception e) {
                e.printStackTrace();
            }
            Intent i = new Intent(this, ViewRequestsAccepted.class);
            i.putExtra("UserId", UserId);
            i.putExtra("AccountType", AccountType);
            finish();
            startActivity(i);
        } else {
            try {
                System.out.println("Reject");
                int requestID = requestList.get(position).getRequestID();
                HashMap<String,Object> attributes = new HashMap<String,Object>();
                attributes.put("requestID", requestID);
                attributes.put("newStatus", 2);

                Client client = Client.initClient("updaterequeststatus", attributes, app);
                client.execute().get();
                Request response = client.getResponse();

                // send push notif to tutee
                String tuteeID = Integer.toString(requestList.get(position).getTuteeID());
                TOPIC = "/topics/"+tuteeID; //topic must match with what the receiver subscribed to
                NOTIFICATION_TITLE = "Tutor Searcher";
                NOTIFICATION_MESSAGE = "Your request has been rejected. Seems like you might be getting the D after all.";

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
            } catch(Exception e) {
                e.printStackTrace();
            }
            loadRequests();
        }
    }
    public void onClick(View view) {

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
                        Toast.makeText(ViewRequests.this, "Request error", Toast.LENGTH_LONG).show();
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
