package comp3350.g3.tasteBud.data.Interface;

import java.util.*;

import comp3350.g3.tasteBud.object.Recipe;

public interface IRecipeDB {
    ArrayList<Recipe> getAllRecipes();

    boolean addRecipe(Recipe newRecipe);

    boolean addRatings(int recipeID, int ratings);

    int getRating(int id);

    Recipe getRecipe(int id);

    void deleteRecipe(int id);

    void updateRecipe(Recipe recipe);

    void deleteRatings(int id);
}
