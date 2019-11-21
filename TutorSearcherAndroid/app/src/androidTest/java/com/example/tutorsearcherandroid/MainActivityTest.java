package com.example.tutorsearcherandroid;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.runner.RunWith;

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
