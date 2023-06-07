package comp3350.g3.tasteBud.logic;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import comp3350.g3.tasteBud.data.RecipeDB;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.Recipe;


public class RecipeProcessor {

    private RecipeDB recipeDB;

    public RecipeProcessor(RecipeDB recipeDB) {
        this.recipeDB = recipeDB;
    }


    //User input validation
    public String inputValidation(String recipeName, String recipeInstructions, String recipeIngredients, String recipeTags) {
        String[] inputTypes = {recipeName, recipeInstructions, recipeIngredients, recipeTags};
        String[] inputFields = {"Name", "Description", "Ingredients", "Tags"};

        for (int i = 0; i < inputTypes.length; i++) {
            String error = inputValidationHelper(inputTypes[i], inputFields[i]);
            if (error != null) {
                return error;
            }
        }
        return null;
    }


    //Helper method for user input validation
    private String inputValidationHelper(String input, String fieldName) {
        if (input.isEmpty()) {
            return fieldName + " cannot be empty!!";
        } else if (input.matches("^\\s+$")) {
            return fieldName + " cannot be spaces only!!";
        } else if (input.matches("^\\p{Punct}+$") || input.matches("\\d+$")) {
            return fieldName + " cannot be numbers or symbols only!!";
        }
        return null;
    }

}
