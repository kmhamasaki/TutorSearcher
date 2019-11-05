package com.example.tutorsearcherandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;
import android.view.View.OnTouchListener;

public class RatingActivity extends AppCompatActivity{

    private int stars = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        final RatingBar ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    stars = (int)starsf + 1;
                    ratingBar.setRating(stars);

                    v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }

                return true;
            }});

    }

    public void onClick(View view){
        Toast.makeText(getBaseContext(), String.valueOf(stars), Toast.LENGTH_SHORT).show();

        // send to db
    }


}
