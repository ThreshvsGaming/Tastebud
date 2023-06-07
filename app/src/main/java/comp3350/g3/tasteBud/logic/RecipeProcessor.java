package comp3350.g3.tasteBud.logic;

import java.util.ArrayList;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipeProcessor {
    private static IRecipeDB recipeDB;

    public RecipeProcessor() {
        recipeDB = Services.getRecipeDB();
    }

    public ArrayList<Recipe> getStoredRecipes(){
        return recipeDB.getStoredRecipes();
    }

    public String inputValidation(String recipeName, String recipeInstructions, String recipeIngredients, String recipeTags) {
        String[] inputTypes = {recipeName, recipeInstructions, recipeIngredients, recipeTags};
        String[] inputFields = {"Name", "Description", "Ingredients", "Tags"};

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

    public void addRecipes (String recipeName, String recipeDesc, String ingredients, String tags) {
        String[] ingredientsArray = ingredients.split(",");
        String[] tagsArray = tags.split(",");

        Recipe newRecipe = new Recipe(
                recipeName,
                recipeDesc,
                ingredientsArray,
                tagsArray
        );
        recipeDB.addRecipe(newRecipe);
    }


}
