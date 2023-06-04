package comp3350.g3.tasteBud.data;

import java.util.List;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipeStub implements RecipePersistence {
    private List<Recipe> storedRecipes;

    public RecipeStub(List<Recipe> storedRecipes) {
        this.storedRecipes = storedRecipes;
    }

    public void add(Recipe newRecipe) {
        this.storedRecipes.add(newRecipe);
    }

    public List<Recipe> getStoredRecipes() {
        return storedRecipes;
    }

    public void clear() {
        storedRecipes.clear();
    }
}
