package comp3350.g3.tasteBud.application;

import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.data.StubDatabase.RecipeStub;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;

public class Services {
    private static IRecipeDB recipeDB;

    public static IRecipeDB getRecipeDB(boolean isPersistence) {
        if (recipeDB == null) {
            if(isPersistence){
                recipeDB = CreateDatabase.createHSQLDatabase();
            }
            else{
                recipeDB = CreateStubDatabase.createArrayListDatabase();
            }
        }

        return recipeDB;
    }

}
