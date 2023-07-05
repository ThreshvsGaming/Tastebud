package comp3350.g3.tasteBud.application;

import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.data.hsqldb.RecipePersistenceHSQLDB;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;

public class Services {
    private static IRecipeDB recipeDB;

    public static IRecipeDB getRecipeDB(boolean isPersistence) {
        if (recipeDB == null) {
            if(isPersistence){
                recipeDB = new RecipePersistenceHSQLDB(Main.getDBPathName());
            }
            else{
                RecipeStub stub = new RecipeStub();
                stub.initRecipeDatabase();
                recipeDB = stub;

            }
        }

        return recipeDB;
    }

}
