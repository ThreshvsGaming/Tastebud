package comp3350.g3.tasteBud.logic;


import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipeProcessor {
    private static IRecipeDB recipeDB;

    public RecipeProcessor(Boolean isPersistence) {
        recipeDB = Services.getRecipeDB(isPersistence);
    }

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

    public void addRecipe(String recipeName, String recipeDesc, List<String> ingredients, String tags) throws IllegalArgumentException {
        Recipe recipe = buildRecipe(recipeName, recipeDesc, ingredients, tags);

        recipeDB.addRecipe(recipe);
    }

    private Recipe buildRecipe(String recipeName, String recipeDesc, List<String> ingredients, String tags){
        return new Recipe(recipeName, recipeDesc, ingredients, tags);
    }
}
