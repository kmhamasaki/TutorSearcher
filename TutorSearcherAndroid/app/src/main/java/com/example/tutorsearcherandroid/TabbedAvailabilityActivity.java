package com.example.tutorsearcherandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.tutorsearcherandroid.ui.main.DateFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutorsearcherandroid.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import tutor.searcher.TutorSearcher.Request;

public class TabbedAvailabilityActivity extends AppCompatActivity
    implements DateFragment.OnFragmentInteractionListener{

    private static final int[] CHECKBOX_ID = new int[]{R.id.time1,R.id.time2,R.id.time3,R.id.time4,R.id.time5,R.id.time6,R.id.time7,R.id.time8};
    private ArrayList<Integer> selectedTimes = new ArrayList<>();
    private String sourcePage;
    private String UserId;
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_availability);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sourcePage = extras.getString("SourcePage");
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }
        // update Title AND Submit button message
        TextView pageTitle = (TextView)findViewById(R.id.tabbed_availability_title);
        Button submitButton = (Button)findViewById(R.id.submitButton);

        if(sourcePage.equals("SearchTutor")){
            pageTitle.setText(R.string.title_activity_search_tutor);
            submitButton.setText("Search");
        }
        else if(sourcePage.equals("EditProfile")){
            pageTitle.setText("Edit Availability");
            submitButton.setText("Save");
        }
        else if(sourcePage.equals("Signup")){
            pageTitle.setText("Set Your Availability");
            submitButton.setText("Save");
        }
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

    public void onClickCheckbox(View view){
        boolean checked = ((CheckBox) view).isChecked();
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        int tabNumber = tabLayout.getSelectedTabPosition();

        // Check which checkbox was clicked
        for(int i=0;i<CHECKBOX_ID.length;i++) {
            if (CHECKBOX_ID[i] == view.getId()){
                if(checked){
                    selectedTimes.add(i+tabNumber*CHECKBOX_ID.length);
                }
                // unchecked
                else{
                    selectedTimes.remove(Integer.valueOf(i+tabNumber*CHECKBOX_ID.length));
                }
            }
        }
    }

    public void onClickSubmit(View view){
        Context context = getBaseContext();
        Collections.sort(selectedTimes);
        CharSequence text = selectedTimes.toString();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        if(sourcePage.equals("SearchTutor")){
            // ***INSERT CODE TO SEND TO BACKEND HERE***
                // we can probably create a function for this because it's used all the time?
            Bundle test = getIntent().getExtras();
            String className = test.getString("ClassName");
            HashMap<String, Object> attr = new HashMap<>();
            attr.put("availability", selectedTimes);
            attr.put("className", className);
            System.out.println(className);
            Client client = new Client("search", attr);
            client.execute();
            Request response = null;
            while(response == null) {
                response = client.getResponse();
            }
            System.out.println("Test " + response.getRequestType());

            openSearchResultsActivity(response);
            // proceed to search result page
        }
        else if(sourcePage.equals("EditProfile")){
            // ***INSERT CODE TO SEND TO BACKEND HERE***


            // return back to edit profile page
        }
        else if(sourcePage.equals("Signup")){
            try {
                HashMap<String, Object> attr = new HashMap<>();
                attr.put("availability", selectedTimes);
                attr.put("tutorID", Integer.parseInt(UserId));

                Client client = new Client("updateavailability", attr);
                client.execute().get();
                Request response = client.getResponse();
                System.out.println(response.getRequestType());
            } catch (Exception e) {
                e.printStackTrace();
            }


            // go to home page
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("UserId",UserId);
            i.putExtra("AccountType", UserId);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }

    public void openSearchResultsActivity(Request response) {
        Intent i = new Intent(this, SearchResultsActivity.class);
        Bundle extras = getIntent().getExtras();
        i.putExtra("ClassName", extras.getString("ClassName"));
        i.putExtra("UserId", extras.getString("UserId"));
        i.putExtra("TutorList",response);
        startActivity(i);
    }
}