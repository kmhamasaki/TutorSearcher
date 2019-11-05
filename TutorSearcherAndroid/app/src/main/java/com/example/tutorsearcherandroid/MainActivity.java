package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutee;
import tutor.searcher.TutorSearcher.Tutor;
import tutor.searcher.TutorSearcher.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

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
        Button bigLogin = findViewById(R.id.bigLogin);
        TextView textView5 = findViewById(R.id.textView5);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        bigLogin.setOnClickListener(this);
        textView5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // handle when user clicks "sign in" at the start
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

                //  handle case when user clicks "register" at the start
            case R.id.register:
                openSignupActivity();
                break;

                // handle case when user clicks "register" if he doesn't have an account
            case R.id.textView5:
                openSignupActivity();
                break;

                // handle case when user clicks "sign in" after entering his user and pass
            case R.id.bigLogin:
                // authentication function <- FUNCTION NEEDS TO RETURN ACCOUNT TYPE (tutor or tutee)
                // FILL IN CAM


                HashMap<String, Object> attr = new HashMap<>();
                attr.put("email",((android.widget.TextView)findViewById(R.id.username)).getText().toString());
                String unhashedPassword = ((android.widget.TextView)findViewById(R.id.password)).getText().toString();

                //Hash Password using MD5
                String hashedPassword = hashPassword(unhashedPassword);
                System.out.println(hashedPassword);
                attr.put("passwordHash", hashedPassword);

                //TODO: Connect to backend
                //Pass all inputs to backend
                System.out.println(attr.get("email"));
                System.out.println(attr.get("passwordHash"));
                Client client = new Client("login",attr);
                try {
                    client.execute().get();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                Request response = client.getResponse();

                if(response.getRequestType().equals("Error: wrong email or password")){
                    System.out.println("Error: wrong email or password");
                    Toast t = Toast.makeText(this, "Wrong email and password combination",
                            Toast.LENGTH_LONG);
                    t.show();
                } else{
                    // if authentication is finished, go to home page
                    System.out.println("Success: Logging in user - ");
                    //Save User Information and Redirect to Appropriate Home Page
                    attr = response.getAttributes();

                    if(attr.get("User").getClass() == Tutor.class){
                        System.out.println("Logging in Tutor");
                        Tutor tutor = (Tutor) attr.get("User");
                        String userId = Integer.toString(tutor.getUserId());
                        openHomeActivity("Tutor", userId);
                        break;
                    }else {
                        System.out.println("Logging in Tutee");
                        Tutee tutee = (Tutee) attr.get("User");
                        String userId = Integer.toString(tutee.getUserId());
                        openHomeActivity("Tutee", userId);
                        break;
                    }
                }
        }
    }

    public void openSignupActivity(){
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    public void openHomeActivity(String accountType, String userId) {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("AccountType", accountType);
        i.putExtra("UserId", userId);
        finish();
        startActivity(i);
    }

    private String hashPassword(String unhashedPassword){
        String hashedPassword = "";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(unhashedPassword.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashedPassword = no.toString(16);
            while (hashedPassword.length() < 32) {
                hashedPassword = "0" + hashedPassword;
            }
            return hashedPassword;
        }catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}
