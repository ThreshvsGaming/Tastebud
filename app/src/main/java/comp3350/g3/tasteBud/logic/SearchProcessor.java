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

    public SearchProcessor(IRecipeDB newRecipeDB) { recipeDB = newRecipeDB; }

    public List<Recipe> searchResults(String text) {
        List<Recipe> results = recipeDB.getAllRecipes();

        results = searchName(results,text);

        return results;
    }

    public List<Recipe> searchResultsWithTag(String[] tagList, String text){
        List<Recipe> results = searchResults(text);

        return searchTags(results, tagList);
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

    private String tokenizer(String text) {
        //Grabs tokens from input string via space delimiter
        String[] textSplit = text.split("[^a-zA-Z0-9']");

        //Assembles the regex pattern so that it contains all tokens
        String patternText = "";
        for(int i = 0; i < textSplit.length; i++) {
            patternText += "(?=.*" + textSplit[i] + ")";
        }

        return patternText + ".+"; //puts all pattern text together to be consumed by a pattern matcher
    }

    private List<Recipe> searchTags(List<Recipe> recipes, String[] tagList) {
        List<Recipe> matchedRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if(matchedTag(recipe, tagList)){
                matchedRecipes.add(recipe);
            }
        }

        return matchedRecipes;
    }

    private boolean matchedTag(Recipe recipe, String[] tagList){
        for (String recipeTag : recipe.getTags()) {
            for (String tag : tagList){
                if (recipeTag.equals(tag)){
                    return true;
                }
            }
        }
        return false;
    }
}
