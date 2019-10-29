package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        String accountType = extras.getString("accountType");
        accountType = accountType.substring(0,1).toUpperCase() + accountType.substring(1).toLowerCase();
        TextView T =(TextView)findViewById(R.id.textView);
        T.setText(accountType + " Log In");
        Toast.makeText(getApplicationContext(),"Acc type: "+accountType, Toast.LENGTH_SHORT).show();
    }
}
