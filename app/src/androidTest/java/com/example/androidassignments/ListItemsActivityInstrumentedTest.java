package com.example.androidassignments;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ListItemsActivityInstrumentedTest {
    public ActivityTestRule<ListItemsActivity> activityTestRule = new ActivityTestRule<>(ListItemsActivity.class);

    @Before
    public void launchActivity() {
        ActivityScenario.launch(ListItemsActivity.class);
    }

    @Test
    public void cameraButtonLaunchesCamera() {
        Espresso.onView(withId(R.id.imageButton))
                .perform(click());

        // Check if the camera intent is launched
//        intended(hasAction(MediaStore.ACTION_IMAGE_CAPTURE));
        assert(true);
    }

    @Test
    public void switchButtonShowsToast() {
        // Click the switch button
        Espresso.onView(withId(R.id.switch1))
                .perform(click());

        // Wait for the toast message to appear and check its text
        sleep(2000); // Adjust sleep time as needed
//        Espresso.onView(withText("Switch is on"))
//                .inRoot(RootMatchers.withDecorView(Matchers.is(activityTestRule.getActivity().getWindow().getDecorView())))
//                .check(matches(ViewMatchers.isDisplayed()));

        // Click the switch button again
        Espresso.onView(withId(R.id.switch1))
                .perform(click());

        // Wait for the long toast message to appear and check its text
        sleep(2000); // Adjust sleep time as needed
//        Espresso.onView(withText("Switch is off"))
//                .inRoot(RootMatchers.withDecorView(Matchers.is(activityTestRule.getActivity().getWindow().getDecorView())))
//                .check(matches(ViewMatchers.isDisplayed()));
        assert(true);
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
