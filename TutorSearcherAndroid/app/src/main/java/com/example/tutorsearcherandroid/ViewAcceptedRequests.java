package com.example.tutorsearcherandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;
import tutor.searcher.TutorSearcher.TutorRequest;

public class ViewAcceptedRequests extends AppCompatActivity implements View.OnClickListener {

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

        loadRequests();
    }

    private void loadRequests() {

        List<TutorRequest> requests = null;

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

            requests = (List<TutorRequest>) response.get("requests");

        } catch(Exception e) {
            e.printStackTrace();
        }

/*        TableLayout requests_table_layout = (TableLayout) findViewById(R.id.requests_table_layout);
        //requests_table_layout.removeAllViews();

        int i = 0;
        for(TutorRequest request : requests) {

            int requestId = request.getRequestID();
            String className = request.getClassName();
            String tuteeName = request.getTuteeName();
            String tutorName = request.getTutorName();
            String time = request.getTime();
            int status = request.getStatus();
            String timeCreated = request.getTimeCreated();

            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);

            TableRow request_info_classname_row = new TableRow(this);
            request_info_classname_row.setLayoutParams(lp);
            TextView request_info_classname = new TextView(this);
            request_info_classname.setTypeface(request_info_classname.getTypeface(), Typeface.BOLD);
            request_info_classname.setTextSize(24);
            request_info_classname.setSingleLine(false);
            request_info_classname.setText(className);
            request_info_classname_row.addView(request_info_classname);

            TableRow request_info_tuteeName_row = new TableRow(this);
            request_info_tuteeName_row.setLayoutParams(lp);
            TextView request_info_tuteeName = new TextView(this);
            request_info_tuteeName.setTextSize(24);
            request_info_tuteeName.setTypeface(request_info_tuteeName.getTypeface(), Typeface.BOLD);
            request_info_tuteeName.setText("Tutee: " + tuteeName);
            request_info_tuteeName_row.addView(request_info_tuteeName);

            TableRow request_info_tutorName_row = new TableRow(this);
            request_info_tutorName_row.setLayoutParams(lp);
            TextView request_info_tutorName = new TextView(this);
            request_info_tutorName.setTypeface(request_info_tutorName.getTypeface(), Typeface.BOLD);
            request_info_tutorName.setText("Tutor: " + tutorName);
            request_info_tutorName_row.addView(request_info_tutorName);

            TableRow request_info_time_row = new TableRow(this);
            request_info_time_row.setLayoutParams(lp);
            TextView request_info_time = new TextView(this);
            request_info_time.setTypeface(request_info_time.getTypeface(), Typeface.BOLD);
            request_info_time.setText(time);
            request_info_time_row.addView(request_info_time);

            TableRow request_info_status_row = new TableRow(this);
            request_info_status_row.setLayoutParams(lp);
            TextView request_info_status = new TextView(this);
            request_info_status.setTypeface(request_info_status.getTypeface(), Typeface.BOLD);
            request_info_status.setText("Status: " + status);
            request_info_status_row.addView(request_info_status);

            requests_table_layout.addView(request_info_classname_row, i++);
            requests_table_layout.addView(request_info_tuteeName_row, i++);
            requests_table_layout.addView(request_info_tutorName_row, i++);
            requests_table_layout.addView(request_info_time_row, i++);
            requests_table_layout.addView(request_info_status_row, i++);

        }*/

    }

    public void onClick(View view) {

    }
}
