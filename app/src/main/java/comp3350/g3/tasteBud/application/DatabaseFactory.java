package comp3350.g3.tasteBud.application;

import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.data.hsqldb.RecipeDBPersistence;

public class DatabaseFactory {

    public static IRecipeDB createHSQLDatabase(){
        return new RecipeDBPersistence(Main.getDBPathName());
    }
}
