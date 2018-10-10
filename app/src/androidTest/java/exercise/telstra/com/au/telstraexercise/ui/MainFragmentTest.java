package exercise.telstra.com.au.telstraexercise.ui;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import exercise.telstra.com.au.telstraexercise.MainActivity;
import exercise.telstra.com.au.telstraexercise.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // Check if the main list has been displayed.
    @Test public void checkRecyclerViewIsDisplayed(){
        onView(withId(R.id.fact_list))
                .check(matches(isDisplayed()));

        onView(withId(R.id.fact_list))
                .perform(RecyclerViewActions.<FactListAdapter.FactViewHolder>scrollToPosition(13));
    }

    // This is for testing the error scenario, make sure the app will display error message when http request fails.
    @Test public void checkErrorMessageDisplayWhenHttpFail(){
        onView(withId(R.id.error_tv))
                .check(matches(isDisplayed()));
    }
}