package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                openLoginActivity("tutor");
                break;
            case R.id.button2:
                openLoginActivity("tutee");
                break;
        }
    }

    public void openLoginActivity(String accountType){
        Intent i = new Intent(this,LoginActivity.class);
        i.putExtra("accountType",accountType);
        startActivity(i);
    }
}
