package justbe.mindfulnessapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import justbe.mindfulnessapp.models.User;

public class MainActivity extends AppCompatActivity {

    /**
     * Fields
     */
    private PopupWindow popupWindow;
    private User user;
    // su, m, t, w, th, f, s
    private String selectedDay;

    /**
     * Called when the view is created
     * @param savedInstanceState Saved Instance State
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        // Set toolbar view to custom toolbar for main view
        LayoutInflater li = LayoutInflater.from(this);
        View customToolbarView = li.inflate(R.layout.custom_main_toolbar, null);
        getSupportActionBar().setCustomView(customToolbarView);

        user = App.getSession().getUser();

        // Set the lesson button's text to the current week
        TextView lessonButtonText = (TextView) findViewById(R.id.weeklyLessonButtonText);
        user.setProgramWeek(3);
        lessonButtonText.setText(String.format("Week %d Exercise", user.getProgramWeek()));

        selectedDay = getCurrentDayOfTheWeek();
        updateSelectedDay(selectedDay);
    }

    /**
     * Colors the currently selected day and updates selectedDay
     * @param newDay the newly selected day
     */
    @SuppressLint("NewApi")
    private void updateSelectedDay(String newDay){
        int currentMeditationId = getResources().getIdentifier(
                selectedDay + "Meditation" , "id", getPackageName());
        int newMeditationId = getResources().getIdentifier(
                newDay + "Meditation" , "id", getPackageName());
        int currentTextViewId = getResources().getIdentifier(
                selectedDay + "MeditationText" , "id", getPackageName());
        int newTextViewId = getResources().getIdentifier(
                newDay + "MeditationText" , "id", getPackageName());

        // remove styling from current day
        TextView currentDayTextView = (TextView) findViewById(currentTextViewId);
        currentDayTextView.setTextColor(ContextCompat.getColor(this, R.color.transparentLightGreen));

        View currentDayLayout = findViewById(currentMeditationId);
        // Note: lint incorrectly marks setForeground as requiring api level 23 (https://code.google.com/p/android/issues/detail?id=186273)
        currentDayLayout.setForeground(null);

        // add styling to new day
        TextView newDayTextView = (TextView) findViewById(newTextViewId);
        newDayTextView.setTextColor(ContextCompat.getColor(this, R.color.bpWhite));

        View newDayLayout = findViewById(newMeditationId);
        newDayLayout.setForeground(ContextCompat.getDrawable(this, R.drawable.selected_day_border));

        selectedDay = newDay;
    }

