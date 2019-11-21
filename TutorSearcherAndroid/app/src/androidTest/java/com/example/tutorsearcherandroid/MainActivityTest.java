package com.example.tutorsearcherandroid;

import android.content.Intent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends TestCase {

/*    @Test
    public void SuccessLoginTest() {
        ActivityTestRule<MainActivity> activity =
                new IntentsTestRule<>(MainActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        activity.getActivity().client = new ClientTest();

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("tutor1@usc.edu"));
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.bigLogin)).perform(click());
        intended(hasComponent(ScrollingHomeActivity.class.getName()));

        //TODO: Check Assigned Intents on Following Page
        Intents.release();
    }

    @Test
    public void InvalidEmailTest() {
        ActivityTestRule<MainActivity> activity =
                new IntentsTestRule<>(MainActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("fakeEmail@usc.edu"));
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.bigLogin)).perform(click());

        onView(withText("Wrong email and password combination"))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Intents.release();
    }
    @Test
    public void InvalidPasswordTest() {
        ActivityTestRule<MainActivity> activity =
                new IntentsTestRule<>(MainActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("validEmail@usc.edu"));
        onView(withId(R.id.password)).perform(typeText("badPassword"));
        onView(withId(R.id.bigLogin)).perform(click());

        onView(withText("Wrong email and password combination"))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Intents.release();
    }*/
}
