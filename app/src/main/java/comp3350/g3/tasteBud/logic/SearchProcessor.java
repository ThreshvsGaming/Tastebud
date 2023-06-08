package comp3350.g3.tasteBud.logic;

import java.util.*;
import java.util.regex.*;
import comp3350.g3.tasteBud.application.Services;
import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.object.Recipe;

public class SearchProcessor {
    private IRecipeDB recipeDB;

    public SearchProcessor() {recipeDB = Services.getRecipeDB(); }

    //Mostly for unit testing
    public SearchProcessor(IRecipeDB newRecipeDB) {recipeDB = newRecipeDB;}

    public ArrayList<Recipe> searchResults(String text) {
        ArrayList<Recipe> results = recipeDB.getStoredRecipes();

        results = searchName(results,text);

        return results;
    }

    private ArrayList<Recipe> searchName(List<Recipe> list, String text) {
        ArrayList<Recipe> searchResults = new ArrayList<>();

        String patternText = tokenizer(text);
        Pattern pattern = Pattern.compile(patternText, Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        for(Recipe recipe : list) {
            matcher = pattern.matcher(recipe.getName());
            if(matcher.find()) {
                searchResults.add(recipe);
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

    public void setRecipeDB(IRecipeDB newRecipeDB) {recipeDB = newRecipeDB; }

    public IRecipeDB getRecipeDB() {return recipeDB;}
}
