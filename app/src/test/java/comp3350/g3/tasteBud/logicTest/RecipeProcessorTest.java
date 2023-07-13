package comp3350.g3.tasteBud.logicTest;

import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.object.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;


public class RecipeProcessorTest {
    RecipeStub testStub = new RecipeStub();
    RecipeProcessor recipeProcessor = new RecipeProcessor(testStub);

    String recipeIngredients = "Bread,vienna sausage,scrambled egg,cheese";
    String[] recipeIngredientsArray = recipeIngredients.split(",");
    String recipeTags = "Lunch,Bad food";
    String[] recipeTagsArray = recipeTags.split(",");
    String recipeName = "Prison food";
    String recipeDesc = "Place vienna sausages and scrambled eggs on sliced bread. Top with another slice of bread and unmelted cheese.";
    String[] inputFields = {"Name", "Description", "Ingredients", "Tags"};
    String[] parameters = {recipeName, recipeDesc, recipeIngredients, recipeTags};

    @Before
    public void intro() {
        System.out.println("Conducting test of the recipe processor...");
    }

    @Test
    public void testAdding() {
        // Tests that the processor adds to the database
        testStub.initRecipeDatabase();
        int checkSize = testStub.getAllRecipes().size();

        recipeProcessor.addRecipe(recipeName, recipeDesc, Arrays.asList(recipeIngredients.split(",")), recipeTags, "");

        // Asserts that the size of database changed
        assertEquals(testStub.getAllRecipes().size(), checkSize + 1);

        // Asserts that the recipe was created properly
        Recipe storedRecipe = testStub.getAllRecipes().get(checkSize);

        assertEquals(storedRecipe.getName(), recipeName);
        assertEquals(storedRecipe.getDesc(), recipeDesc);
        assertArrayEquals(storedRecipe.getIngredients().toArray(), recipeIngredientsArray);
        assertArrayEquals(storedRecipe.getTags().toArray(), recipeTagsArray);
    }

    @After
    public void end() {
        System.out.println("Finished test of recipe processor.");
    }
}
