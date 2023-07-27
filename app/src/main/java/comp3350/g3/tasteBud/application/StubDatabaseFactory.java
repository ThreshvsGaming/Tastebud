package comp3350.g3.tasteBud.application;

import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.data.StubDatabase.RecipeStub;

public class StubDatabaseFactory {

    public static IRecipeDB createArrayListDatabase(){
        IRecipeDB stub = new RecipeStub();
        ((RecipeStub)stub).initRecipeDatabase();
        return stub;
    }
}
