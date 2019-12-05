package com.example.tutorsearcherandroid;

import android.content.Intent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends TestCase {

    @Test
    public void SuccessTutorLoginTest() {
        ActivityTestRule<MainActivity> activity =
                new IntentsTestRule<>(MainActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("successTutor@usc.edu"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.bigLogin)).perform(click());
        intended(hasComponent(ScrollingHomeActivity.class.getName()));

        Intents.release();
    }

    @Test
    public void SuccessTuteeLoginTest() {
        ActivityTestRule<MainActivity> activity =
                new IntentsTestRule<>(MainActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("successTutee@usc.edu"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.bigLogin)).perform(click());
        intended(hasComponent(ScrollingHomeActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void InvalidLoginTest() {
        ActivityTestRule<MainActivity> activity =
                new IntentsTestRule<>(MainActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("invalidEmail@usc.edu"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.bigLogin)).perform(click());

        onView(withText("Wrong email and password combination"))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        Intents.release();
    }

    @Test
    public void MostRecentSearchTest() {
        ActivityTestRule<MainActivity> activity =
                new IntentsTestRule<>(MainActivity.class, true, true);
        Intent intent = new Intent();
        activity.launchActivity(intent);

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.username)).perform(typeText("successTutee@usc.edu"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.password)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.bigLogin)).perform(click());
        intended(hasComponent(ScrollingHomeActivity.class.getName()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(TutorTimeActivity.class.getName()));

        Intents.release();
    }

}
