package comp3350.g3.tasteBud.application;

import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.RecipeStub;

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
