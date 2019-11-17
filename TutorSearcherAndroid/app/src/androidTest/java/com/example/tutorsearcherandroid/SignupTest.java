package com.example.tutorsearcherandroid;

import android.widget.TextView;

import junit.framework.TestCase;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.Espresso.onView;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import static androidx.test.espresso.action.ViewActions.*;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.intent.Intents.*;
import static androidx.test.espresso.intent.matcher.IntentMatchers.*;
import androidx.test.rule.ActivityTestRule;


import java.util.HashMap;

import static org.mockito.Mockito.*;

import tutor.searcher.TutorSearcher.Request;

@RunWith(AndroidJUnit4.class)
public class SignupTest extends TestCase {

    @Test
    public void LoginCorrect() {
        ActivityTestRule<SignupActivity> activity = new IntentsTestRule<>(SignupActivity.class, true, true);
        Intent intent = new Intent();

        activity.launchActivity(intent);
        onView(withId(R.id.email)).perform(typeText("tutor2020@usc.edu"));
        onView(withId(R.id.first_name)).perform(typeText("Tutor"));
        onView(withId(R.id.last_name)).perform(typeText("Trojan"));
        onView(withId(R.id.password)).perform(typeText("asdfas"));
        onView(withId(R.id.phone)).perform(typeText("123456789"));
        onView(withId(R.id.phone)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.signup_button)).perform(click());
        intended(hasComponent(ChooseClasses.class.getName()));
//        intended(hasExtra("UserId", "1"));
    }
}
