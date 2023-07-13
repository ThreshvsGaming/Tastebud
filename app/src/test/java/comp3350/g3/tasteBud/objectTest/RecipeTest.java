package comp3350.g3.tasteBud.objectTest;
import comp3350.g3.tasteBud.object.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class RecipeTest {
    int recipeId = 1;
    String recipeName = "Fried Chicken";
    String recipeDesc = "Combine dry ingredients. Combine wet ingredients to make a batter. Heat oil to 350 degrees. Dredge chicken and fry for 10 minutes.";
    String[] recipeIngredients = {"Chicken drumsticks", "Chicken", "Buttermilk", "Salt", "Pepper", "Flour", "Corn starch", "Paprika", "Onion powder"};
    String recipeTags = "Dinner, Fried";

    @Before
    public void intro() {
        System.out.println("Conducting test of the recipe object...\n");
    }
    @Test
    public void testCreation() {
        // Tests if constructor works
        Recipe recipe = new Recipe(
                recipeName,
                recipeDesc,
                Arrays.asList(recipeIngredients),
                recipeTags,
                ""
        );
        recipe.setId(recipeId);

        assertEquals(recipeId, recipe.getId());
        assertEquals(recipeName, recipe.getName());
        assertEquals(recipeDesc, recipe.getDesc());
        // New arrays to see if constructor copied parameter arrays properly
        assertArrayEquals(new String[]{"Chicken drumsticks", "Chicken", "Buttermilk", "Salt", "Pepper", "Flour", "Corn starch", "Paprika", "Onion powder"}, recipe.getIngredients().toArray());
        assertArrayEquals(new String[]{"Dinner", "Fried"}, recipe.getTags().toArray());
    }

    @Test
    public void testDefaultConstructor() {
        // Tests if alternative constructor with a default id works
        Recipe recipe = new Recipe(
                recipeName,
                recipeDesc,
                Collections.singletonList(recipeIngredients),
                recipeTags,
                ""
        );

        // This tests if an object is given the default ID (-1) if not specified an ID
        assertEquals(-1, recipe.getId());
    }

    @Test
    public void testEmptyIngredients() {
        // While we do not allow the user to create a recipe without ingredients for better HCI, creating such an object directly should not fail.
        String[] recipeIngredientsEmpty = {};
        // Tests if constructor works
        Recipe recipe = new Recipe(
                recipeName,
                recipeDesc,
                Collections.singletonList(recipeIngredientsEmpty),
                recipeTags
        );
        recipe.setId(recipeId);

        assertTrue(recipe.getIngredients().size() == 0);
    }

    @Test
    public void testEmptyTags() {
        // Same for tags.
        String[] recipeTagsEmpty = {};

        // Tests if constructor works
        Recipe recipe = new Recipe(
                recipeName,
                recipeDesc,
                Collections.singletonList(recipeIngredients),
                ""
        );
        recipe.setId(recipeId);

        assertTrue(recipe.getTags().size() == 0);
    }

    @After
    public void runTest() {
        System.out.println("Test of Recipe object complete.");
    }

}
