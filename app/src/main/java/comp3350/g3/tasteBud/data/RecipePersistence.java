package comp3350.g3.tasteBud.data;

import comp3350.g3.tasteBud.object.Recipe;
import java.util.ArrayList;

public interface RecipePersistence {
    boolean addRecipe(Recipe recipe);
    Recipe getRecipe(int id);

    ArrayList<Recipe> getAllRecipes();
    void deleteRecipe(int id);
    void updateRecipe(Recipe recipe);
}
