package comp3350.g3.tasteBud.logicTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import comp3350.g3.tasteBud.logic.Processors.RecipeSelectionProcessor;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipeSelectionProcessorTest {

    @Mock
    private List<Recipe> recipeListMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSelectedItems_NoSelections() {
        // Arrange
        RecipeSelectionProcessor processor = new RecipeSelectionProcessor(3);
        List<Recipe> expectedSelectedItems = new ArrayList<>();

        // Act
        List<Recipe> selectedItems = processor.getSelectedItems(recipeListMock);

        // Assert
        assertEquals(expectedSelectedItems, selectedItems);
    }

    @Test
    public void testGetSelectedItems_WithSelections() {
        // Arrange
        Recipe recipe1 = new Recipe("Recipe 1", "Description 1", new ArrayList<>());
        Recipe recipe2 = new Recipe("Recipe 2", "Description 2", new ArrayList<>());
        Recipe recipe3 = new Recipe("Recipe 3", "Description 3", new ArrayList<>());
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);

        RecipeSelectionProcessor processor = new RecipeSelectionProcessor(3);
        processor.setSelection(0, true); // Select recipe1
        processor.setSelection(2, true); // Select recipe3

        List<Recipe> expectedSelectedItems = new ArrayList<>();
        expectedSelectedItems.add(recipe1);
        expectedSelectedItems.add(recipe3);

        // Act
        List<Recipe> selectedItems = processor.getSelectedItems(recipes);

        // Assert
        assertEquals(expectedSelectedItems, selectedItems);
    }

    @Test
    public void testGetSelectedItems_SelectionSizeMismatch() {
        // Arrange
        RecipeSelectionProcessor processor = new RecipeSelectionProcessor(3);
        processor.setSelection(0, true); // Select recipe1
        processor.setSelection(1, true); // Select recipe2

        List<Recipe> recipes = new ArrayList<>();

        // Act
        List<Recipe> selectedItems = processor.getSelectedItems(recipes);

        // Assert
        assertEquals(0, selectedItems.size());
    }

    // Add more test cases to cover other scenarios and edge cases as needed.
}
