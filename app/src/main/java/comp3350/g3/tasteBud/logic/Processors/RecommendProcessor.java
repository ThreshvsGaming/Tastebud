package comp3350.g3.tasteBud.logic.Processors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.logic.Singletons.PersistenceSingleton;
import comp3350.g3.tasteBud.object.Recipe;

public class RecommendProcessor {
    private static IRecipeDB recipeDB;

    public RecommendProcessor() {
        recipeDB = Services.getRecipeDB(PersistenceSingleton.getInstance().GetIsPersistence());
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

    public List<Recipe> searchAssemblableRecipes(String ingredientListString) {
        String[] ingredientList = constructSelectedIngredients(ingredientListString);

        List<Recipe> matchedRecipes = new ArrayList<>();
        List<Recipe> allRecipes = recipeDB.getAllRecipes();

        for (Recipe recipe : allRecipes) {
            if( matchesAllIngredients(recipe, ingredientList) ) {
                matchedRecipes.add(recipe);
            }
        }

        return matchedRecipes;
    }

    private Boolean matchesAllIngredients(Recipe recipe, String[] ingredientList) {
        List<String> currIngredientList = new LinkedList<>(recipe.getIngredients());
        boolean matches = false;

        for(int i = 0; i < ingredientList.length && !currIngredientList.isEmpty(); i++) {
            if( currIngredientList.contains(ingredientList[i]) ) {
                currIngredientList.remove(ingredientList[i]);
            }
        }

        if(currIngredientList.isEmpty()){
            matches = true;
        }

        return matches;
    }
}
