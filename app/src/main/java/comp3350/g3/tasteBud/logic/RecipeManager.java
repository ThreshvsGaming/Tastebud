package comp3350.g3.tasteBud.logic;


import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.data.RecipePersistence;

public class RecipeManager {
    private RecipePersistence recipePersistence;

    public RecipeManager() {

        recipePersistence = Services.getRecipePersistence();
    }


    public boolean addRecipe(Recipe recipe) throws IllegalArgumentException {
        if (recipe == null) throw new IllegalArgumentException("Recipe cannot be null");

        return recipePersistence.addRecipe(recipe);
    }


    public List<Recipe> getAllRecipes() {

        return recipePersistence.getAllRecipes();
    }
}

//    public void delete(int id) throws IllegalArgumentException
//    {
//        if(id < 0) throw new IllegalArgumentException(("Recipe Id must be positive"));
//        recipePersistence.deleteRecipe(id);
//    }
//public void editRecipe(Recipe recipe) {
//    if (recipe == null) throw new IllegalArgumentException("Recipe cannot be null");
//
//    recipePersistence.updateRecipe(recipe);
//}
//
//    public Recipe getRecipe(int id) throws RecipeNotFoundException, IllegalArgumentException {
//        if (id < 0) throw new IllegalArgumentException(("Recipe Id must be positive"));
//        Recipe found = null;
//
//        found = recipePersistence.getRecipe(id);
//        if (found == null) throw new RecipeNotFoundException();
//
//        return found;
//    }
