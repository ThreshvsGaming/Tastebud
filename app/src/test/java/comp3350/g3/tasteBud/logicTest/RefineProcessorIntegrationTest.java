package comp3350.g3.tasteBud.logicTest;

import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;
import comp3350.g3.tasteBud.logic.RefineProcessor;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RefineProcessorIntegrationTest {
    private RefineProcessor refineProcessor;
    private IRecipeDB recipeDB;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        recipeDB = new RecipeDBPersistence(tempDB.getAbsolutePath().replace(".script", ""));
        removeAllRecipes();
        defaultRecipe();
        refineProcessor = new RefineProcessor(recipeDB);
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }

    @Test
    public void testConstructSelectedTags() {
        //Messages
        System.out.println("Start Doing ConstructSelectedTags Test!");
        String tagList = "tag1,tag2,tag3,tag4";
        String[] expectedTags = {"tag1", "tag2", "tag3", "tag4"};

        String[] selectedTags = refineProcessor.constructSelectedTags(tagList);

        assertEquals(expectedTags.length, selectedTags.length);
        System.out.println(Arrays.asList(tagList));
        System.out.println(Arrays.asList(expectedTags));
        assertTrue(Arrays.asList(selectedTags).containsAll(Arrays.asList(expectedTags)));
        //Messages
        System.out.println("End Doing ConstructSelectedTags Test!");
    }

    @Test
    public void testGetTagList() {
        //Messages
        System.out.println("Start Doing GetTagList Test!");

            String[] expectedTags = {"Filipino","Fried","Appetizer", "Dinner", "Seafood", "Indian", "Lunch"};

            String[] tagList = refineProcessor.getTagList();

            assertEquals(expectedTags.length, tagList.length);
            System.out.println(expectedTags.length);
            System.out.println(tagList.length);
            System.out.println("TagList" + " " + Arrays.asList(tagList));
            System.out.println(Arrays.asList(expectedTags));
            assertTrue(Arrays.asList(tagList).containsAll(Arrays.asList(expectedTags)));

        //Messages
        System.out.println("End Doing GetTagList Test!");
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


