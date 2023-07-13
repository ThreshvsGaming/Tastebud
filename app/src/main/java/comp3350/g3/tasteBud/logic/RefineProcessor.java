package comp3350.g3.tasteBud.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.object.Recipe;

public class RefineProcessor {
    private static IRecipeDB recipeDB;

    public RefineProcessor(boolean isPersistence) {
        recipeDB = Services.getRecipeDB(isPersistence);
    }

    public RefineProcessor(IRecipeDB recipeDB) {
        RefineProcessor.recipeDB = recipeDB;
    }

    public String[] constructSelectedTags(String tagList)
    {
        String[] results = new String[0];
        if(tagList != null && tagList.length() > 0) {
            results = tagList.split("[,]");
            for(int i = 0 ; i < results.length ; i++){
                results[i] = results[i].trim();
            }
        }
        return results;
    }

    public String[] getTagList() {
        HashSet<String> tagList = new HashSet<>();

        List<Recipe> recipes = recipeDB.getAllRecipes();

        for(Recipe recipe: recipes) {
            List<String> currTagList = recipe.getTags();

            for(String tag: currTagList){
                tagList.add(tag);
            }
        }

        return tagList.toArray(new String[tagList.size()]);
    }
}
