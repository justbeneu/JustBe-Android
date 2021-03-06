package justbe.mindfulness;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule mActivityRule = new IntentsTestRule(MainActivity.class);

    //test preferenceButton on main page
    @Test
    public void testPreferencesButton() {
        onView(withId(R.id.preferencesButton)).check(matches(isDisplayed()));
        onView(withId(R.id.preferencesButton)).perform(click());
        intended(hasComponent(PreferencesActivity.class.getName()));
        onView(withText("Preferences")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("Username")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("Preferences")).check(doesNotExist());

    }

    //testWeekButton on the main page
    @Test
    public void testWeekButton() {
        onView(withId(R.id.weekButton)).check(matches(isDisplayed()));
        onView(withId(R.id.weekButton)).perform(click());
        onView(withText("This Week")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("This Week")).check(doesNotExist());
    }

    //test WeeklyLessonButton on the main page
    @Test
    public void testWeeklyLessonButton() {
        onView(withId(R.id.weeklyLessonButton)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.weeklyLessonButtonText), withText("Week 1 Exercise")))
                .check(matches(isDisplayed()));
        onView(withId(R.id.weeklyLessonButton)).perform(click());
        intended(hasComponent(LessonActivity.class.getName()));
        onView(withText("Weekly Lesson")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("Weekly Lesson")).check(doesNotExist());
    }

    @Test
    public void WeekButton() {
        onView(withId(R.id.Meditation6)).perform(click());
        onView(withId(R.id.Meditation0)).perform(click());
        onView(withId(R.id.Meditation1)).perform(click());
        onView(withId(R.id.Meditation2)).perform(click());
        onView(withId(R.id.Meditation3)).perform(click());
        onView(withId(R.id.Meditation4)).perform(click());
        onView(withId(R.id.Meditation5)).perform(click());

    }

    @Test
    public void AudioButton() {
        int audioButtonId = R.id.audioButton;

        onView(withId(audioButtonId)).perform(click());
        onView(withId(audioButtonId)).perform(click());
    }

}
