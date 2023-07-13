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

    public String[] constructSelectedTags(String tagList)
    {
        if(tagList != null && tagList.length() > 0) {
            return tagList.split("[,]");
        }

        return new String[0];
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
