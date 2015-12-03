package justbe.mindfulnessapp;

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
        onView(withText("Meditation")).check(ViewAssertions.matches(isDisplayed()));

    }

    //testWeekButton on the main page
    @Test
    public void testWeekButton() {
        onView(withId(R.id.weekButton)).check(matches(isDisplayed()));
        onView(withId(R.id.weekButton)).perform(click());
        onView(withText("Week 1")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("Week 1")).check(doesNotExist());
        onView(withText("Meditation")).check(ViewAssertions.matches(isDisplayed()));
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
        onView(withText("Meditation")).check(ViewAssertions.matches(isDisplayed()));
    }

    //test AssessmentTmpButton on the main page
    @Test
    public void AssessmentTmpButton() {
        onView(withId(R.id.assessmentButton)).check(matches(isDisplayed()));
        onView(withId(R.id.assessmentButton)).perform(click());
        intended(hasComponent(SmokeAssessmentActivity.class.getName()));
        onView(withText("Assessment")).check(ViewAssertions.matches(isDisplayed()));
        pressBack();
        onView(withText("Assessment")).check(doesNotExist());
        onView(withText("Meditation")).check(ViewAssertions.matches(isDisplayed()));
    }

    //test AssessemntTmp on Assessment page
    @Test
    public void AssessmentPageYesTest() {
        onView(withId(R.id.assessmentButton)).check(matches(isDisplayed()));
        onView(withId(R.id.assessmentButton)).perform(click());
        intended(hasComponent(SmokeAssessmentActivity.class.getName()));
        onView(withText("Assessment")).check(ViewAssertions.matches(isDisplayed()));
        //press yes
        onView(withId(R.id.yesButton)).perform(click());
        onView(withText("Assessment")).check(doesNotExist());
        onView(withText("Meditation")).check(ViewAssertions.matches(isDisplayed()));
    }
    //test AssessemntTmp on Assessment page

    @Test
    public void AssessmentPageNoTest() {
        onView(withId(R.id.assessmentButton)).check(matches(isDisplayed()));
        onView(withId(R.id.assessmentButton)).perform(click());
        intended(hasComponent(SmokeAssessmentActivity.class.getName()));
        onView(withText("Assessment")).check(ViewAssertions.matches(isDisplayed()));
        //press yes
        onView(withId(R.id.noButton)).perform(click());
        onView(withText("Assessment")).check(doesNotExist());
        onView(withText("Meditation")).check(ViewAssertions.matches(isDisplayed()));
    }

}
