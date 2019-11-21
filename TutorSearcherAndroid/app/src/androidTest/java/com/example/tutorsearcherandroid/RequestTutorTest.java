package com.example.tutorsearcherandroid;

import android.content.Intent;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

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
        Tutor tutor = new Tutor(1,"Tutor","Tutor",
                "tutor@usc.edu","8081234567","password",
                true,"5",5.0);
        ArrayList<Tutor> t = new ArrayList<>();
        t.add(tutor);
        activity.getActivity().TutorList = t;
        activity.getActivity().Class = "CSCI 102";

        //Select Tutor
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(TabbedAvailabilityActivity.class.getName()));
        intended(hasComponent(TutorTimeActivity.class.getName()));
        //Select Time

        //TODO: activity.getActivity().client = new ClientTest();
//        onView(allOf(withText("Monday: 9:00am - 10:00am"),
//                withParent(withId(R.id.radio_group))))
//                .check(matches(isChecked()));
//        onView(withId(R.id.sendRequest)).perform(click());
//        intended(hasComponent(ScrollingHomeActivity.class.getName()));

        Intents.release();
    }
}
