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
public class UpdateProfileTest extends TestCase {

    @Test
    public void TuteeTest() {
        ActivityTestRule<UpdateProfile> activity =
                new IntentsTestRule<>(UpdateProfile.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType", "Tutee");

        activity.launchActivity(intent);

        onView(withId(R.id.accepted_requests_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.update_classses_button)).check(matches(not(isDisplayed())));
        Intents.release();
    }

    @Test
    public void TutorTest() {
        ActivityTestRule<UpdateProfile> activity =
                new IntentsTestRule<>(UpdateProfile.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "2");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withId(R.id.accepted_requests_button)).check(matches(isDisplayed()));
        onView(withId(R.id.update_classses_button)).check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void UpdateToastTest() {
        ActivityTestRule<UpdateProfile> activity =
                new IntentsTestRule<>(UpdateProfile.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "2");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);
        onView(withId(R.id.firstName)).perform(typeText("Trojan"));
        onView(withId(R.id.button3)).perform(click());

        onView(withText("Successfully updated profile information!"))
                .inRoot(withDecorView(not(activity.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
        Intents.release();
    }

    @Test
    public void UpdateClassesTest() {
        ActivityTestRule<UpdateProfile> activity =
                new IntentsTestRule<>(UpdateProfile.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "2");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withId(R.id.update_classses_button)).perform(click());
        intended(hasComponent(ChooseClasses.class.getName()));
        Intents.release();
    }

    @Test
    public void UpdateAvailabilityTest() {
        ActivityTestRule<UpdateProfile> activity =
                new IntentsTestRule<>(UpdateProfile.class, true, false);
        Intent intent = new Intent();

        intent.putExtra("UserId", "2");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withId(R.id.accepted_requests_button)).perform(click());
        intended(hasComponent(TabbedAvailabilityActivity.class.getName()));
        Intents.release();
    }

}
