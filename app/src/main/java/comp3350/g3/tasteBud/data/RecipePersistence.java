package comp3350.g3.tasteBud.data;

import java.util.*;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipePersistence {
    private List<Recipe> storedRecipes;

    public RecipePersistence(List<Recipe> storedRecipes) {
        this.storedRecipes = storedRecipes;
    }

    public void add(Recipe newRecipe) {
        this.storedRecipes.add(newRecipe);
    }
}
