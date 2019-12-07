package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;

public class BioActivity extends AppCompatActivity {

    public static final int PICK_PROFILE_IMAGE_INTENT = 1;

    private String sourcePage;
    private String UserId;
    private String AccountType;
    Application app;
    TextView bio;
    TextView bioHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        app = (Application)getApplicationContext();

        // Picture
        ImageView profilePic = (ImageView) findViewById(R.id.profile_image_view);

        bio = (TextView) findViewById(R.id.bio);
        bioHeader = (TextView) findViewById(R.id.bio_header);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sourcePage = extras.getString("SourcePage");
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }
        if(AccountType.equals("Tutor")){
            bioHeader.setText("What would you like Tutees to know about you?");
        }else if(AccountType.equals("Tutee")){
            bioHeader.setText("What would you like Tutors to know about you?");
        }

        if(sourcePage.equals("UpdateProfile")) {
            try {
                HashMap<String, Object> attr = new HashMap<>();
                attr.put("userID", Integer.parseInt(UserId));
                Client client = Client.initClient("getbio", attr, app);
                client.execute().get();
                Request response = client.getResponse();

                bio.setText((String)response.get("bio"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                HashMap<String, Object> attr = new HashMap<>();
                attr.put("userID", Integer.parseInt(UserId));
                Client client = Client.initClient("getProfilePicBlob", attr, app);
                client.execute().get();
                Request response = client.getResponse();

                String a = (String) response.get("profilePicBlob");
                System.out.println("HELLOOSADASDSADSASD");
                System.out.println(a);

                profilePic.setImageBitmap(ProfilePictureUtil.StringToBitMap((String)response.get("profilePicBlob")));


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void onClick(View view) {
        if(view.getId() == R.id.submit_button) {
            String bioText = bio.getText().toString();
            if(bioText.length()>127){
                Toast t = Toast.makeText(this, "Please shorten your bio to 127 characters.",
                        Toast.LENGTH_LONG);
                t.show();
                return;
            }

            if(bioText.length() != 0) {
                HashMap<String, Object> attr = new HashMap<>();
                attr.put("userID", Integer.parseInt(UserId));
                attr.put("bio", bioText);
                Client client = Client.initClient("addbio", attr, app);
                client.execute();
            }

            if(sourcePage.equals("UpdateProfile")) {
                openUpdateProfile();
            } else {
                openHome();
            }

        } else if(view.getId() == R.id.upload_profile_image_button) {
            Toast t = Toast.makeText(this, "Testing.",
                    Toast.LENGTH_LONG);
            t.show();

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, PICK_PROFILE_IMAGE_INTENT);

        }
    }

    public void openHome() {
        Intent i = new Intent(this, ScrollingHomeActivity.class);
        i.putExtra("UserId",UserId);
        i.putExtra("AccountType", AccountType);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    public void openUpdateProfile() {
        Intent i = new Intent(this, UpdateProfile.class);
        i.putExtra("UserId",UserId);
        i.putExtra("AccountType", AccountType);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PROFILE_IMAGE_INTENT && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            try {
                Bitmap profilePic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                String profilePicBlob = ProfilePictureUtil.BitMapToString(profilePic);

                System.out.println(profilePicBlob);


                HashMap<String, Object> attr = new HashMap<>();
                attr.put("userID", Integer.parseInt(UserId));
                attr.put("profilePicBlob", profilePicBlob);

                Client client = Client.initClient("updateProfilePicture", attr, app);
                client.execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

//            InputStream inputStream = context.getContentResolver().openInputStream(data.getData());
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

}
