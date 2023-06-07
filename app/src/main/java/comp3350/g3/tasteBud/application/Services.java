package comp3350.g3.tasteBud.application;

import java.util.ArrayList;

import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.Recipe;

public class Services {
    private static IRecipeDB recipeDB;

    public static IRecipeDB getRecipeDB(){
        if (recipeDB == null){
            RecipeStub stub = new RecipeStub();
            stub.initRecipeDatabase();
            recipeDB = stub;
        }

        return recipeDB;
    }
}
