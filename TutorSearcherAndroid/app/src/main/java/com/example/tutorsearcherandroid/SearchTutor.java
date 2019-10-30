package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import tutor.searcher.TutorSearcher.Request;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class SearchTutor extends AppCompatActivity {
    TextView serverResponse;
    Spinner classSpinner;
    Button submitButton;
    Request request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tutor);

        //Class Selection
        classSpinner = findViewById(R.id.classes_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.class_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);

        //Temp Response View
        serverResponse = findViewById(R.id.responseText);

        //Submit Button
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> attributes = new HashMap<String,Object>();

//                attributes.put("class","CSCI 103");
//                attributes.put("time","1,10,11,20");
                Client myClient = new Client("search", attributes);
                myClient.execute();
            }
        });
    }
}
