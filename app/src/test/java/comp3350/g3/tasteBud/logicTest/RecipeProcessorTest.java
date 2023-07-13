package comp3350.g3.tasteBud.logicTest;

import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.object.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

        recipeProcessor.addRecipe(recipeName, recipeDesc, Arrays.asList(recipeIngredientsArray), recipeTags, "");

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
        Recipe recipe = new Recipe(recipeName, recipeDesc, Arrays.asList(Arrays.asList(recipeIngredientsArray)), recipeTags, "");
        testStub.addRecipe(recipe);
        int checkSize = testStub.getAllRecipes().size();

        recipeProcessor.deleteRecipe(recipe.getId());

        // Asserts that the size of database changed
        assertEquals(testStub.getAllRecipes().size(), checkSize - 1);

        // Asserts that the recipe was created properly
        for(Recipe r : testStub.getAllRecipes())
        {
            assertTrue(r.getId() != recipe.getId());
        }
    }

    @Test
    public void testUpdating() {
        Recipe recipe = new Recipe(recipeName, recipeDesc, Arrays.asList(recipeIngredientsArray), recipeTags, "");
        testStub.addRecipe(recipe);

        recipeProcessor.updateRecipe(recipe.getId(), "Updated", "Updated", Arrays.asList(recipeIngredientsArray), recipeTags, "");

        // Tests that the name was updated
        assertTrue(testStub.getRecipe(recipe.getId()).getName().equals("Updated"));
    }

    @Test
    public void testDeleteList() {
        Recipe recipe1 = new Recipe("recipe1", recipeDesc, Arrays.asList(recipeIngredientsArray), recipeTags, "");
        Recipe recipe2 = new Recipe("recipe2", recipeDesc, Arrays.asList(recipeIngredientsArray), recipeTags, "");
        Recipe recipe3 = new Recipe("recipe3", recipeDesc, Arrays.asList(recipeIngredientsArray), recipeTags, "");
        Recipe recipe4 = new Recipe("recipe4", recipeDesc, Arrays.asList(recipeIngredientsArray), recipeTags, "");

        testStub.addRecipe(recipe1);
        testStub.addRecipe(recipe2);
        testStub.addRecipe(recipe3);
        testStub.addRecipe(recipe4);

        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();

        recipeList.add(recipe1);
        recipeList.add(recipe2);
        recipeList.add(recipe3);
        recipeList.add(recipe4);

        recipeProcessor.deleteListOfRecipe(recipeList);

        // Tests if list is deleted from the stub
        for (Recipe r : testStub.getAllRecipes()) {
            // this test runs n^2 deal with it B)
            for (int i = 0; i < 4; i++) {
                assertTrue(r.getId() != recipeList.get(i).getId());
            }
        }
    }

    @After
    public void end() {
        System.out.println("Finished test of recipe processor.");
    }
}
