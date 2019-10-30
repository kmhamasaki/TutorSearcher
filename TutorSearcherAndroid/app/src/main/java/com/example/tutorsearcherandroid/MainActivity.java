package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register);
        TextView textView5 = findViewById(R.id.textView5);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        textView5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                EditText username = findViewById(R.id.username);
                EditText password = findViewById(R.id.password);
                username.setVisibility(View.VISIBLE);
                password.setVisibility(View.VISIBLE);

                Button login = findViewById(R.id.login);
                Button register = findViewById(R.id.register);
                login.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);

                Button bigLogin = findViewById(R.id.bigLogin);
                bigLogin.setVisibility(View.VISIBLE);

                TextView textView4 = findViewById(R.id.textView4);
                TextView textView5 = findViewById(R.id.textView5);
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.VISIBLE);


                break;
            case R.id.register:
                openLoginActivity("asdf");
                break;

            case R.id.textView5:
                openLoginActivity("yas");
                break;
        }
    }

    public void openLoginActivity(String accountType){
        Intent i = new Intent(this,HomeActivity.class);
        i.putExtra("accountType",accountType);
        startActivity(i);
    }
}
