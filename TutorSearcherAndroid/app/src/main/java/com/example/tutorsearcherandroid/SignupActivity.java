package com.example.tutorsearcherandroid;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Pattern;

import tutor.searcher.TutorSearcher.Request;

public class SignupActivity extends AppCompatActivity {
    Client client;

    private String UserId;
    private String AccountType;

    String email;
    String passwordHash;
    String firstName;
    String lastName;
    String phoneNumber;

    RadioButton selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Application app = (Application)getApplicationContext();
        client = Client.initClient(app);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

    }

    public void getViews() {
        email = ((TextView) findViewById(R.id.email)).getText().toString();
        passwordHash = ((TextView) findViewById(R.id.password)).getText().toString();
        firstName = ((TextView) findViewById(R.id.first_name)).getText().toString();
        lastName = ((TextView) findViewById(R.id.last_name)).getText().toString();
        phoneNumber = ((TextView) findViewById(R.id.phone)).getText().toString();

        RadioGroup rg = findViewById(R.id.tutorTuteeRadioSelector);
        int selectedId = rg.getCheckedRadioButtonId();
        selectedButton = findViewById(selectedId);
        AccountType = selectedButton.getText().toString();
    }

    public boolean verifyFields() {
        // If not all fields, filled, out error.
        if (email.equals("") || passwordHash.equals("") || firstName.equals("") ||
                phoneNumber.equals("") || lastName.equals("")) {
            Toast t = Toast.makeText(this, "You must complete all fields to sign up!",
                    Toast.LENGTH_LONG);
            t.show();
            return false;
        }
        return true;
    }

    public boolean verifyUSC() {
        if (!Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@usc.edu").matcher(email).matches()) {
            Toast t = Toast.makeText(this, "Please enter a USC email.",
                    Toast.LENGTH_LONG);
            t.show();
            return false;
        }
        return true;
    }

    public Request clientCall() {
        HashMap<String, Object> attr = new HashMap<>();
        attr.put("accountType", (!selectedButton.getText().toString().equals("Tutee")));

        //HashPassword
        passwordHash = hashPassword(passwordHash);
        System.out.println(passwordHash);

        attr.put("email", email);
        attr.put("passwordHash", passwordHash);
        attr.put("firstName", firstName);
        attr.put("lastName", lastName);
        attr.put("phoneNumber", phoneNumber);

        System.out.println("Sending from SignupButtonClick");
        client.setTypeAndAttr("signup", attr);

        // Pass all inputs to backend
        try {
            client.execute().get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        Request response = client.getResponse();
        System.out.println(response.getRequestType());

        // Error if email already exists
        if (response.getRequestType().equals("Error: email exists")) {
            Toast t = Toast.makeText(this, "Email already in use.",
                    Toast.LENGTH_LONG);
            t.show();
            return null;
        }

        return response;
    }

    public void onClick(View view) {
        try {
            getViews();

            if(!verifyFields()) {
                return;
            }

            if(!verifyUSC()) {
                return;
            }

            Request response = clientCall();
            if(response == null)
                return;

            UserId = (String) response.getAttributes().get("userID");
            FirebaseMessaging.getInstance().subscribeToTopic(UserId);

            // Success, tutee, go to home page
            if (AccountType.equals("Tutee")) {
                openHomeActivity();
            }

            // Success, tutor, go to availability page.
            else {
                openClassesActivity();
                //openAvailabilityActivity();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void openHomeActivity(){
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        // this destroys all activities before this page
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


    public void openClassesActivity() {
        Intent i = new Intent(this, ChooseClasses.class);
        i.putExtra("SourcePage","Signup");
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }

    public static String hashPassword(String unhashedPassword){
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

