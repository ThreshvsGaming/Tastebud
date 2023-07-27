package comp3350.g3.tasteBud.logic.Processors;


import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.logic.Singletons.PersistenceSingleton;
import comp3350.g3.tasteBud.object.Ratings;


public class RatingsProcessor {
    private static IRecipeDB recipeDB;

    // Constructor for unit testing with custom DBs
    public RatingsProcessor(IRecipeDB recipeDB) {
        this.recipeDB = recipeDB;
    }

    // Production constructor
    public RatingsProcessor() {
        recipeDB = Services.getRecipeDB(PersistenceSingleton.getInstance().GetIsPersistence());
    }


    public void addRatings(int recipeID, Ratings ratings) throws IllegalArgumentException {
        recipeDB.addRatings(recipeID, ratings.getRecipeRatings());
    }

    public int getRating(int id) {
        return recipeDB.getRating(id);
    }
}
