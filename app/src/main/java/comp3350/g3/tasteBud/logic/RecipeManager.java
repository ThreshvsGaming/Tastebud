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


