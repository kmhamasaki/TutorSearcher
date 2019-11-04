package com.example.tutorsearcherandroid;

import android.content.Context;
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

        List<TutorRequest> requests = null;

        try {
            HashMap<String,Object> attributes = new HashMap<String,Object>();
            attributes.put("userID", Integer.parseInt(UserId));
            attributes.put("viewrequeststype", "tutorpending");

            Client client = new Client("viewrequests", attributes);
            client.execute().get();
            Request response = client.getResponse();

            requests = (List<TutorRequest>) response.get("requests");

        } catch(Exception e) {
            e.printStackTrace();
        }

        TableLayout requests_table_layout = (TableLayout) findViewById(R.id.requests_table_layout);

        int i = 0;
        for(TutorRequest request : requests) {

            int requestId = request.getRequestID();
            String className = request.getClassName();
            String tuteeName = request.getTuteeName();
            String tutorName = request.getTutorName();
            String time = request.getTime();
            int status = request.getStatus();
            Date timeCreated = request.getTimeCreated();

            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView tv = new TextView(this);
            tv.setText(className + "\nTutee: " + tuteeName + "\nTutor: " + tutorName + "\nTime: " + time + "\nStatus:" + status);
            row.addView(tv);

            TableRow row2 = new TableRow(this);
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp2);

            Button approveButton = new Button(this);
            approveButton.setText("Approve");
            approveButton.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            assignApproveButton(approveButton, requestId);
            row2.addView(approveButton);

            Button rejectButton = new Button(this);
            rejectButton.setText("Reject");
            rejectButton.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            TableRow row3 = new TableRow(this);
            TableRow.LayoutParams lp3 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp3);

            assignRejectButton(rejectButton, requestId);
            row3.addView(rejectButton);

            requests_table_layout.addView(row, i++);
            requests_table_layout.addView(row2, i++);
            requests_table_layout.addView(row3, i++);
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

    private void assignApproveButton(final Button btn, final int str){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                CharSequence text = "Approving Request " + str;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                try {
                    HashMap<String,Object> attributes = new HashMap<String,Object>();
                    attributes.put("requestID", str);
                    attributes.put("newStatus", 1);

                    Client client = new Client("updaterequeststatus", attributes);
                    client.execute().get();
                    Request response = client.getResponse();

                } catch(Exception e) {
                    e.printStackTrace();
                }


        }
        });
    }

    private void assignRejectButton(final Button btn, final int str){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = getApplicationContext();
                CharSequence text = "Rejecting Request " + str;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
    }

    public void onClick(View view) {

    }
}
