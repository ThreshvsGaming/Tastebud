package comp3350.g3.tasteBud.application;

import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.data.RecipePersistence;
import comp3350.g3.tasteBud.data.hsqldb.RecipePersistenceHSQLDB;

public class Services {
    private static IRecipeDB recipeDB;

    public static IRecipeDB getRecipeDB() {
        if (recipeDB == null) {
            RecipeStub stub = new RecipeStub();
            stub.initRecipeDatabase();
            recipeDB = stub;
        }

        return recipeDB;
    }

    private static RecipePersistence recipePersistence = null;

    public static synchronized RecipePersistence getRecipePersistence() {
        if (recipePersistence == null) {
            recipePersistence = new RecipePersistenceHSQLDB(Main.getDBPathName());
        }
        return recipePersistence;
    }
}
