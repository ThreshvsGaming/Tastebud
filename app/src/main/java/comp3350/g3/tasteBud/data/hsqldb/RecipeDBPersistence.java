package comp3350.g3.tasteBud.data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comp3350.g3.tasteBud.data.Interface.IRecipeDB;
import comp3350.g3.tasteBud.object.Recipe;

public class RecipeDBPersistence implements IRecipeDB {
    private final String dbPath;

    public RecipeDBPersistence(final String dbPath) {
        this.dbPath = dbPath;
    }


    public boolean addRecipe(Recipe recipe) {
        try (final Connection c = connection()) {
            int newID = getNextID(c); // Get the unused ID
            recipe.setId(newID);

            // Add recipe
            PreparedStatement pst = c.prepareStatement("INSERT INTO RECIPES VALUES(?,?,?,?,?,?)");
            pst.setInt(1, recipe.getId());
            pst.setString(2, recipe.getName());
            pst.setString(3, recipe.getDesc());
            pst.setString(4, UtilitiesForDB.encoding(recipe.getIngredients()));
            pst.setString(5, UtilitiesForDB.encoding(recipe.getTags()));
            pst.setString(6, recipe.getImageUri());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        }
    }


    public boolean addRatings(int recipeId, int ratings) {
        try (final Connection c = connection()) {
            PreparedStatement pst = c.prepareStatement("UPDATE RATING SET RATINGS = ? WHERE RECIPE_ID = ?");
            pst.setInt(1, ratings);
            pst.setInt(2, recipeId);
            int updated = pst.executeUpdate();

            //Meaning no previous entry for this particular Recipe, then insert a new rating
            if (updated == 0) {
                pst = c.prepareStatement("INSERT INTO RATING (RECIPE_ID, RATINGS) VALUES (?, ?)");
                pst.setInt(1, recipeId);
                pst.setInt(2, ratings);
                pst.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        }
    }

    public int getRating(int id) {

        try (final Connection c = connection()) {

            final PreparedStatement st = c.prepareStatement("SELECT RATINGS FROM RATING WHERE RECIPE_ID=?");
            st.setInt(1, id);
            final ResultSet rs = st.executeQuery();
            int rating;
            if (rs.next()) {
                rating = rs.getInt("RATINGS");
            } else {
                rating = -1; // Or any other indicator of no rating found.
            }
            rs.close();
            st.close();
            return rating;
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        }
    }


    public Recipe getRecipe(int id) {

        try (final Connection c = connection()) {

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
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        }
    }


    public ArrayList<Recipe> getAllRecipes() {
        final ArrayList<Recipe> recipes = new ArrayList<>();
        try (final Connection c = connection()) {

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
            throw new ExceptionHandler(e);
        }
    }


    public void deleteRecipe(int id) {
        try (final Connection c = connection()) {
            //Delete ratings, if there are any, before proceeding to delete recipes
            deleteRatings(id);

            final PreparedStatement st = c.prepareStatement("DELETE FROM RECIPES WHERE ID=?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        }
    }

    public void deleteRatings(int id) {
        try (final Connection c = connection()) {

            final PreparedStatement st = c.prepareStatement("DELETE FROM RATING WHERE RECIPE_ID=?");
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        }
    }


    public void updateRecipe(Recipe recipe) {
        try (final Connection c = connection()) {

            final PreparedStatement st = c.prepareStatement("UPDATE RECIPES SET NAME=?, DESC=?,  INGREDIENTS=?, TAGS=?, IMAGEPATH=? WHERE ID=?");
            st.setString(1, recipe.getName());
            st.setString(2, recipe.getDesc());
            st.setString(3, UtilitiesForDB.encoding(recipe.getIngredients()));
            st.setString(4, UtilitiesForDB.encoding(recipe.getTags()));
            st.setString(5, recipe.getImageUri());
            st.setInt(6, recipe.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            throw new ExceptionHandler(e);
        }
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Recipe fromResultSet(final ResultSet rs) throws SQLException {

        final int id = rs.getInt("ID");
        final String name = rs.getString("NAME");
        final String description = rs.getString("DESC");
        final String ingredientsString = rs.getString("INGREDIENTS");
        final List<String> ingredients = Arrays.asList(ingredientsString.split(",")); // 拆分字符串并转换为List
        String tags = rs.getString("TAGS");
        final String imagePath = rs.getString("IMAGEPATH");

        try {
            Recipe recipe = new Recipe(name, description, ingredients);
            // fill other fields
            recipe.setId(id);
            recipe.dbsetTags(tags.split(UtilitiesForDB.FIELD_SEP));
            recipe.setImageUri(imagePath);
            return recipe;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private int getNextID(Connection connection) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT MAX(ID) FROM RECIPES");
        int newID = 1;
        if (rs.next()) {
            newID = rs.getInt(1) + 1;
        }
        rs.close();
        st.close();
        return newID;
    }
}
