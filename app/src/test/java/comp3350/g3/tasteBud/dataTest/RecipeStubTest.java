package comp3350.g3.tasteBud.dataTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.g3.tasteBud.data.StubDatabase.RecipeStub;
import comp3350.g3.tasteBud.object.Recipe;

import static org.junit.Assert.*;

public class RecipeStubTest {

    private RecipeStub recipeStub;

    @Before
    public void setUp() {
        recipeStub = new RecipeStub();
        recipeStub.initRecipeDatabase();
    }

    @Test
    public void testGetStoredRecipes() {
        ArrayList<Recipe> storedRecipes = recipeStub.getAllRecipes();

        // Ensure the stored recipes list is not null
        assertNotNull(storedRecipes);

        // Ensure the stored recipes list contains the correct number of recipes
        assertEquals(4, storedRecipes.size());

        // Ensure the stored recipes list contains the expected recipes
        Recipe recipe1 = storedRecipes.get(0);
        assertEquals(1, recipe1.getId());
        assertEquals("Fried Chicken", recipe1.getName());
        assertEquals("Combine dry ingredients. Combine wet ingredients to make a batter. Heat oil to 350 degrees. Dredge chicken and fry for 10 minutes.", recipe1.getDesc());
        assertArrayEquals(new String[]{"Chicken drumsticks", "Chicken", "Buttermilk", "Salt", "Pepper", "Flour", "Corn starch", "Paprika", "Onion powder"}, recipe1.getIngredients().toArray());
        assertArrayEquals(new String[]{"Dinner", "Fried"}, recipe1.getTags().toArray());

        Recipe recipe2 = storedRecipes.get(1);
        assertEquals(2, recipe2.getId());
        assertEquals("Chicken Adobo", recipe2.getName());
        assertEquals("Combine soy sauce, vinegar, garlic, bay leaves, peppercorns, and brown sugar. Marinate chicken in the mixture for 1 hour. Cook chicken in the marinade until tender.", recipe2.getDesc());
        assertArrayEquals(new String[]{"Chicken thighs", "Soy sauce", "Vinegar", "Garlic", "Bay leaves", "Peppercorns", "Brown sugar"}, recipe2.getIngredients().toArray());
        assertArrayEquals(new String[]{"Lunch", "Dinner", "Filipino"}, recipe2.getTags().toArray());

        Recipe recipe3 = storedRecipes.get(2);
        assertEquals(3, recipe3.getId());
        assertEquals("Kacchi Biryani", recipe3.getName());
        assertEquals("Soak basmati rice. Marinate lamb with yogurt and spices. Layer rice and marinated lamb in a pot. Add saffron and ghee. Cook on low heat until rice and lamb are tender.", recipe3.getDesc());
        assertArrayEquals(new String[]{"Basmati rice", "Marinated lamb", "Yogurt", "Onions", "Ginger", "Garlic", "Green chilies", "Saffron", "Ghee", "Whole spices"}, recipe3.getIngredients().toArray());
        assertArrayEquals(new String[]{"Dinner", "Indian"}, recipe3.getTags().toArray());

        Recipe recipe4 = storedRecipes.get(3);
        assertEquals(4, recipe4.getId());
        assertEquals("Crispy Calamari", recipe4.getName());
        assertEquals("Slice calamari into rings. In a bowl, mix flour, cornmeal, paprika, salt, pepper, and garlic powder. In another bowl, whisk egg and milk. Dip calamari rings in the egg mixture, then coat them in the flour mixture. Fry in oil until golden brown and crispy.", recipe4.getDesc());
        assertArrayEquals(new String[]{"Calamari", "Flour", "Cornmeal", "Paprika", "Salt", "Pepper", "Garlic powder", "Egg", "Milk", "Oil"}, recipe4.getIngredients().toArray());
        assertArrayEquals(new String[]{"Appetizer", "Seafood", "Fried"}, recipe4.getTags().toArray());
    }

    @Test
    public void testAddRecipe() {
        // Create a new recipe
        Recipe newRecipe = new Recipe(
                "Test Recipe",
                "Test Description",
                Arrays.asList("Ingredient1", "Ingredient2"),
                "Tag1, Tag2"
        );

        // Add the new recipe to the stored recipes list
        recipeStub.addRecipe(newRecipe);

        // Retrieve the stored recipes list
        ArrayList<Recipe> storedRecipes = recipeStub.getAllRecipes();

        // Ensure the stored recipes list contains the new recipe
        assertTrue(storedRecipes.contains(newRecipe));
    }

    @After
    public void end() {
        System.out.println("End of test for RecipeStub");
    }

}
