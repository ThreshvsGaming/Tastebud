package comp3350.g3.tasteBud.data;

import java.util.*;

import comp3350.g3.tasteBud.object.Recipe;

public class RecipeStub implements IRecipeDB {
    private static ArrayList<Recipe> storedRecipes;

    public void initRecipeDatabase() {
        String[] recipe1ingredients = {"Chicken drumsticks", "Chicken", "Buttermilk", "Salt", "Pepper", "Flour", "Corn starch", "Paprika", "Onion powder"};
        String[] recipe1tags = {"Dinner", "Fried"};
        String[] recipe2ingredients = {"Chicken thighs", "Soy sauce", "Vinegar", "Garlic", "Bay leaves", "Peppercorns", "Brown sugar"};
        String[] recipe2tags = {"Lunch", "Dinner", "Filipino"};
        String[] recipe3ingredients = {"Basmati rice", "Marinated lamb", "Yogurt", "Onions", "Ginger", "Garlic", "Green chilies", "Saffron", "Ghee", "Whole spices"};
        String[] recipe3tags = {"Dinner", "Indian"};
        String[] recipe4ingredients = {"Calamari", "Flour", "Cornmeal", "Paprika", "Salt", "Pepper", "Garlic powder", "Egg", "Milk", "Oil"};
        String[] recipe4tags = {"Appetizer", "Seafood", "Fried"};
      /*  Recipe recipe1 = new Recipe(
                1,
                "Fried Chicken",
                "Combine dry ingredients. Combine wet ingredients to make a batter. Heat oil to 350 degrees. Dredge chicken and fry for 10 minutes.",
                recipe1ingredients,
                recipe1tags
        );
        Recipe recipe2 = new Recipe(
                2,
                "Chicken Adobo",
                "Combine soy sauce, vinegar, garlic, bay leaves, peppercorns, and brown sugar. Marinate chicken in the mixture for 1 hour. Cook chicken in the marinade until tender.",
                recipe2ingredients,
                recipe2tags
        );
        Recipe recipe3 = new Recipe(
                3,
                "Kacchi Biryani",
                "Soak basmati rice. Marinate lamb with yogurt and spices. Layer rice and marinated lamb in a pot. Add saffron and ghee. Cook on low heat until rice and lamb are tender.",
                recipe3ingredients,
                recipe3tags
        );
        Recipe recipe4 = new Recipe(
                4,
                "Crispy Calamari",
                "Slice calamari into rings. In a bowl, mix flour, cornmeal, paprika, salt, pepper, and garlic powder. In another bowl, whisk egg and milk. Dip calamari rings in the egg mixture, then coat them in the flour mixture. Fry in oil until golden brown and crispy.",
                recipe4ingredients,
                recipe4tags
        );
*/
        storedRecipes = new ArrayList<>();
     /*   storedRecipes.add(recipe1);
        storedRecipes.add(recipe2);
        storedRecipes.add(recipe3);
        storedRecipes.add(recipe4);*/
    }

    public void addRecipe(Recipe newRecipe) {
        storedRecipes.add(newRecipe);
    }

    public ArrayList<Recipe> getStoredRecipes() {
        return storedRecipes;
    }
}
