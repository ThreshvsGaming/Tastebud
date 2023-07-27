package comp3350.g3.tasteBud.logicTest;

import static org.junit.Assert.*;
import org.junit.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;
//import comp3350.g3.tasteBud.logic.RecommendProcessor;
import comp3350.g3.tasteBud.logic.Processors.RecommendProcessor;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.utils.TestUtils;

public class RecommendProcessorIntegrationTest {

    private RecommendProcessor recommendProcessor;
    private IRecipeDB recipeDB;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        recipeDB = new RecipeDBPersistence(tempDB.getAbsolutePath().replace(".script", ""));
        recommendProcessor = new RecommendProcessor(recipeDB);
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }

    @Test
    public void testConstructSelectedIngredients() {
        // Test the constructSelectedIngredients method
        String ingredientList = "Ingredient1, Ingredient2, Ingredient3";
        String[] result = recommendProcessor.constructSelectedIngredients(ingredientList);

        String[] expected = {"Ingredient1", "Ingredient2", "Ingredient3"};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testGetIngredientList() {
        // Test the getIngredientList method
        String[] result = recommendProcessor.getIngredientList();

        String[] expected = {"flour", "spaghetti", "chicken"};
        Arrays.sort(result);
        Arrays.sort(expected);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchAssemblableRecipes() {
        // Test the searchAssemblableRecipes method
        String ingredientListString = "flour, spaghetti";
        List<Recipe> matchedRecipes = recommendProcessor.searchAssemblableRecipes(ingredientListString);

        // There should be 2 recipes that match the given ingredients: Cookies and Spaghetti
        assertEquals(2, matchedRecipes.size());
        assertEquals("Cookies", matchedRecipes.get(0).getName());
        assertEquals("Spaghetti", matchedRecipes.get(1).getName());
    }
}
