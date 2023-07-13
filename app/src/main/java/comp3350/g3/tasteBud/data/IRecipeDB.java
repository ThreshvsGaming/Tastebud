package comp3350.g3.tasteBud.data;

import java.util.*;

import comp3350.g3.tasteBud.object.Recipe;

public interface IRecipeDB {
    ArrayList<Recipe> getAllRecipes();

    boolean addRecipe(Recipe newRecipe);

    Recipe getRecipe(int id);

    void deleteRecipe(int id);

    void updateRecipe(Recipe recipe);

}
