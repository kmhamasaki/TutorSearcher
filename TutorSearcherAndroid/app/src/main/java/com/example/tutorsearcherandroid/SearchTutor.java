package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import tutor.searcher.TutorSearcher.Request;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class SearchTutor extends AppCompatActivity implements View.OnClickListener{
    Spinner classSpinner;
    Button submitButton;

    private String UserId;

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

        //Submit Button
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
        }
    }

    public void onClick(View v){
        openAvailabilityActivity();
    }

    protected void openAvailabilityActivity(){
        Intent i = new Intent(this, TabbedAvailabilityActivity.class);
        i.putExtra("SourcePage","SearchTutor");
        String className = classSpinner.getSelectedItem().toString();
        i.putExtra("ClassName",className);
        i.putExtra("UserId",UserId);

        startActivity(i);
    }
}
