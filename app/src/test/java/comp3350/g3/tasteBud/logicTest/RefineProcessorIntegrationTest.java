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
        refineProcessor = new RefineProcessor(true);
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


            Recipe recipe1 = new Recipe("Recipe 1", "", new LinkedList<>(), "tag1");
            Recipe recipe2 = new Recipe("Recipe 2", "", new LinkedList<>(), "tag2");
            Recipe recipe3 = new Recipe("Recipe 3", "", new LinkedList<>(), "tag3");
            Recipe recipe4 = new Recipe("Recipe 4", "", new LinkedList<>(), "tag4");

            recipeDB.addRecipe(recipe1);
            recipeDB.addRecipe(recipe2);
            recipeDB.addRecipe(recipe3);
            recipeDB.addRecipe(recipe4);


            String[] expectedTags = {"Cookies","Pasta","Asian","tag1", "tag2", "tag3", "tag4"};

            String[] tagList = refineProcessor.getTagList();

            assertEquals(expectedTags.length, tagList.length);
            System.out.println(expectedTags.length);
            System.out.println(tagList.length);
            System.out.println(Arrays.asList(tagList));
            System.out.println(Arrays.asList(expectedTags));
            assertTrue(Arrays.asList(tagList).containsAll(Arrays.asList(expectedTags)));

        //Messages
        System.out.println("End Doing GetTagList Test!");
    }





}


