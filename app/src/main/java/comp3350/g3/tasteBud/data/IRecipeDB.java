package comp3350.g3.tasteBud.data;

import java.util.*;

import comp3350.g3.tasteBud.object.Recipe;

public interface IRecipeDB {
    ArrayList<Recipe> getStoredRecipes();

    void addRecipe(Recipe newRecipe);
}
