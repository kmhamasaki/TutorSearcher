package com.example.tutorsearcherandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.TutorRequest;

public class ViewRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_requests);

        // Loop through return  viewrequests()
        HashMap<String,Object> attributes = new HashMap<String,Object>();
        Client viewrequests = new Client("viewrequests", attributes);

//        List<TutorRequest> requests = viewrequests.incomingAttributes.requests

        TableLayout requests_table_layout = (TableLayout) findViewById(R.id.requests_table_layout);

        for (int i = 0; i <2; i++) {

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

