package comp3350.g3.tasteBud.object;

import java.util.*;

public class Recipe {

    private int id;
    private String name;
    private String desc;
    private String[] ingredients;
    private String[] tags;

    // Class constructor
    public Recipe(int id, String name, String desc, String[] ingredients, String[] tags) {
        this.name = name;
        this.desc = desc;
        this.ingredients = ingredients;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getdesc() {
        return this.name;
    }

    public String[] getIngredients() {
        return this.ingredients;
    }

    public String[] tags() {
        return this.tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setdesc(String desc) {
        this.desc = desc;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}

