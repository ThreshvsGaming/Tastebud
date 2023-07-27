package comp3350.g3.tasteBud.logicTest;

import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;
import comp3350.g3.tasteBud.logic.Processors.RecipeProcessor;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class RecipeProcessorIntegrationTest {
    private RecipeProcessor recipeProcessor;
    private IRecipeDB recipeDB;
    private File tempDB;


    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        recipeDB = new RecipeDBPersistence(tempDB.getAbsolutePath().replace(".script", ""));
        recipeProcessor = new RecipeProcessor(recipeDB);
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }

    @Test
    public void testAddRecipe() {
        //Message
        System.out.println("Start Test!");
        // Arrange
        String recipeName = "Test Recipe";
        String recipeDesc = "This is a test recipe";
        List<String> ingredients = Arrays.asList("Ingredient 1");
        String tags = "Tag 1";
        String imageUri = "image_uri";

        // Act
        recipeProcessor.addRecipe(recipeName, recipeDesc, ingredients, tags, imageUri);
        ArrayList<Recipe> allRecipes = recipeDB.getAllRecipes();
        Recipe addedRecipe = allRecipes.get(allRecipes.size() - 1);

        // Assert
        assertEquals(recipeName, addedRecipe.getName());
        assertEquals(recipeDesc, addedRecipe.getDesc());
        assertEquals(ingredients, addedRecipe.getIngredients());
        assertEquals(Arrays.asList("Tag 1"), addedRecipe.getTags());
        assertEquals(imageUri, addedRecipe.getImageUri());
        //Message
        System.out.println("End Test!");
    }

    @Test
    public void testUpdateRecipe() {
        //Message
        System.out.println("Start Test!");
        // Arrange
        Recipe existingRecipe = new Recipe("Existing Recipe", "Old Description", Arrays.asList("Ingredient 2"), "Tag 2");
        recipeDB.addRecipe(existingRecipe);

        int recipeId = existingRecipe.getId();
        String newRecipeName = "Updated Recipe";
        String newRecipeDesc = "New Description";
        List<String> newIngredients = Arrays.asList("New Ingredient 2");
        String newTags = " New Tag 2";
        String newImageUri = "new_image_uri";

        // Act
        recipeProcessor.updateRecipe(recipeId, newRecipeName, newRecipeDesc, newIngredients, newTags, newImageUri);
        Recipe updatedRecipe = recipeDB.getRecipe(recipeId);

        // Assert
        assertEquals(recipeId, updatedRecipe.getId());
        assertEquals(newRecipeName, updatedRecipe.getName());
        assertEquals(newRecipeDesc, updatedRecipe.getDesc());
        assertEquals(newIngredients, updatedRecipe.getIngredients());
        assertEquals(Arrays.asList("New Tag 2"), updatedRecipe.getTags());
        assertEquals(newImageUri, updatedRecipe.getImageUri());
        //Message
        System.out.println("End Test!");
    }

    @Test
    public void testDeleteListOfRecipe() {
        //Message
        System.out.println("Start Test!");
        // Arrange
        Recipe recipe1 = new Recipe("Recipe 1", "Description 1", Arrays.asList("Ingredient 3"), "Tag 3");
        Recipe recipe2 = new Recipe("Recipe 2", "Description 2", Arrays.asList("Ingredient 4"), "Tag 4");
        recipeDB.addRecipe(recipe1);
        recipeDB.addRecipe(recipe2);
        List<Recipe> recipesToDelete = Arrays.asList(recipe1, recipe2);

        // Act
        recipeProcessor.deleteListOfRecipe(recipesToDelete);
        Recipe deletedRecipe1 = recipeDB.getRecipe(recipe1.getId());
        Recipe deletedRecipe2 = recipeDB.getRecipe(recipe2.getId());

        // Assert
        assertNull(deletedRecipe1);
        assertNull(deletedRecipe2);
        //Message
        System.out.println("End Test!");
    }

    @Test
    public void testGetRecipe() {
        //Message
        System.out.println("Start Test!");
        // Arrange
        Recipe recipe = new Recipe("Recipe", "Description", Arrays.asList("Ingredient 5"), "Tag 5");
        recipeDB.addRecipe(recipe);

        int recipeId = recipe.getId();

        // Act
        Recipe retrievedRecipe = recipeProcessor.getRecipe(recipeId);

        // Assert
        assertEquals(recipeId, retrievedRecipe.getId());
        assertEquals(recipe.getName(), retrievedRecipe.getName());
        assertEquals(recipe.getDesc(), retrievedRecipe.getDesc());
        assertEquals(recipe.getIngredients(), retrievedRecipe.getIngredients());
        assertEquals(recipe.getTags(), retrievedRecipe.getTags());
        assertEquals(recipe.getImageUri(), retrievedRecipe.getImageUri());
        //Message
        System.out.println("End Test!");
    }

    @Test
    public void testDeleteRecipe() {
        //Message
        System.out.println("Start Test!");
        // Arrange
        Recipe recipe = new Recipe("Recipe", "Description", Arrays.asList("Ingredient 6"), "Tag 6");
        recipeDB.addRecipe(recipe);

        int recipeId = recipe.getId();

        // Act
        recipeProcessor.deleteRecipe(recipeId);
        Recipe deletedRecipe = recipeDB.getRecipe(recipeId);

        // Assert
        assertNull(deletedRecipe);
        //Message
        System.out.println("End Test!");
    }
}