    /**
     * Callback for when the preferences button is pressed
     * @param view The View
     */
    public void preferencesButtonPressed(View view) {
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for when the calender button is pressed
     * @param view The view
     */
    public void weekButtonPressed(View view) {
        // Get screen size
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        // Attempt to create  and display the weekly progress popup
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View pw_view = inflater.inflate(R.layout.activity_check_progress_popup,
                    (ViewGroup) findViewById(R.id.checkProgressPopup));
            popupWindow = new PopupWindow(pw_view,  width, height, true);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);

            setupPopupTextFields(pw_view);

            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override public boolean onTouch(View v, MotionEvent event) {
                    popupWindow.dismiss(); return true;
                }
            });

            popupWindow.showAtLocation(pw_view, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the Text and Image fields on the weekly progress popup
     * @param pw_view The popup view that the fields are on
     */
    private void setupPopupTextFields(View pw_view) {
        int currentWeek = user.getProgramWeek();

        // Go through each week of the program and sets the correct UI
        for(int i = 1; i <= 8; i++) {
            TextView weekTextView = getTextViewForWeek(pw_view, i);
            ImageView weekImageView = getImageViewForWeek(pw_view, i);
            if(i < currentWeek) {
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                weekImageView.setImageResource(R.drawable.check_green_2x);
            } else if(i == currentWeek) {
                weekTextView.setText(R.string.this_week_text);
                weekTextView.setTypeface(null, Typeface.BOLD);
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.black));
                weekImageView.setVisibility(View.GONE);
            } else {
                weekTextView.setTextColor(ContextCompat.getColor(this, R.color.lightGray));
                weekImageView.setImageResource(R.drawable.check_gray_2x);
            }
        }
    }

    /**
     * Callback for when the lesson button is pressed
     * @param view The view
     */
    public void lessonButtonPressed(View view) {
        Intent intent = new Intent(this, LessonActivity.class);
        startActivity(intent);
    }

    // TODO: Remove this after assessment acitivies are done
    // THIS IS A TEMP BUTTON USED TO TEST ASSESSMENT ACTIVITIES
    public void assessmentButtonPressed(View view) {
        Intent intent = new Intent(this, SmokeAssessmentActivity.class);
        startActivity(intent);
    }

    /**
     * Callback for when any weekday is pressed
     * @param view The view
     */
    public void changeWeekdayButtonPressed (View view) {
        // Get the abbreviated weekday from beginning of the id set in layout
        String stringId = view.getResources().getResourceName(view.getId());
        stringId = stringId.substring(0, stringId.length() - "Meditation".length());

        updateSelectedDay(stringId);
    }

    /**
     * Returns the day of the week in the string format used by the day selector
     * @return The day of the week in the following format: su, m, t, w, th, f, s
     */
    private String getCurrentDayOfTheWeek() {
        String day = "";
        switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                day = "su";
                break;
            case Calendar.MONDAY:
                day = "m";
                break;
            case Calendar.TUESDAY:
                day = "t";
                break;
            case Calendar.WEDNESDAY:
                day = "w";
                break;
            case Calendar.THURSDAY:
                day = "th";
                break;
            case Calendar.FRIDAY:
                day = "f";
                break;
            case Calendar.SATURDAY:
                day = "s";
                break;
            default:
                // impossible to get here
        }
        return day;
    }

    /**
     * Helper function that finds the text view for a given week in the given view
     * @param pw_view The popup view that the text field is on
     * @param week The week
     * @return The text view for the given week
     */
    private TextView getTextViewForWeek(View pw_view, int week) {
        TextView weekTextView;
        switch (week){
            case 1:
                weekTextView = (TextView) pw_view.findViewById(R.id.week1Text);
                break;
            case 2:
                weekTextView = (TextView) pw_view.findViewById(R.id.week2Text);
                break;
            case 3:
                weekTextView = (TextView) pw_view.findViewById(R.id.week3Text);
                break;
            case 4:
                weekTextView = (TextView) pw_view.findViewById(R.id.week4Text);
                break;
            case 5:
                weekTextView = (TextView) pw_view.findViewById(R.id.week5Text);
                break;
            case 6:
                weekTextView = (TextView) pw_view.findViewById(R.id.week6Text);
                break;
            case 7:
                weekTextView = (TextView) pw_view.findViewById(R.id.week7Text);
                break;
            case 8:
                weekTextView = (TextView) pw_view.findViewById(R.id.week8Text);
                break;
            default:
                weekTextView = null;
                break;
        }
        return weekTextView;
    }

    /**
     * Helper function that finds the image view for a given week in the given view
     * @param pw_view The popup view that the image field is on
     * @param week The week
     * @return The image view for the given week
     */
    private ImageView getImageViewForWeek(View pw_view, int week) {
        ImageView weekImageView;
        switch (week){
            case 1:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week1Check);
                break;
            case 2:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week2Check);
                break;
            case 3:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week3Check);
                break;
            case 4:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week4Check);
                break;
            case 5:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week5Check);
                break;
            case 6:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week6Check);
                break;
            case 7:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week7Check);
                break;
            case 8:
                weekImageView = (ImageView) pw_view.findViewById(R.id.week8Check);
                break;
            default:
                weekImageView = null;
                break;
        }
        return weekImageView;
    }
}