package com.example.tutorsearcherandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;

public class ViewRequests extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_requests);

        TableRow request_row = (TableRow) findViewById(R.id.request_row);
    }

    public void onClick(View view) {

    }
}

