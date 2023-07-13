package comp3350.g3.tasteBud.logicTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;
import comp3350.g3.tasteBud.logic.SearchProcessor;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.utils.TestUtils;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class SearchProcessorIntegrationTest {

    private SearchProcessor searchProcessor;
    private IRecipeDB recipeDB;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        //Sets up 4 recipes to test the Search Processor:
        // 1) Fried Chicken
        // 2) Chicken Adobo
        // 3) Kacchi Biryani
        // 4) Crispy Calamari
        tempDB = TestUtils.copyDB();
        recipeDB = new RecipeDBPersistence(tempDB.getAbsolutePath().replace(".script", ""));
        removeAllRecipes();
        defaultRecipe();
        searchProcessor = new SearchProcessor(true);
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
        assertEquals("Fried Chicken", recipe2.getName());
        assertEquals("Chicken Adobo", recipe1.getName());

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
        System.out.println(searchResults.size());

        assertTrue(searchResults.size() == 1);

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

        recipeDB.addRecipe(newRecipe);

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
        System.out.println(searchResults.size());

        assertTrue(searchResults.size() == 5);

        //Make sure spaces doesn't filter the list
        searchResults = searchProcessor.searchResults("                 ");

        assertTrue(searchResults.size() == 5);
    }
    @Test
    public void testSearchAndTag(){

    }
    @After
    public void end() {
        System.out.println("Finish Test on SearchProcessor");
    }

    private void removeAllRecipes() {
        List<Recipe> list = recipeDB.getAllRecipes();
        for(Recipe r:list) {
            recipeDB.deleteRecipe(r.getId());
        }
    }
    private void defaultRecipe(){
        ArrayList<String> recipe1ingredients = new ArrayList<>(Arrays.asList("Chicken drumsticks", "Chicken", "Buttermilk", "Salt", "Pepper", "Flour", "Corn starch", "Paprika", "Onion powder"));
        String recipe1tags = "Dinner, Fried";
        ArrayList<String> recipe2ingredients = new ArrayList<>(Arrays.asList("Chicken thighs", "Soy sauce", "Vinegar", "Garlic", "Bay leaves", "Peppercorns", "Brown sugar"));
        String recipe2tags = "Lunch, Dinner, Filipino";
        ArrayList<String> recipe3ingredients = new ArrayList<>(Arrays.asList("Basmati rice", "Marinated lamb", "Yogurt", "Onions", "Ginger", "Garlic", "Green chilies", "Saffron", "Ghee", "Whole spices"));
        String recipe3tags = "Dinner, Indian";
        ArrayList<String> recipe4ingredients = new ArrayList<>(Arrays.asList("Calamari", "Flour", "Cornmeal", "Paprika", "Salt", "Pepper", "Garlic powder", "Egg", "Milk", "Oil"));
        String recipe4tags = "Appetizer, Seafood, Fried";
        Recipe recipe1 = new Recipe(
                "Fried Chicken",
                "Combine dry ingredients. Combine wet ingredients to make a batter. Heat oil to 350 degrees. Dredge chicken and fry for 10 minutes.",
                recipe1ingredients,
                recipe1tags
        );
        Recipe recipe2 = new Recipe(
                "Chicken Adobo",
                "Combine soy sauce, vinegar, garlic, bay leaves, peppercorns, and brown sugar. Marinate chicken in the mixture for 1 hour. Cook chicken in the marinade until tender.",
                recipe2ingredients,
                recipe2tags
        );
        Recipe recipe3 = new Recipe(
                "Kacchi Biryani",
                "Soak basmati rice. Marinate lamb with yogurt and spices. Layer rice and marinated lamb in a pot. Add saffron and ghee. Cook on low heat until rice and lamb are tender.",
                recipe3ingredients,
                recipe3tags
        );
        Recipe recipe4 = new Recipe(
                "Crispy Calamari",
                "Slice calamari into rings. In a bowl, mix flour, cornmeal, paprika, salt, pepper, and garlic powder. In another bowl, whisk egg and milk. Dip calamari rings in the egg mixture, then coat them in the flour mixture. Fry in oil until golden brown and crispy.",
                recipe4ingredients,
                recipe4tags
        );


        recipeDB.addRecipe(recipe1);
        recipeDB.addRecipe(recipe2);
        recipeDB.addRecipe(recipe3);
        recipeDB.addRecipe(recipe4);
    }

}