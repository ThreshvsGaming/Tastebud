package comp3350.g3.tasteBud.logicTest;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.logic.Processors.RecommendProcessor;
import comp3350.g3.tasteBud.object.Recipe;

public class RecommendProcessorTest {
    private RecommendProcessor recommendProcessor;
    private IRecipeDB mockRecipeDB;

    @Before
    public void setUp() {
            // Create a mock for IRecipeDB
            mockRecipeDB = mock(IRecipeDB.class);
            recommendProcessor = new RecommendProcessor(mockRecipeDB);

            // Set up mock data for IRecipeDB
            Recipe recipe1 = new Recipe("Cookies", "Recipe for delicious cookies", Arrays.asList("flour"), "dessert");
            Recipe recipe2 = new Recipe("Spaghetti", "Classic spaghetti recipe", Arrays.asList("spaghetti"), "pasta");
            ArrayList<Recipe> mockRecipes = new ArrayList<>(Arrays.asList(recipe1, recipe2));

            // Mock the behavior of IRecipeDB to return the mockRecipes
            when(mockRecipeDB.getAllRecipes()).thenReturn(mockRecipes);
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

        String[] expected = {"flour","spaghetti"};
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
