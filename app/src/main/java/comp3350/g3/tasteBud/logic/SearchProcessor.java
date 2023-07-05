package comp3350.g3.tasteBud.logic;

import java.util.*;
import java.util.regex.*;

import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.object.Recipe;

public class SearchProcessor {
    private IRecipeDB recipeDB;

    public SearchProcessor(boolean isPersistence) {
        recipeDB = Services.getRecipeDB(isPersistence);
    }

    public List<Recipe> searchResults(String text) {
        List<Recipe> results = recipeDB.getAllRecipes();

        results = searchName(results, text);

        return results;
    }

    private List<Recipe> searchName(List<Recipe> list, String text) {
        List<Recipe> searchResults = new ArrayList<>();

        if(list != null) {
            String patternText = tokenizer(text);
            Pattern pattern = Pattern.compile(patternText, Pattern.CASE_INSENSITIVE);
            Matcher matcher;

            for (Recipe recipe : list) {
                matcher = pattern.matcher(recipe.getName());
                if (matcher.find()) {
                    searchResults.add(recipe);
                }
            }
        }

        return searchResults;
    }

    private static String tokenizer(String text) {
        //Grabs tokens from input string via space delimiter
        String[] textSplit = text.split("\\s+");

        //Assembles the regex pattern so that it contains all tokens
        String patternText = "";
        for (int i = 0; i < textSplit.length; i++) {
            patternText += "(?=.*" + textSplit[i] + ")";
        }

        return patternText + ".+"; //puts all pattern text together to be consumed by a pattern matcher
    }
}
