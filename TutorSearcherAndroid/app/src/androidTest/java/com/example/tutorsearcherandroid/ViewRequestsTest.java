package com.example.tutorsearcherandroid;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.DataInteraction;

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;

import static androidx.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class ViewRequestsTest extends TestCase {

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    //https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/

    @Test
    public void BasicTest() {
        ActivityTestRule<ViewRequests> activity =
                new IntentsTestRule<>(ViewRequests.class, true, false);
        Intent intent = new Intent();
        intent.putExtra("UserId", "1");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withRecyclerView(R.id.recyclerView).atPosition(0))
                .check(matches(hasDescendant(withText("TuteeName1"))))
                .check(matches(hasDescendant(withText("Monday: 12:00 pm - 1:00 pm"))))
                .check(matches(hasDescendant(withText("CSCI 103"))))
                .check(matches(hasDescendant(withText("Accept"))))
                .check(matches(hasDescendant(withText("Reject"))));
        onView(withRecyclerView(R.id.recyclerView).atPosition(1))
                .check(matches(hasDescendant(withText("TuteeName2"))))
                .check(matches(hasDescendant(withText("Tuesday: 10:00 am - 11:00 am"))))
                .check(matches(hasDescendant(withText("CSCI 102"))))
                .check(matches(hasDescendant(withText("Accept"))))
                .check(matches(hasDescendant(withText("Reject"))));
        Intents.release();
    }

   /* @Test
    public void TutorTest() {
        ActivityTestRule<UpdateProfile> activity =
                new IntentsTestRule<>(UpdateProfile.class, true, false);
        //ActivityTestRule<UpdateProfile> activity = new IntentsTestRule<>(UpdateProfile.class, true, false);
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
        //ActivityTestRule<UpdateProfile> activity = new IntentsTestRule<>(UpdateProfile.class, true, false);
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
       // Intents.init();
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
        //Intents.init();

        intent.putExtra("UserId", "2");
        intent.putExtra("AccountType", "Tutor");

        activity.launchActivity(intent);

        onView(withId(R.id.accepted_requests_button)).perform(click());
        intended(hasComponent(TabbedAvailabilityActivity.class.getName()));
        Intents.release();
    }*/

}
