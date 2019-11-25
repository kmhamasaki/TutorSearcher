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
public class SignupTest extends TestCase {

    @Test
    public void SuccessTutorTest() {
        ActivityTestRule<SignupActivity> activity = new IntentsTestRule<>(SignupActivity.class, true, true);
        Intent intent = new Intent();

        activity.launchActivity(intent);
        onView(withId(R.id.email)).perform(typeText("tutor2020@usc.edu"));
        onView(withId(R.id.first_name)).perform(typeText("Success"));
        onView(withId(R.id.last_name)).perform(typeText("Trojan"));
        onView(withId(R.id.password)).perform(typeText("asdfas"));
        onView(withId(R.id.phone)).perform(typeText("123456789"));
        onView(withId(R.id.phone)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.signup_button)).perform(click());
        intended(hasComponent(ChooseClasses.class.getName()));
        Intents.release();
    }

    @Test
    public void SuccessTuteeTest() {
        ActivityTestRule<SignupActivity> activity = new IntentsTestRule<>(SignupActivity.class, true, true);
        Intent intent = new Intent();

        activity.launchActivity(intent);
        onView(withId(R.id.email)).perform(typeText("tutor2020@usc.edu"));
        onView(withId(R.id.first_name)).perform(typeText("Success"));
        onView(withId(R.id.last_name)).perform(typeText("Trojan"));
        onView(withId(R.id.password)).perform(typeText("asdfas"));
        onView(withId(R.id.phone)).perform(typeText("123456789"));
        onView(withId(R.id.phone)).perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button_tutee)).perform(click());

        onView(withId(R.id.signup_button)).perform(click());
        intended(hasComponent(ScrollingHomeActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void DuplicateEmailTest() {
        ActivityTestRule<SignupActivity> activity = new IntentsTestRule<>(SignupActivity.class, true, true);
        Intent intent = new Intent();

        activity.launchActivity(intent);
        onView(withId(R.id.email)).perform(typeText("tutor2020@usc.edu"));
        onView(withId(R.id.first_name)).perform(typeText("Duplicate"));
        onView(withId(R.id.last_name)).perform(typeText("Trojan"));
        onView(withId(R.id.password)).perform(typeText("asdfas"));
        onView(withId(R.id.phone)).perform(typeText("123456789"));
        onView(withId(R.id.phone)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.signup_button)).perform(click());
        onView(withText("Email already in use."))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Intents.release();
    }


    @Test
    public void NonUSCEmailTest() {
        ActivityTestRule<SignupActivity> activity = new IntentsTestRule<>(SignupActivity.class, true, true);
        Intent intent = new Intent();

        activity.launchActivity(intent);
        onView(withId(R.id.email)).perform(typeText("tutor2020@not.edu"));
        onView(withId(R.id.first_name)).perform(typeText("Duplicate"));
        onView(withId(R.id.last_name)).perform(typeText("Trojan"));
        onView(withId(R.id.password)).perform(typeText("asdfas"));
        onView(withId(R.id.phone)).perform(typeText("123456789"));
        onView(withId(R.id.phone)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.signup_button)).perform(click());
        onView(withText("Please enter a USC email."))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Intents.release();
    }


    @Test
    public void EmptyFieldsTest() {
        ActivityTestRule<SignupActivity> activity = new IntentsTestRule<>(SignupActivity.class, true, true);
        Intent intent = new Intent();

        activity.launchActivity(intent);
        onView(withId(R.id.email)).perform(typeText("tutor2020@not.edu"));
        onView(withId(R.id.last_name)).perform(typeText("Trojan"));
        onView(withId(R.id.password)).perform(typeText("asdfas"));
        onView(withId(R.id.phone)).perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.signup_button)).perform(click());
        onView(withText("You must complete all fields to sign up!"))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Intents.release();
    }
}
