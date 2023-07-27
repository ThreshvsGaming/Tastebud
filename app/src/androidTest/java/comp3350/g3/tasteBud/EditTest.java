package comp3350.g3.tasteBud;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.g3.tasteBud.ui.Activities.MainActivity;


@RunWith(AndroidJUnit4.class)
public class EditTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setup() {
        // Navigate to the EditActivity
        TestUtils.navigateToCreateActivity();
    }

    @Test
    public void testEditRecipe() {
        Espresso.onView(ViewMatchers.withId(R.id.ivCreateButton)).perform(ViewActions.click());
        // Fill in the recipe details
        Espresso.onView(ViewMatchers.withId(R.id.recipeTitle)).perform(ViewActions.replaceText("Test Recipe2"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeDescription)).perform(ViewActions.replaceText("This is Test Recipe2"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeIngredients)).perform(ViewActions.replaceText("Test Ingredient2"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeTags)).perform(ViewActions.replaceText("Test Tag2"));

        // Click on the "Submit" button
        Espresso.onView(ViewMatchers.withId(R.id.recipeSubmit)).perform(ViewActions.click());

        // Check if the validation status is displayed and showing success message
        Espresso.onView(ViewMatchers.withId(R.id.textView2))
                .check(matches(ViewMatchers.isDisplayed()))
                .check(matches(withText("Recipe Successfully Added!")));
        TestUtils.sleep(1000);

        Espresso.onView(ViewMatchers.withId(R.id.ivSearchButton)).perform(ViewActions.click());

        Espresso.onView(withText("Test Recipe2")).perform(ViewActions.click());

        // Perform the Edit action
        Espresso.onView(ViewMatchers.withId(R.id.edit)).perform(ViewActions.click());

        // Verify that the EditActivity is displayed
        Espresso.onView(ViewMatchers.withId(R.id.recipeTitle)).check(matches(ViewMatchers.isDisplayed()));

        // Modify the recipe details
        Espresso.onView(ViewMatchers.withId(R.id.recipeTitle)).perform(ViewActions.replaceText("Modified Test Recipe"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeDescription)).perform(ViewActions.replaceText("This is a modified Test Recipe"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeIngredients)).perform(ViewActions.replaceText("Modified Test Ingredient"));
        Espresso.onView(ViewMatchers.withId(R.id.recipeTags)).perform(ViewActions.replaceText("Modified Test Tag"));

        // Click on the "Submit" button to save the changes
        Espresso.onView(ViewMatchers.withId(R.id.recipeSubmit)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.ivBack)).perform(ViewActions.click());
        Espresso.onView(withText("Modified Test Recipe")).perform(ViewActions.longClick());
        Espresso.onView(ViewMatchers.withId(R.id.delete)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("YES"))
                .perform(ViewActions.click());


    }
}
