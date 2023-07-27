package comp3350.g3.tasteBud;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;

import android.widget.ImageView;

import comp3350.g3.tasteBud.ui.Activities.MainActivity;

@RunWith(AndroidJUnit4.class)
public class CreateTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCreateRecipe() throws InterruptedException {
        //move from Main Page to Create Page
        Espresso.onView(ViewMatchers.withId(R.id.ivCreateButton)).perform(ViewActions.click());
        // Check if the CreateActivity is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recipeTitle)).check(matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.recipeTitle)).perform(ViewActions.typeText("Test Recipe"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeDescription)).perform(ViewActions.typeText("This is Test Recipe"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeIngredients)).perform(ViewActions.typeText("Test Ingredient"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeTags)).perform(ViewActions.typeText("Test Tag"));

        // Click on the "Submit" button
        Espresso.onView(ViewMatchers.withId(R.id.recipeSubmit)).perform(ViewActions.click());

        // Check if the validation status is displayed and showing success message
        Espresso.onView(ViewMatchers.withId(R.id.textView2))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(ViewMatchers.withText("Recipe Successfully Added!")));
        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());
        Espresso.pressBack();
        Espresso.onView(withText("Test Recipe")).perform(ViewActions.longClick());
        Espresso.onView(ViewMatchers.withId(R.id.delete)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("YES"))
                .perform(ViewActions.click());
    }

    private static ViewInteraction onViewWithDrawable(int drawableResId) {
        return Espresso.onView(allOf(isAssignableFrom(ImageView.class), withDrawable(drawableResId)));
    }

    private static Matcher<Object> withDrawable(final int drawableResId) {
        return new TypeSafeMatcher<Object>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(Object item) {
                return false;
            }

        };
    }
}
