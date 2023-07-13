package comp3350.g3.tasteBud.logicTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.logic.SearchProcessor;
import comp3350.g3.tasteBud.object.Recipe;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class SearchProcessorTest {
    private RecipeStub recipeStub;
    private SearchProcessor searchProcessor;

    @Before
    public void setUp() {
        //Sets up 4 recipes to test the Search Processor:
        // 1) Fried Chicken
        // 2) Chicken Adobo
        // 3) Kacchi Biryani
        // 4) Crispy Calamari
        recipeStub = new RecipeStub();
        recipeStub.initRecipeDatabase();
        searchProcessor = new SearchProcessor(recipeStub);
    }

    //Test a generic search to see if it returns the correct results
    @Test
    public void testSearchName() {
        //Does a generic search of chicken

        List<Recipe> searchResults = searchProcessor.searchResults("cHicKeN");

        //Searching for "cHicKN" will result in 2 recipes: Chicken Adobo and Fried Chicken
        assertTrue(searchResults.size() == 2);

        Recipe recipe1 = searchResults.get(0);
        Recipe recipe2 = searchResults.get(1);
        assertEquals("Fried Chicken", recipe1.getName());
        assertEquals("Chicken Adobo", recipe2.getName());

        //Specific Search for Chicken Adobo
        searchResults = searchProcessor.searchResults("chicken                  ADOBO");

        assertTrue(searchResults.size() == 1);

        Recipe recipe3 = searchResults.get(0);
        assertEquals("Chicken Adobo", recipe3.getName());
    }

    //Test whether the Search Logic can return the correct recipe no matter the order of the inputted query
    //Example: "Chicken Adobo" and "Adobo Chicken" should return the same recipe
    @Test
    public void testSearchQueryOrder() {
        //Checks one way to query the search
        List<Recipe> searchResults = searchProcessor.searchResults("KacChi BIRYANI");

        assertTrue(searchResults.size() == 1);
        Recipe recipe1 = searchResults.get(0);
        assertEquals("Kacchi Biryani", recipe1.getName());

        //Checks another way to query the search
        searchResults = searchProcessor.searchResults("BIRYANI KacChi");

        assertTrue(searchResults.size() == 1);
        assertTrue(searchResults.contains(recipe1));
    }
    //Test whether the search logic will return newly added recipes
    @Test
    public void testSearchNewRecipes() {
        List<Recipe> searchResults = searchProcessor.searchResults("Test Recipe");

        //Checks if Test Recipe is not in the search
        assertTrue(searchResults.size() == 0);

        Recipe newRecipe = new Recipe(
                "Test Recipe",
                "Test Description",
                Arrays.asList("Ingredient1", "Ingredient2"),
                "test"
        );

        recipeStub.addRecipe(newRecipe);

        searchResults = searchProcessor.searchResults("Test Recipe");

        //Checks if Test Recipe is in the search after adding the recipe to the database
        assertTrue(searchResults.size() == 1);
        Recipe recipe1 = searchResults.get(0);
        assertEquals("Test Recipe", recipe1.getName());
    }

    //Test whether an empty inputted search query will return all the recipes in the database
    @Test
    public void testSearchEmptyQuery() {

        //Make empty query doesn't filter the list
        List<Recipe> searchResults = searchProcessor.searchResults("");

        assertTrue(searchResults.size() == 4);

        //Make sure spaces doesn't filter the list
        searchResults = searchProcessor.searchResults("                 ");

        assertTrue(searchResults.size() == 4);
    }
    @Test
    public void testSearchAndTag(){
        String[] tags = {"Indian", "Dinner"};

        List<Recipe> searchResults = searchProcessor.searchResultsWithTag(tags, "");

        System.out.println(searchResults.size());
        assertTrue(searchResults.size() == 3);

        searchResults = searchProcessor.searchResultsWithTag(tags, "Biryani");

        assertTrue(searchResults.size() == 1);
        assertTrue(searchResults.get(0).getName() == "Kacchi Biryani");

        tags = new String[]{"Tag Doesn't exist", "Tag Doesn't exist 2"};

        searchResults = searchProcessor.searchResultsWithTag(tags, "");

        assertTrue(searchResults.size() == 0);

    }
    @After
    public void end() {

        System.out.println("Finish Test on SearchProcessor");
    }
}
