package com.example.tutorsearcherandroid;

import android.content.Intent;

//import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.hamcrest.core.AllOf;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import tutor.searcher.TutorSearcher.Tutor;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class RequestTutorTest extends TestCase {
    @Test
    public void SuccessSearchTest() {
        ActivityTestRule<SearchResultsActivity> activity =
                new IntentsTestRule<>(SearchResultsActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        //Set Test Tutor List and Class

        intended(hasComponent(TabbedAvailabilityActivity.class.getName()));

        //Select Tutor
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(TutorTimeActivity.class.getName()));
        //Select Time
        onView(AllOf.allOf(withText("Monday: 9:00 am - 10:00 am"),
                withParent(withId(R.id.radio_group)))).perform(click());
//        onView(withId(R.id.sendRequest)).perform(click());
//        intended(hasComponent(ScrollingHomeActivity.class.getName()));

        Intents.release();
    }
    @Test
    public void NoResultTest() {
        ActivityTestRule<SearchResultsActivity> activity =
                new IntentsTestRule<>(SearchResultsActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        //Set Test Tutor List and Class

        intended(hasComponent(TabbedAvailabilityActivity.class.getName()));


        Intents.release();
    }
}
