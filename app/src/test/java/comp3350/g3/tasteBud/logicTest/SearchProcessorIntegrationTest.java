package comp3350.g3.tasteBud.logicTest;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;
import comp3350.g3.tasteBud.logic.RefineProcessor;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.logic.SearchProcessor;
import comp3350.g3.tasteBud.utils.TestUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SearchProcessorIntegrationTest {
    private SearchProcessor searchProcessor;
    private IRecipeDB recipeDB;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        tempDB = TestUtils.copyDB();
        recipeDB = new RecipeDBPersistence(tempDB.getAbsolutePath().replace(".script", ""));
        searchProcessor = new SearchProcessor(true);
    }

    @After
    public void tearDown() {
        tempDB.delete();
    }

    @Test
    public void testSearchResultsWithTag() {
        String searchText = "chicken";
        String[] tags = {"chicken"};

        List<Recipe> results = searchProcessor.searchResultsWithTag(tags, searchText);

        // Assert the number of search results
        assertEquals(1, results.size());

        // Assert the name of the search result
        assertEquals("Chicken Soup", results.get(0).getName());
    }
}
