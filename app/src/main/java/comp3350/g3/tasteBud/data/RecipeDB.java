package comp3350.g3.tasteBud.data;

import java.util.*;
import comp3350.g3.tasteBud.object.Recipe;

public interface RecipeDB {
    public static List<Recipe> getStoredRecipes() {return null;}
    public void add(Recipe newRecipe);
    public void clear();
}
