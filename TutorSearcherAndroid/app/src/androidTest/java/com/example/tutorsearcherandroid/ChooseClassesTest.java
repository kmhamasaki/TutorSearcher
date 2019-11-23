package com.example.tutorsearcherandroid;

import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class ChooseClassesTest extends TestCase {

    @Test
    public void SuccessClassSelectionTest() {
        ActivityTestRule<ChooseClasses> activity =
                new IntentsTestRule<>(ChooseClasses.class, true, true);
        Intent intent = new Intent();
        intent.putExtra("SourcePage","Signup");
        activity.launchActivity(intent);

        onView(withId(R.id.CSCI102)).perform(click());

        onView(withId(R.id.continueButton)).perform(click());

        intended(hasComponent(TabbedAvailabilityActivity.class.getName()));
        Intents.release();
    }
    @Test
    public void FailClassSelectionTest() {
        ActivityTestRule<ChooseClasses> activity =
                new IntentsTestRule<>(ChooseClasses.class, true, true);
        Intent intent = new Intent();
        intent.putExtra("SourcePage","Signup");
        activity.launchActivity(intent);

        onView(withId(R.id.continueButton)).perform(click());

        onView(withText("Please select at least one class to tutor."))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));


        Intents.release();
    }
}
