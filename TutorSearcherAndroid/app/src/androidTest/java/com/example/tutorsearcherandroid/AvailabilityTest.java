package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.view.View;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class AvailabilityTest extends TestCase {
/*
    @Test
    public void TutorSignUpAvailabilityTest() {
        ActivityTestRule<TabbedAvailabilityActivity> activity =
                new IntentsTestRule<>(TabbedAvailabilityActivity.class, true, true);
        Intent intent = new Intent();
        intent.putExtra("SourcePage","Signup");
        intent.putExtra("UserId","1");
        intent.putExtra("AccountType","Tutor");

        activity.launchActivity(intent);

        onView(withText("WED")).perform(click());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.time1),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time2),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time3),isCompletelyDisplayed())).perform(click());

        onView(withText("MON")).perform(click());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.time5),isCompletelyDisplayed())).perform(click());

        onView(withText("SUN")).perform(click());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.time5),isCompletelyDisplayed())).perform(click());

        onView(withText("SAT")).perform(click());
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.time1),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time2),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time3),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time4),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time5),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time6),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time7),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time8),isCompletelyDisplayed())).perform(click());

        onView(withId(R.id.submitButton)).perform(click());

        intended(hasComponent(ScrollingHomeActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void TutorSignUpNoAvailabilityTest() {
        ActivityTestRule<TabbedAvailabilityActivity> activity =
                new IntentsTestRule<>(TabbedAvailabilityActivity.class, true, true);
        Intent intent = new Intent();
        intent.putExtra("SourcePage","Signup");
        intent.putExtra("UserId","1");
        intent.putExtra("AccountType","Tutor");

        activity.launchActivity(intent);

        onView(withText("TUE")).perform(click());

        onView(allOf(withId(R.id.time1),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time2),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time3),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time4),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time5),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time6),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time7),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time8),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time1),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time2),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time3),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time4),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time5),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time6),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time7),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time8),isCompletelyDisplayed())).perform(click());

        onView(withId(R.id.submitButton)).perform(click());

        onView(withText("Please select at least one time slot."))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Intents.release();
    }
    */

    @Test
    public void TutorEditAvailability(){
        ActivityTestRule<TabbedAvailabilityActivity> activity =
                new IntentsTestRule<>(TabbedAvailabilityActivity.class, true, true);
        Intent intent = new Intent();
        intent.putExtra("SourcePage","UpdateProfile");
        intent.putExtra("UserId","1");
        intent.putExtra("AccountType","Tutor");

        activity.launchActivity(intent);

        onView(withText("TUE")).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.time1),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time2),isCompletelyDisplayed())).perform(click());
        onView(allOf(withId(R.id.time3),isCompletelyDisplayed())).perform(click());

        onView(withId(R.id.submitButton)).perform(click());

        intended(hasComponent(UpdateProfile.class.getName()));
        Intents.release();
    }
}
