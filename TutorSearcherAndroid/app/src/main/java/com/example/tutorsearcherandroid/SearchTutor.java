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

import java.util.HashMap;

public class SearchTutor extends AppCompatActivity implements View.OnClickListener{
    TextView serverResponse;
    Spinner classSpinner;

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

        Button submitButton;
        //Submit Button
        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
    }

    public void onClick(View view){
//        HashMap<String, Object> attr = new HashMap<>();
//        attr.put("className", classSpinner.getSelectedItem().toString());
//        attr.put("availability", "");
//        Client client = new Client("search",attr);
//
//        client.execute();

//        System.out.println("Adding selected class " + classSpinner.getSelectedItem().toString());
        openAvailabilityActivity();
    }

    public void openAvailabilityActivity(){
        Intent intent = new Intent(this, AvailabilityActivity.class);
        intent.putExtra("CLASS_NAME", classSpinner.getSelectedItem().toString());
        startActivity(intent);
        finish();
    }
}
