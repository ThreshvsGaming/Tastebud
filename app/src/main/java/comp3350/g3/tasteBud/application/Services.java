package comp3350.g3.tasteBud.application;

import comp3350.g3.tasteBud.data.Interface.IRecipeDB;

public class Services {
    private static IRecipeDB recipeDB;

    public static IRecipeDB getRecipeDB(boolean isPersistence) {
        if (recipeDB == null) {
            if(isPersistence){
                recipeDB = DatabaseFactory.createHSQLDatabase();
            }
            else{
                recipeDB = StubDatabaseFactory.createArrayListDatabase();
            }
        }

        return recipeDB;
    }

}
