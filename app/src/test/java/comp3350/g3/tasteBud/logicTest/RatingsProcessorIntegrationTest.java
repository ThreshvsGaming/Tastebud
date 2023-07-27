package comp3350.g3.tasteBud.logicTest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

//import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;
//import comp3350.g3.tasteBud.logic.RatingsProcessor;
import comp3350.g3.tasteBud.logic.Processors.RatingsProcessor;
import comp3350.g3.tasteBud.object.Ratings;
//import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.utils.TestUtils;

public class RatingsProcessorIntegrationTest {
    private RatingsProcessor ratingsProcessor;
    private IRecipeDB recipeDB;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        recipeDB = new RecipeDBPersistence(tempDB.getAbsolutePath().replace(".script", ""));
        ratingsProcessor = new RatingsProcessor(recipeDB);
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }

    @Test
    public void testAddRatings() {
        // Add a recipe to the test database
        int recipeID = 1;
        Ratings ratings = new Ratings();
        ratings.setRecipeRatings(5);

        // Add ratings to the recipe
        ratingsProcessor.addRatings(recipeID, ratings);

        // Verify that the ratings were added correctly
        assertEquals(ratings.getRecipeRatings(), ratingsProcessor.getRating(recipeID));
    }

    @Test
    public void testGetRating() {
        // Add a recipe to the test database
        int recipeID = 1;
        Ratings ratings = new Ratings();
        ratings.setRecipeRatings(4);

        // Add ratings to the recipe
        ratingsProcessor.addRatings(recipeID, ratings);

        // Get the ratings for the recipe
        int retrievedRating = ratingsProcessor.getRating(recipeID);

        // Verify that the retrieved ratings match the added ratings
        assertEquals(ratings.getRecipeRatings(), retrievedRating);
    }
}
