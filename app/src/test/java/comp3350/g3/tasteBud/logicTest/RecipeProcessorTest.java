package comp3350.g3.tasteBud.logicTest;

import comp3350.g3.tasteBud.data.StubDatabase.RecipeStub;
import comp3350.g3.tasteBud.logic.Processors.RecipeProcessor;
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
        testStub.initRecipeDatabase();
        System.out.println("Conducting test of the recipe processor...");
    }

    @Test
    public void testAdding() {
        // Tests that the processor adds to the database
        int checkSize = testStub.getAllRecipes().size();

        recipeProcessor.addRecipe(recipeName, recipeDesc, Arrays.asList(recipeIngredients.split(",")), recipeTags, "");

        // Asserts that the size of database changed
        assertEquals(checkSize + 1, testStub.getAllRecipes().size());

        // Asserts that the recipe was created properly
        Recipe storedRecipe = testStub.getAllRecipes().get(checkSize);

        assertEquals(storedRecipe.getName(), recipeName);
        assertEquals(storedRecipe.getDesc(), recipeDesc);
        assertArrayEquals(storedRecipe.getIngredients().toArray(), recipeIngredientsArray);
        assertArrayEquals(storedRecipe.getTags().toArray(), recipeTagsArray);
    }

    @Test
    public void testDeleting() {
        // Tests that the processor deletes recipes from database
        Recipe recipe = new Recipe(recipeName, recipeDesc, Arrays.asList(recipeIngredients.split(",")), recipeTags, "");
        testStub.addRecipe(recipe);
        int checkSize = testStub.getAllRecipes().size();

        recipeProcessor.deleteRecipe(recipe.getId());

        // Asserts that the size of database changed
        assertEquals(testStub.getAllRecipes().size(), checkSize - 1);

        // Asserts that the recipe was created properly
        for (Recipe r : testStub.getAllRecipes()) {
            assertTrue(r.getId() != recipe.getId());
        }
    }

    @After
    public void end() {
        System.out.println("Finished test of recipe processor.");
    }
}
