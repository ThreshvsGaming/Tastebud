package comp3350.g3.tasteBud.logic;

import android.util.Log;

import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.object.Ratings;
import comp3350.g3.tasteBud.object.Recipe;

public class RatingsProcessor {
    private static IRecipeDB recipeDB;

    // Constructor for unit testing with custom DBs
    public RatingsProcessor(IRecipeDB recipeDB) {
        this.recipeDB = recipeDB;
    }

    // Production constructor
    public RatingsProcessor (Boolean isPersistence) {
        recipeDB = Services.getRecipeDB(isPersistence);
    }


    public void addRatings(int recipeID, Ratings ratings) throws IllegalArgumentException {
        recipeDB.addRatings(recipeID, ratings.getRecipeRatings());
        Log.d("APP", "addRatings: " + ratings.getRecipeRatings() + "  recipeID: " + recipeID);

    }

    public int getRating (int id){
        Log.d("TAG", "initializeRatings: " +  recipeDB.getRating(id));
            return recipeDB.getRating(id);
    }

    public void deleteRating (int id) {
        recipeDB.deleteRatings(id);
    }
}
