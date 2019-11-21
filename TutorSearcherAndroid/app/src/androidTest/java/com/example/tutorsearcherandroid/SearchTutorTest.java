package com.example.tutorsearcherandroid;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SearchTutorTest extends TestCase {

    @Test
    public void SuccessSearchTest() {
        ActivityTestRule<SearchTutor> activity =
                new IntentsTestRule<>(SearchTutor.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        //Select Class
        onView(withId(R.id.classes_spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CSCI 103"))).perform(click());
        onView(withId(R.id.classes_spinner)).check(matches(withSpinnerText(containsString("CSCI 103"))));
        onView(withId(R.id.submit_button)).perform(click());
        intended(hasComponent(TabbedAvailabilityActivity.class.getName()));

        //Select Time
        //TODO: Check Assigned Intents on Following Page


        //TODO: activity.getActivity().client = new ClientTest();

        onView(withId(R.id.submit_button)).perform(click());
        intended(hasComponent(SearchResultsActivity.class.getName()));

        Intents.release();
    }
}
