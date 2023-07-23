package comp3350.g3.tasteBud.logic;

import java.util.HashSet;
import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.object.Recipe;

public class RecommendProcessor {
    private static IRecipeDB recipeDB;

    public RecommendProcessor(boolean isPersistence) {
        recipeDB = Services.getRecipeDB(isPersistence);
    }

    public RecommendProcessor(IRecipeDB recipeDB) {
        this.recipeDB = recipeDB;
    }

    public String[] constructSelectedIngredients(String ingredientList) {
        String[] results = new String[0];
        if (ingredientList != null && ingredientList.length() > 0) {
            results = ingredientList.split("[,]");
            for (int i = 0; i < results.length; i++) {
                results[i] = results[i].trim();
            }
        }
        return results;
    }

    public String[] getIngredientList() {
        HashSet<String> ingredientList = new HashSet<>();

        List<Recipe> recipes = recipeDB.getAllRecipes();

        for (Recipe recipe : recipes) {
            List<String> currIngredientList = recipe.getIngredients();

            for (String ingredient : currIngredientList) {
                ingredientList.add(ingredient);
            }
        }
        return ingredientList.toArray(new String[ingredientList.size()]);
    }
}
