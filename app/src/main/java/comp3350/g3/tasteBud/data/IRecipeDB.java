package comp3350.g3.tasteBud.data;

import java.util.*;

import comp3350.g3.tasteBud.object.Recipe;

public interface IRecipeDB {
    boolean addRecipe(Recipe recipe);
    Recipe getRecipe(int id);

    ArrayList<Recipe> getAllRecipes();
    void deleteRecipe(int id);
    void updateRecipe(Recipe recipe);
}
