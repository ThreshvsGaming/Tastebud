package comp3350.g3.tasteBud.logicTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.logic.RefineProcessor;
import comp3350.g3.tasteBud.object.Recipe;

public class RefineProcessorTest {

    private RecipeStub recipeStub;
    private RefineProcessor refineProcessor;

    @Before
    public void setup() {
        //Sets up 4 recipes to test the Search Processor:
        // 1) Fried Chicken
        // 2) Chicken Adobo
        // 3) Kacchi Biryani
        // 4) Crispy Calamari
        recipeStub = new RecipeStub();
        recipeStub.initRecipeDatabase();
        refineProcessor = new RefineProcessor(recipeStub);
    }

    @Test
    public void testGetAllTagsFromDatabase() {
        String[] allTags = {"Dinner", "Fried", "Lunch", "Filipino", "Indian", "Appetizer", "Seafood"};

        String[] results = refineProcessor.getTagList();

        Arrays.sort(allTags);
        Arrays.sort(results);

        assertTrue(Arrays.equals(allTags, results));

        //Check if it still works on a empty database
        List<Recipe> list = recipeStub.getAllRecipes();
        List<Recipe> copyList = new ArrayList<>(list);
        for (Recipe r : copyList) {
            recipeStub.deleteRecipe(r.getId());
        }

        results = refineProcessor.getTagList();

        assertTrue(results.length == 0);


    }

    @Test
    public void testConstructSelectedTags() {
        String tags = "AJ, is     , the ,       best, right,       ?     ";
        String[] tagsList = {"AJ", "is", "the", "best", "right", "?"};

        String[] result = refineProcessor.constructSelectedTags(tags);

        for (String s : result) {
            System.out.print(s);
        }
        assertTrue(Arrays.equals(tagsList, result));
    }


}
