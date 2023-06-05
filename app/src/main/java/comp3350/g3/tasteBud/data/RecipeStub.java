package comp3350.g3.tasteBud.data;

import java.util.*;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipeStub implements RecipeDB {
    private ArrayList<Recipe> storedRecipes;
    private int nextId;

    public RecipeStub() {
        this.storedRecipes = new ArrayList<Recipe>();
        this.nextId = 1;
    }
    public RecipeStub(ArrayList<Recipe> storedRecipes) {
        String[] recipe1ingredients = {"Chicken drumsticks", "Chicken", "Buttermilk", "Salt", "Pepper", "Flour", "Corn starch", "Paprika", "Onion powder"};
        String[] recipe1tags = {"Dinner", "Fried"};
        String[] recipe2ingredients = {"Chicken thighs", "Soy sauce", "Vinegar", "Garlic", "Bay leaves", "Peppercorns", "Brown sugar"};
        String[] recipe2tags = {"Lunch", "Dinner", "Filipino"};
        String[] recipe3ingredients = {"Basmati rice", "Marinated lamb", "Yogurt", "Onions", "Ginger", "Garlic", "Green chilies", "Saffron", "Ghee", "Whole spices"};
        String[] recipe3tags = {"Dinner", "Indian"};
        String[] recipe4ingredients = {"Calamari", "Flour", "Cornmeal", "Paprika", "Salt", "Pepper", "Garlic powder", "Egg", "Milk", "Oil"};
        String[] recipe4tags = {"Appetizer", "Seafood", "Fried"};
        String[] recipe5ingredients = {"Lasagna noodles", "Ground beef", "Onion", "Garlic", "Tomato sauce", "Tomato paste", "Italian seasoning", "Salt", "Pepper", "Ricotta cheese", "Mozzarella cheese", "Parmesan cheese"};
        String[] recipe5tags = {"Dinner", "Italian"};
        String[] recipe6ingredients = {"Eggs", "Spinach", "Cheddar cheese", "Onion", "Salt", "Pepper", "Butter"};
        String[] recipe6tags = {"Breakfast", "Omelette"};
        this.storedRecipes = new ArrayList<Recipe>();
        this.nextId = storedRecipes.size() + 1; //with the assumption that passed arrayList already contains some recipes.
        Recipe recipe1 = new Recipe(
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
        Recipe recipe5 = new Recipe(
                5,
                "Classic Lasagna",
                "Cook lasagna noodles according to package instructions. In a skillet, brown ground beef with onion and garlic. Add tomato sauce, tomato paste, Italian seasoning, salt, and pepper to the beef mixture. In a baking dish, layer noodles, beef mixture, ricotta cheese, mozzarella cheese, and Parmesan cheese. Repeat layers. Bake in the oven until cheese is melted and lasagna is bubbly.",
                recipe5ingredients,
                recipe5tags
        );
        Recipe recipe6 = new Recipe(
                6,
                "Cheesy Spinach Omelette",
                "Whisk eggs in a bowl and season with salt and pepper. Heat butter in a non-stick skillet over medium heat. Add chopped onion and sauté until softened. Add spinach and cook until wilted. Pour the beaten eggs into the skillet and let it cook until the edges set. Sprinkle grated cheddar cheese over the eggs and fold the omelette in half. Cook for another minute until the cheese melts. Serve hot.",
                recipe6ingredients,
                recipe6tags
        );
        this.storedRecipes.add(recipe1);
        nextId++;   //2
        this.storedRecipes.add(recipe2);
        nextId++;   //3
        this.storedRecipes.add(recipe3);
        nextId++;   //4
        this.storedRecipes.add(recipe4);
        nextId++;   //5
        this.storedRecipes.add(recipe5);
        nextId++;   //6
        this.storedRecipes.add(recipe6);
        nextId++;   //7, for new recipes being added using add method

    }

    public void add(Recipe newRecipe) {
        newRecipe.setId(nextId);
        this.storedRecipes.add(newRecipe);
        nextId++;   //update nextId
    }

    public ArrayList<Recipe> getStoredRecipes() {
        return storedRecipes;
    }

    public void clear() {
        storedRecipes.clear();
    }
}
