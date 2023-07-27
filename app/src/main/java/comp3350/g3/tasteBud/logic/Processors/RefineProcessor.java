package comp3350.g3.tasteBud.logic.Processors;

import java.util.HashSet;
import java.util.List;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.logic.Singletons.PersistenceSingleton;
import comp3350.g3.tasteBud.object.Recipe;

public class RefineProcessor {
    private static IRecipeDB recipeDB;

    public RefineProcessor() {
        recipeDB = Services.getRecipeDB(PersistenceSingleton.getInstance().GetIsPersistence());
    }

    public RefineProcessor(IRecipeDB recipeDB) {
        this.recipeDB = recipeDB;
    }

    public String[] constructSelectedTags(String tagList) {
        String[] results = new String[0];
        if (tagList != null && tagList.length() > 0) {
            results = tagList.split("[,]");
            for (int i = 0; i < results.length; i++) {
                results[i] = results[i].trim();
            }
        }
        return results;
    }

    public String[] getTagList() {
        HashSet<String> tagList = new HashSet<>();

        List<Recipe> recipes = recipeDB.getAllRecipes();

        for (Recipe recipe : recipes) {
            List<String> currTagList = recipe.getTags();

            for (String tag : currTagList) {
                tagList.add(tag);
            }
        }

        return tagList.toArray(new String[tagList.size()]);
    }
}
