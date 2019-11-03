package com.example.tutorsearcherandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.TutorRequest;

public class ViewRequests extends AppCompatActivity implements View.OnClickListener {

    private String UserId;
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_requests);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

//      Loop through return  viewrequests()

        HashMap<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("UserId", UserId);

        try {
            Client client = new Client("viewrequests", attributes);

            client.execute().get();
            Request response = client.getResponse();
            System.out.println(response.getRequestType());
        } catch(Exception e) {
            e.printStackTrace();
        }






//      List<TutorRequest> requests = viewrequests.;

      TableLayout requests_table_layout = (TableLayout) findViewById(R.id.requests_table_layout);

        for (int i = 0; i < 200; i++) {

            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView tv = new TextView(this);
            tv.setText("Testing");
            row.addView(tv);
            requests_table_layout.addView(row, i);
        }


//
//
//
//        for(TutorRequest tutorRequest : requests) {
//            TableRow request_row = (TableRow) findViewById(R.id.request_row);
//
//            // If User == Tutor
//                // Add button to approve / reject
//            // If User == Tutee
//                // Don't add button
//
//            request_row.generateLayoutParams();
//
//            requests_table_layout.addView(request_row);
//
//
//        }

    }

    public void onClick(View view) {

    }
}