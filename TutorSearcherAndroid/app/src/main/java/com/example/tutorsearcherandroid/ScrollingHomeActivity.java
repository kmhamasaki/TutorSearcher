package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class ScrollingHomeActivity extends AppCompatActivity {

    private String UserId;
    private String AccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Button checkAvailability = findViewById(R.id.sendRequest3);
                // home icons aren't shown
                if (scrollRange + verticalOffset == 0) {
                    constraintLayout.setVisibility(View.INVISIBLE);
                    toolbar.setVisibility(View.VISIBLE);
                    checkAvailability.setVisibility(View.VISIBLE);
                    isShow = true;
                }
                // home icons are shown
                else if (isShow) {
                    constraintLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                    checkAvailability.setVisibility(View.INVISIBLE);
                    isShow = false;
                }
            }
        });

        Button viewRequestButton = findViewById(R.id.view_requests_button);
        Button searchButton = findViewById(R.id.search_button);
        Button updateProfileButton = findViewById(R.id.update_profile_button);
        Button logoutButton = findViewById(R.id.logout_button);
        Button acceptedRequestsButton = findViewById(R.id.accepted_requests_button);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserId = extras.getString("UserId");
            AccountType = extras.getString("AccountType");
        }

        System.out.println(AccountType);
        if(AccountType.equals("Tutor")) {
            searchButton.setVisibility(View.GONE);
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
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(i);
    }

    public void openAcceptedRequestActivity(View view) {
        Intent i = new Intent(this, ViewAcceptedRequests.class);
        i.putExtra("UserId", UserId);
        i.putExtra("AccountType", AccountType);

        startActivity(i);
    }

}
