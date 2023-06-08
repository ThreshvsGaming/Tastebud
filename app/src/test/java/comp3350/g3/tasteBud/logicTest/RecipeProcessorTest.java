package comp3350.g3.tasteBud.logicTest;

import comp3350.g3.tasteBud.logic.RecipeProcessor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class RecipeProcessorTest {
    RecipeProcessor recipeProcessor = new RecipeProcessor();

    String recipeIngredients = "Chicken drumsticks, Chicken, Buttermilk, Salt, Pepper, Flour, Corn starch, Paprika, Onion powder";
    String recipeTags = "Dinner, Fried";
    String recipeName = "Fried Chicken";
    String recipeDesc = "Combine dry ingredients. Combine wet ingredients to make a batter. Heat oil to 350 degrees. Dredge chicken and fry for 10 minutes.";
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
            for (int j = 0; j < whitespaceTest.length; j++){
                testParameters[i] = whitespaceTest[j];

                assertEquals(recipeProcessor.inputValidation(testParameters[0], testParameters[1], testParameters[2], testParameters[3]), inputFields[i] + whitespaceError);
            }

            testParameters[i] = swap;
        }
    }

    @Test
    public void testValidatorEmpty() {
        String emptyError = " cannot be empty!!";

        String testEmpty = "";
        String testNull = null;

        String[] testParameters = parameters.clone();


        // This ensures that this works no matter where in .inputValidator() whitespaces are placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = testParameters[i];

            testParameters[i] = testEmpty;
            assertEquals(recipeProcessor.inputValidation(testParameters[0], testParameters[1], testParameters[2], testParameters[3]), inputFields[i] + emptyError);

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

        // This ensures that this works no matter where in .inputValidator() whitespaces are placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = testParameters[i];
            for (int j = 0; j < symbolTest.length; j++){
                testParameters[i] = symbolTest[j];
                assertEquals(recipeProcessor.inputValidation(testParameters[0], testParameters[1], testParameters[2], testParameters[3]), inputFields[i] + emptyError);
            }
            testParameters[i] = swap;
        }
    }

}
