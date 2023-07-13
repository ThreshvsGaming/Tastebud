package comp3350.g3.tasteBud.data;

import java.util.*;

import comp3350.g3.tasteBud.object.Recipe;

public class RecipeStub implements IRecipeDB {
    private static ArrayList<Recipe> storedRecipes;

    public RecipeStub(){
        if(storedRecipes == null){
            initRecipeDatabase();
        }
    }


    public void initRecipeDatabase() {
        ArrayList<String> recipe1ingredients = new ArrayList<>(Arrays.asList("Chicken drumsticks", "Chicken", "Buttermilk", "Salt", "Pepper", "Flour", "Corn starch", "Paprika", "Onion powder"));
        String recipe1tags = "Dinner, Fried";
        ArrayList<String> recipe2ingredients = new ArrayList<>(Arrays.asList("Chicken thighs", "Soy sauce", "Vinegar", "Garlic", "Bay leaves", "Peppercorns", "Brown sugar"));
        String recipe2tags = "Lunch, Dinner, Filipino";
        ArrayList<String> recipe3ingredients = new ArrayList<>(Arrays.asList("Basmati rice", "Marinated lamb", "Yogurt", "Onions", "Ginger", "Garlic", "Green chilies", "Saffron", "Ghee", "Whole spices"));
        String recipe3tags = "Dinner,Indian";
        ArrayList<String> recipe4ingredients = new ArrayList<>(Arrays.asList("Calamari", "Flour", "Cornmeal", "Paprika", "Salt", "Pepper", "Garlic powder", "Egg", "Milk", "Oil"));
        String recipe4tags = "Appetizer, Seafood, Fried";
        Recipe recipe1 = new Recipe(
                "Fried Chicken",
                "Combine dry ingredients. Combine wet ingredients to make a batter. Heat oil to 350 degrees. Dredge chicken and fry for 10 minutes.",
                recipe1ingredients,
                recipe1tags
        );
        Recipe recipe2 = new Recipe(
                "Chicken Adobo",
                "Combine soy sauce, vinegar, garlic, bay leaves, peppercorns, and brown sugar. Marinate chicken in the mixture for 1 hour. Cook chicken in the marinade until tender.",
                recipe2ingredients,
                recipe2tags
        );
        Recipe recipe3 = new Recipe(
                "Kacchi Biryani",
                "Soak basmati rice. Marinate lamb with yogurt and spices. Layer rice and marinated lamb in a pot. Add saffron and ghee. Cook on low heat until rice and lamb are tender.",
                recipe3ingredients,
                recipe3tags
        );
        Recipe recipe4 = new Recipe(
                "Crispy Calamari",
                "Slice calamari into rings. In a bowl, mix flour, cornmeal, paprika, salt, pepper, and garlic powder. In another bowl, whisk egg and milk. Dip calamari rings in the egg mixture, then coat them in the flour mixture. Fry in oil until golden brown and crispy.",
                recipe4ingredients,
                recipe4tags
        );
        recipe1.setId(1);
        recipe2.setId(2);
        recipe3.setId(3);
        recipe4.setId(4);

        storedRecipes = new ArrayList<>();
        storedRecipes.add(recipe1);
        storedRecipes.add(recipe2);
        storedRecipes.add(recipe3);
        storedRecipes.add(recipe4);
    }

    public boolean addRecipe(Recipe newRecipe) {
        final int NUM_UNIQUE_IDS = 1000;
        boolean pass = false;
        if(storedRecipes.size() < NUM_UNIQUE_IDS) {
            int newID = -1;
            while (newID == -1) {
                newID = (int) (Math.random() * NUM_UNIQUE_IDS);
                for (Recipe r : storedRecipes) {
                    if (newID == r.getId()) {
                        newID = -1;
                        break;
                    }
                }
            }
            newRecipe.setId(newID);
            storedRecipes.add(newRecipe);
            pass = true;
        }
        return pass;
    }
    @Override
    public Recipe getRecipe(int id) {
        Recipe result = null;
        for(Recipe r: storedRecipes){
            if(r.getId() == id)
            {
                result = r;
                break;
            }
        }
        return result;
    }

    public ArrayList<Recipe> getAllRecipes() {
        return storedRecipes;
    }
    @Override
    public void deleteRecipe(int id) {
        for(Recipe r: storedRecipes){
            if(r.getId() == id)
            {
                storedRecipes.remove(r);
                break;
            }
        }
    }

    @Override
    public void updateRecipe(Recipe recipe) {
        for(Recipe r: storedRecipes){
            if(r.getId() == recipe.getId())
            {
                storedRecipes.remove(r);
                storedRecipes.add(recipe);
                break;
            }
        }
    }


}
