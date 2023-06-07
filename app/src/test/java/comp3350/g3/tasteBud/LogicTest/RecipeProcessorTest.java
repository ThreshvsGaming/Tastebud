package comp3350.g3.tasteBud.LogicTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import comp3350.g3.tasteBud.logic.RecipeProcessor;

public class RecipeProcessorTest {
    private RecipeProcessor recipeProcessor;

    @Before
    public void setUp() {
        recipeProcessor = new RecipeProcessor(null);
    }

    @Test
    public void inputValidation_validInputs() {
        String recipeName = "Chicken Curry";
        String recipeInstructions = "Cook chicken with curry sauce.";
        String recipeIngredients = "Chicken, curry sauce";
        String recipeTags = "Dinner, Indian";

        String result = recipeProcessor.inputValidation(recipeName, recipeInstructions, recipeIngredients, recipeTags);

        assertNull(result);
    }

    @Test
    public void inputValidation_emptyName() {
        String recipeName = "";
        String recipeInstructions = "Cook chicken with curry sauce.";
        String recipeIngredients = "Chicken, curry sauce";
        String recipeTags = "Dinner, Indian";

        String result = recipeProcessor.inputValidation(recipeName, recipeInstructions, recipeIngredients, recipeTags);

        assertEquals("Name cannot be empty!!", result);
    }

    @Test
    public void inputValidation_spacesOnlyName() {
        String recipeName = "     ";
        String recipeInstructions = "Cook chicken with curry sauce.";
        String recipeIngredients = "Chicken, curry sauce";
        String recipeTags = "Dinner, Indian";

        String result = recipeProcessor.inputValidation(recipeName, recipeInstructions, recipeIngredients, recipeTags);

        assertEquals("Name cannot be spaces only!!", result);
    }

    @Test
    public void inputValidation_numbersAndSymbolsOnlyName() {
        String recipeName = "12345";
        String recipeInstructions = "Cook chicken with curry sauce.";
        String recipeIngredients = "Chicken, curry sauce";
        String recipeTags = "Dinner, Indian";

        String result = recipeProcessor.inputValidation(recipeName, recipeInstructions, recipeIngredients, recipeTags);

        assertEquals("Name cannot be numbers or symbols only!!", result);
    }
}
