package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.*;

import tutor.searcher.TutorSearcher.Request;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onClick(View view) {
        try {
            HashMap<String, Object> attr = new HashMap<>();

            String email = ((TextView) findViewById(R.id.email)).getText().toString();
            String passwordHash = ((TextView) findViewById(R.id.password)).getText().toString();
            String firstName = ((TextView) findViewById(R.id.first_name)).getText().toString();
            String lastName = ((TextView) findViewById(R.id.last_name)).getText().toString();
            String phoneNumber = ((TextView) findViewById(R.id.phone)).getText().toString();

            // If not all fields, filled, out error.
            if (email.equals("") || passwordHash.equals("") || firstName.equals("") ||
                    phoneNumber.equals("") || lastName.equals("")) {
                Toast t = Toast.makeText(this, "You must complete all fields to sign up!",
                        Toast.LENGTH_LONG);
                t.show();
                return;
            }

            // Error not usc email
            if (!Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@usc.edu").matcher(email).matches()) {
                Toast t = Toast.makeText(this, "Please enter a USC email.",
                        Toast.LENGTH_LONG);
                t.show();
                return;
            }

            RadioGroup rg = findViewById(R.id.tutorTuteeRadioSelector);
            int selectedId = rg.getCheckedRadioButtonId();
            RadioButton selectedButton = findViewById(selectedId);
            attr.put("accountType", (!selectedButton.getText().equals("Tutee")));
            System.out.println(selectedButton.getText());
            //HashPassword
            passwordHash = hashPassword(passwordHash);
            System.out.println(passwordHash);

            attr.put("email", email);
            attr.put("passwordHash", passwordHash);
            attr.put("firstName", firstName);
            attr.put("lastName", lastName);
            attr.put("phoneNumber", phoneNumber);

            System.out.println("Sending from SignupButtonClick");
            Client client = new Client("signup", attr);

            // Pass all inputs to backend
            client.execute().get();
            Request response = client.getResponse();
            System.out.println(response.getRequestType());


            // Error if email already exists
            if (response.getRequestType().equals("Error: email exists")) {
                Toast t = Toast.makeText(this, "Email already in use.",
                        Toast.LENGTH_LONG);
                t.show();
            }

            // Success, tutor, go to home page
            else if (selectedButton.getText().equals("Tutee")) {
                openHomeActivity();
            }
            // Success, tutee, go to availability page.
            else {
                openAvailabilityActivity();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void openHomeActivity(){
        Intent i = new Intent(this, HomeActivity.class);
        // this destroys all activities before this page
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void openAvailabilityActivity() {
        Intent i = new Intent(this, TabbedAvailabilityActivity.class);
        i.putExtra("SourcePage","Signup");
        // this destroys all activities before this page
        /* we are doing this because once the tutor has filled in the sign up page, he has technically
        already signed up and his account details are in our database. so he should not be able to press
        the back button
         */
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

