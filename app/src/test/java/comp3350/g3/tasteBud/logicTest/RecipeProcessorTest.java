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

        // This ensures that this works no matter where in .inputValidator() whitespaces are placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = parameters[i];
            for (int j = 0; j < whitespaceTest.length; j++){
                parameters[i] = whitespaceTest[j];

                assertEquals(recipeProcessor.inputValidation(parameters[0], parameters[1], parameters[2], parameters[3]), inputFields[i] + whitespaceError);
            }

            parameters[i] = swap;
        }
    }

    @Test
    public void testValidatorEmpty() {
        String emptyError = " cannot be empty!!";

        String testEmpty = "";
        String testNull = null;

        // This ensures that this works no matter where in .inputValidator() whitespaces are placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = parameters[i];

            parameters[i] = testEmpty;
            assertEquals(recipeProcessor.inputValidation(parameters[0], parameters[1], parameters[2], parameters[3]), inputFields[i] + emptyError);

            parameters[i] = swap;
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
        String testNumberSymbols = "!@?23!24^112";
        String testSymbolWhitespaceNumber = "31  !?9..21#";


        String[] symbolTest = {testNumber, testDifferentNumbers, testSameNumbers, testSymbol, testDifferentSymbols, testSameSymbols, testNumberSymbols, testSymbolWhitespaceNumber};

        // This ensures that this works no matter where in .inputValidator() whitespaces are placed.
        for (int i = 0; i < inputFields.length; i++) {
            String swap = parameters[i];
            for (int j = 0; j < symbolTest.length; j++){
                parameters[i] = symbolTest[j];
                assertEquals(recipeProcessor.inputValidation(parameters[0], parameters[1], parameters[2], parameters[3]), inputFields[i] + emptyError);
            }
            parameters[i] = swap;
        }
    }

}
