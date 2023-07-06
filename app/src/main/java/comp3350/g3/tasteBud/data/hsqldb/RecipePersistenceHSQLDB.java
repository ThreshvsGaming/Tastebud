package comp3350.g3.tasteBud.data.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.g3.tasteBud.data.IRecipeDB;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipePersistenceHSQLDB implements IRecipeDB {
    private final String dbPath;
    private final String dbType;

    public RecipePersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
        this.dbType = "file";
    }

    /**
     * Returns a boolean indicating successful addition to the data backend.
     * Upon success, the new id of the passed recipe with also be set.
     *
     * @param  recipe  the recipe to add to the backend
     * @return         successful addition indicator
     */
    public boolean addRecipe(Recipe recipe) {
        if (recipe == null) throw new IllegalArgumentException("Recipe must not be null.");
        try (final Connection c = connection()) {

            // find unused ID
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM RECIPES ORDER BY ID");
            int newID = 0;
            while (rs.next() && newID == rs.getInt("ID")) {
                newID++;
            }
            recipe.setId(newID);

            // add new recipe
            PreparedStatement pst = c.prepareStatement("INSERT INTO RECIPES VALUES(?,?,?,?,?)");
            pst.setInt(1, recipe.getId());
            pst.setString(2, recipe.getName());
            pst.setString(3, recipe.getDesc());
            pst.setString(4, UtilsHSQLDB.encodeString(recipe.getIngredients()));
            pst.setString(5, UtilsHSQLDB.encodeString(recipe.getTags()));
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Returns the recipe specified at index id.
     * Upon failure, null we be returned.
     *
     * @param  id  the id of the recipe to search
     * @return     the searched recipe
     */
    public Recipe getRecipe(int id) {
        if (id < 0) throw new IllegalArgumentException("Recipe ID must be a non-negative integer.");
        try (final Connection c = connection()) {

            // find match by id, return null on empty result set
            final PreparedStatement st = c.prepareStatement("SELECT * FROM RECIPES WHERE ID=?");
            st.setInt(1, id);
            final ResultSet rs = st.executeQuery();
            Recipe recipe;
            if (rs.next()) {
                recipe = fromResultSet(rs);
            } else {
                recipe = null;
            }
            rs.close();
            st.close();
            return recipe;
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Returns all stored recipes.
     *
     * @return ArrayList of Recipe objects.
     */
    public ArrayList<Recipe> getAllRecipes() {
        final ArrayList<Recipe> recipes = new ArrayList<>();
        try (final Connection c = connection()) {

            // return all recipes in the result set
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM RECIPES ORDER BY NAME");
            while (rs.next()) {
                Recipe recipe = fromResultSet(rs);
                if (recipe != null) {
                    recipes.add(recipe);
                }
            }
            rs.close();
            st.close();
            return recipes;
        } catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Delete the recipe specified by id.
     * Return true/false indicating successful
     * removal.
     *
     * @param  id  the id of the recipe to remove
     */
    public void deleteRecipe(int id) {
        if (id < 0) throw new IllegalArgumentException("Recipe ID must be a non-negative integer.");
        try (final Connection c = connection()) {

            // delete any recipe whose id matches passed value
            final PreparedStatement st = c.prepareStatement("DELETE FROM RECIPES WHERE ID=?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     * Update the recipe specified by recipe.
     *
     * @param  recipe the recipe to update
     */
    public void updateRecipe(Recipe recipe) {
        if (recipe == null) throw new IllegalArgumentException("Recipe must not be null.");
        try (final Connection c = connection()) {

            // update any recipe whose id matches the recipe id
            final PreparedStatement st = c.prepareStatement("UPDATE RECIPES SET NAME=?, DESC=?,  INGREDIENTS=?, TAGS=?, FAVOURITE=? WHERE ID=?");
            st.setString(1, recipe.getName());
            st.setString(2, recipe.getDesc());
            st.setString(3, UtilsHSQLDB.encodeString(recipe.getIngredients()));
            st.setString(4, UtilsHSQLDB.encodeString(recipe.getTags()));
            st.setInt(5, recipe.getId());
            st.executeUpdate();
            st.close();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:" + dbType + ":" + dbPath + ";shutdown=true", "SA", "");
    }

    private Recipe fromResultSet(final ResultSet rs) throws SQLException {

        // get all fields from database
        final int id = rs.getInt("ID");
        final String name = rs.getString("NAME");
        final String description = rs.getString("DESC");
        final String ingredientsString = rs.getString("INGREDIENTS");
        final List<String> ingredients = Arrays.asList(ingredientsString.split(",")); // 拆分字符串并转换为List
        String tags = rs.getString("TAGS");

        // re-create recipe from constructor
        try {
            Recipe recipe = new Recipe(name, description, ingredients);
            // fill other fields
            recipe.setId(id);
            recipe.setTags(tags.split(UtilsHSQLDB.FIELD_SEP));
            return recipe;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
