package com.example.androidassignments;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChatWindowInstrumentedTest {

    @Before
    public void launchActivity() {
        ActivityScenario.launch(ChatWindow.class);
    }

    @Test
    public void sendMessage_DisplayedInListView() {
        String testMessage = "Hello, this is a test message!";
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .perform(ViewActions.typeText(testMessage), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.sendButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.listView))
                .check(matches(hasDescendant(withText(testMessage))));
    }

    @Test
    public void addMultipleMessages_DisplayedInListView() {
        // Add multiple messages and check if they are displayed
        String[] testMessages = {"Message 1", "Message 2", "Message 3"};

        for (String message : testMessages) {
            Espresso.onView(ViewMatchers.withId(R.id.editText))
                    .perform(ViewActions.typeText(message), ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.sendButton)).perform(ViewActions.click());
        }

        // Check if all messages are displayed in the listView
        Espresso.onView(ViewMatchers.withId(R.id.listView))
                .check(matches(hasChildCount(testMessages.length)));
    }

    @Test
    public void checkAlternatingLayouts() {
        // Add messages and check if layouts alternate correctly
        String[] testMessages = {"Message 1", "Message 2", "Message 3"};

        for (String message : testMessages) {
            Espresso.onView(ViewMatchers.withId(R.id.editText))
                    .perform(ViewActions.typeText(message), ViewActions.closeSoftKeyboard());
            Espresso.onView(ViewMatchers.withId(R.id.sendButton)).perform(ViewActions.click());
        }

        // Check if layouts alternate correctly
        Espresso.onView(ViewMatchers.withId(R.id.listView))
                .check(matches(hasDescendant(withText(testMessages[0]))))
                .check(matches(hasDescendant(withText(testMessages[1]))))
                .check(matches(hasDescendant(withText(testMessages[2]))));
    }

    @Test
    public void clearEditTextAfterSendingMessage() {
        // Type a message, send it, and check if the EditText is cleared
        String testMessage = "Clear me!";
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .perform(ViewActions.typeText(testMessage), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.sendButton)).perform(ViewActions.click());

        // Check if the EditText is cleared after sending a message
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .check(matches(withText("")));
    }

    @Test
    public void checkIfChatAdapterIsUpdated() {
        // Add a message and check if the chatAdapter is updated
        String testMessage = "Update me!";
        Espresso.onView(ViewMatchers.withId(R.id.editText))
                .perform(ViewActions.typeText(testMessage), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.sendButton)).perform(ViewActions.click());

        // Check if the chatAdapter is updated with the new message
        Espresso.onView(ViewMatchers.withId(R.id.listView))
                .check(matches(hasDescendant(withText(testMessage))));
    }
}
