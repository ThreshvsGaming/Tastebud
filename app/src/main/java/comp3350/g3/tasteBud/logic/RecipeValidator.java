package comp3350.g3.tasteBud.logic;

import java.util.List;

public class RecipeValidator {
    public String inputValidation(String recipeName, String recipeInstructions, List recipeIngredients, String recipeTags) {
        String[] inputTypes = {
                recipeName,
                recipeInstructions,
                String.join(",", recipeIngredients), // Convert a List<String> to a comma-separated string
                recipeTags
        };
        String[] inputFields = {
                "Name",
                "Description",
                "Ingredients",
                "Tags"
        };


        for (int i = 0; i < inputTypes.length; i++) {
            String error = inputValidationHelper(inputTypes[i], inputFields[i]);
            if (error != null) {
                return error;
            }
        }

        //return null for any uncaught exceptions for now
        return null;
    }

    private String inputValidationHelper(String input, String fieldName) {
        if (input.isEmpty()) {
            return fieldName + " cannot be empty!!";
        } else if (input.matches("^\\s+$")) {
            return fieldName + " cannot be spaces only!!!";
        } else if (input.matches("^\\p{Punct}+$") || input.matches("\\d+$")) {
            return fieldName + " cannot be numbers or symbols only!!!";
        }

        //return null for any uncaught exceptions for now
        return null;
    }
}
