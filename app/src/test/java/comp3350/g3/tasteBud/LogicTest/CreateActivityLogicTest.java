package comp3350.g3.tasteBud.LogicTest;

import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.logic.CreateActivityLogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest=Config.NONE)
public class CreateActivityLogicTest {
    @Mock
    private TextView validationStatus;
    @Mock
    private RecipeStub database;
    @Mock
    private Handler handler;
    @Mock
    private View mockView;
    @Mock
    private EditText recipeTitleEditText;
    @Mock
    private EditText recipeDescriptionEditText;
    @Mock
    private EditText recipeIngredientsEditText;
    @Mock
    private EditText recipeTagsEditText;

    private CreateActivityLogic createActivityLogic;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        createActivityLogic = new CreateActivityLogic(validationStatus);
        createActivityLogic.setDatabase(database);
        createActivityLogic.setHandler(handler);
    }

    @Test
    public void submitRecipe_validInputs() {
        assertTrue(createActivityLogic.inputValidation("Test Recipe", "Test Description", "Test Ingredient", "Test Tag"));
    }

    @Test
    public void submitRecipe_emptyName() {
        assertFalse(createActivityLogic.inputValidation("", "Test Description", "Test Ingredient", "Test Tag"));
    }

    @Test
    public void submitRecipe_spacesOnlyName() {
        assertFalse(createActivityLogic.inputValidation("    ", "Test Description", "Test Ingredient", "Test Tag"));
    }

    @Test
    public void submitRecipe_numbersAndSymbolsOnlyName() {
        assertTrue(createActivityLogic.inputValidation("123$%^", "Test Description", "Test Ingredient", "Test Tag"));
    }

    @Test
    public void submitRecipe_emptyDescription() {
        assertFalse(createActivityLogic.inputValidation("Test Recipe", "", "Test Ingredient", "Test Tag"));
    }

    @Test
    public void submitRecipe_numbersAndSymbolsOnlyDescription() {
        assertTrue(createActivityLogic.inputValidation("Test Recipe", "123$%^", "Test Ingredient", "Test Tag"));
    }

    @Test
    public void submitRecipe_spacesOnlyDescription() {
        assertFalse(createActivityLogic.inputValidation("Test Recipe", "    ", "Test Ingredient", "Test Tag"));
    }
}
