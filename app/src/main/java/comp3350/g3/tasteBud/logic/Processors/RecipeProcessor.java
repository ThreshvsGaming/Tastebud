package comp3350.g3.tasteBud.logic.Processors;

import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.logic.Singletons.PersistenceSingleton;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipeProcessor {
    private static IRecipeDB recipeDB;

    // Constructor for unit testing with custom DBs
    public RecipeProcessor(IRecipeDB recipeDB) {
        this.recipeDB = recipeDB;
    }

    // Production constructor
    public RecipeProcessor() {
        recipeDB = Services.getRecipeDB(PersistenceSingleton.getInstance().GetIsPersistence());
    }

    public void addRecipe(String recipeName, String recipeDesc, List<String> ingredients, String tags, String imageUri) throws IllegalArgumentException {
        Recipe recipe = buildRecipe(recipeName, recipeDesc, ingredients, tags, imageUri);

        recipeDB.addRecipe(recipe);
    }

    public void updateRecipe(int recipeId, String recipeName, String recipeDesc, List<String> ingredients, String tags, String imageUri) throws IllegalArgumentException {
        Recipe recipe = buildRecipe(recipeName, recipeDesc, ingredients, tags, imageUri);
        recipe.setId(recipeId);
        recipeDB.updateRecipe(recipe);
    }

    private Recipe buildRecipe(String recipeName, String recipeDesc, List<String> ingredients, String tags, String imageUri) {
        return new Recipe(recipeName, recipeDesc, ingredients, tags, imageUri);
    }

    public void deleteListOfRecipe(List<Recipe> recipes) {
        for (int i = 0; i < recipes.size(); i++) {
            deleteRecipe(recipes.get(i).getId());
        }
    }

    public Recipe getRecipe(int id) {
        return recipeDB.getRecipe(id);
    }

    public void deleteRecipe(int id) {
        recipeDB.deleteRecipe(id);
    }


}
