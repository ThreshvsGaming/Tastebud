package comp3350.g3.tasteBud;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.g3.tasteBud.ui.Activities.MainActivity;


@RunWith(AndroidJUnit4.class)
public class SearchTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCreateRecipe() {
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
        Espresso.pressBack();
        Espresso.pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.searchView)).perform(ViewActions.typeText("Test Recipe"));
        Espresso.onView(ViewMatchers.withId(R.id.ivSearchButton)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Test Recipe"))
                .perform(ViewActions.click());
        Espresso.pressBack();
        Espresso.onView(withText("Test Recipe")).perform(ViewActions.longClick());
        Espresso.onView(ViewMatchers.withId(R.id.delete)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("YES"))
                .perform(ViewActions.click());


    }


}


