package comp3350.g3.tasteBud.logicTest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Arrays;


import comp3350.g3.tasteBud.logic.Validators.RecipeValidator;

public class RecipeValidatorTest {
    RecipeValidator recipeValidator = new RecipeValidator();

    String recipeIngredients = "Bread,vienna sausage,scrambled egg,cheese";
    String[] recipeIngredientsArray = recipeIngredients.split(",");
    String recipeTags = "Lunch,Bad food";
    String[] recipeTagsArray = recipeTags.split(",");
    String recipeName = "Prison food";
    String recipeDesc = "Place vienna sausages and scrambled eggs on sliced bread. Top with another slice of bread and unmelted cheese.";
    String[] inputFields = {"Name", "Description", "Ingredients", "Tags"};
    String[] parameters = {recipeName, recipeDesc, recipeIngredients, recipeTags};

    @Before
    public void intro() {
        System.out.println("Conducting test of the recipe processor...");
    }

    @Test
    public void testValidatorWhitespace() {
        String whitespaceError = " cannot be spaces only!!!";

        String testOneSpace = " ";
        String testTwoSpace = "  ";
        String testTabbedSpace = "\t";

        String[] whitespaceTest = {testOneSpace, testTwoSpace, testTabbedSpace};

        String[] testParameters = parameters.clone();

        // This ensures that this works no matter where in .inputValidator() whitespaces are placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = testParameters[i];
            for (int j = 0; j < whitespaceTest.length; j++) {
                testParameters[i] = whitespaceTest[j];

                assertEquals(recipeValidator.inputValidation(testParameters[0], testParameters[1], Arrays.asList(testParameters[2].split(",")), testParameters[3]), inputFields[i] + whitespaceError);
            }

            testParameters[i] = swap;
        }
    }

    @Test
    public void testValidatorEmpty() {
        String emptyError = " cannot be empty!!";

        String testEmpty = "";

        String[] testParameters = parameters.clone();

        // This ensures that this works no matter where in .inputValidator() the empty string is placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = testParameters[i];

            testParameters[i] = testEmpty;
            assertEquals(recipeValidator.inputValidation(testParameters[0], testParameters[1], Arrays.asList(testParameters[2].split(",")), testParameters[3]), inputFields[i] + emptyError);

            testParameters[i] = swap;
        }
    }

    @Test
    public void testValidatorSymbols() {
        String emptyError = " cannot be numbers or symbols only!!!";

        String testNumber = "1";
        String testDifferentNumbers = "326";
        String testSameNumbers = "666666"; // 😈
        String testSymbol = "!";
        String testDifferentSymbols = "#^@%(@!?";
        String testSameSymbols = "????????";

        String[] testParameters = parameters.clone();

        String[] symbolTest = {testNumber, testDifferentNumbers, testSameNumbers, testSymbol, testDifferentSymbols, testSameSymbols};// testNumberSymbols, testSymbolWhitespaceNumber};

        // This ensures that this works no matter where in .inputValidator() symbols are placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = testParameters[i];
            for (int j = 0; j < symbolTest.length; j++) {
                testParameters[i] = symbolTest[j];
                assertEquals(recipeValidator.inputValidation(testParameters[0], testParameters[1], Arrays.asList(testParameters[2].split(",")), testParameters[3]), inputFields[i] + emptyError);
            }
            testParameters[i] = swap;
        }
    }
}
