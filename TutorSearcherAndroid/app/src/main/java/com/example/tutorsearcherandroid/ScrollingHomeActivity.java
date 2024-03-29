package com.example.tutorsearcherandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import tutor.searcher.TutorSearcher.Request;
import tutor.searcher.TutorSearcher.Tutor;

public class ScrollingHomeActivity extends AppCompatActivity implements MyAdapter.OnTutorClickListener {

    private SharedPreferences sharedPreferences;

    private String UserId;
    private String AccountType;
    public String Class;

    //Recyler View Variables
    private List<Tutor> TutorList;
    private RecyclerView recyclerView;
    private MyAdapter rAdapter; //Bridge between list and recyclerview
    private RecyclerView.LayoutManager rLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("LoginData",
                Context.MODE_PRIVATE);
        setContentView(R.layout.activity_scrolling_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.home_navigator);

        // function to hide/display title upon scrolling page
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                // home icons aren't shown
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Select a Tutor");
                    constraintLayout.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    isShow = true;
                }
                // home icons are shown
                else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    constraintLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                    isShow = false;
                }
            }
        });

        // restrict scrolling behaviour to only the bottom part of the screen
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        params.setBehavior(new AppBarLayout.Behavior());
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });

        Button viewRequestButton = findViewById(R.id.view_requests_button);
        Button searchButton = findViewById(R.id.search_button);
        Button updateProfileButton = findViewById(R.id.update_profile_button);
        Button logoutButton = findViewById(R.id.logout_button);
        Button acceptedRequestsButton = findViewById(R.id.accepted_requests_button);

        //Recycler Initiation
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
            if(AccountType.equals("Tutee")){
                Request response = (Request) extras.get("TutorList");
                if(response != null){
                    System.out.println("Got Response");
                    TutorList = (List<Tutor>) response.get("results");
                    Class = (String) response.get("className");
                }
            }
        }

        //Generate Recycler Information
        if(AccountType.equals("Tutee") && TutorList != null){
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.hasFixedSize();
            rLayoutManager = new LinearLayoutManager(this);
            rAdapter = new MyAdapter(TutorList);

            recyclerView.setLayoutManager(rLayoutManager);
            recyclerView.setAdapter(rAdapter);

            rAdapter.setOnTutorClickListener(this);
        }

        System.out.println(AccountType);
        if(AccountType.equals("Tutor")) {
            searchButton.setVisibility(View.GONE);
            NestedScrollView bottomContent = findViewById(R.id.nestedScrollView);
            bottomContent.setVisibility(View.GONE);
        } else {
            viewRequestButton.setVisibility(View.GONE);
        }
    }


    public void openViewRequestActivity(View view) {
        Intent i = new Intent(this, ViewRequests.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }

    public void openSearchActivity(View view) {
        //redirects to search screens
        Intent i = new Intent(this, SearchTutor.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }

    public void openUpdateProfileActivity(View view) {
        Intent i = new Intent(this, UpdateProfile.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        startActivity(i);
    }

    public void openMainActivity(View view) {
        //logs user out and redirects to login page

        // remove push notifs
        FirebaseMessaging.getInstance().unsubscribeFromTopic(UserId);

        // clear log in state
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("accountType");
        editor.remove("userId");
        editor.commit();

        // start home intent
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }

    public void openAcceptedRequestActivity(View view) {
        Intent i = new Intent(this, ViewRequestsAccepted.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }
    @Override
    public void onTutorClick(int position) {
        Intent i = new Intent(this, TutorTimeActivity.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);
        Tutor tutor = TutorList.get(position);
        i.putExtra("Tutor", tutor);
        System.out.println(tutor.getMatchingAvailabilities().get(0));
        i.putExtra("ClassName",Class);
        System.out.println(Class);
        startActivity(i);
    }

}
