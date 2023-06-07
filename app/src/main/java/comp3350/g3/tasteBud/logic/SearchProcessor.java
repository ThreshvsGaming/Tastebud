package comp3350.g3.tasteBud.logic;

import java.util.*;
import java.util.regex.*;

import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.Recipe;

public class SearchProcessor {


    public static List<Recipe> searchResults(String text)
    {
        List<Recipe> results = RecipeStub.getStoredRecipes();

        results = searchName(results,text);

        return results;
    }

    private static List<Recipe> searchName(List<Recipe> list, String text)
    {
        List<Recipe> searchResults = new ArrayList<>();


        String[] textSplit = text.split("\\s+");
        String patternText = "";
        for(int i = 0; i < textSplit.length; i++)
        {
            patternText += "(?=.*" + textSplit[i] + ")";

        }

        patternText = patternText + ".+";

        Pattern pattern = Pattern.compile(patternText, Pattern.CASE_INSENSITIVE);

        Matcher matcher;

        for(Recipe recipe : list)
        {
            matcher = pattern.matcher(recipe.getName());
            if(matcher.find())
            {
                searchResults.add(recipe);
            }
        }
        return searchResults;
    }


}
