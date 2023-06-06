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
    public Recipe(String name, String desc, String[] ingredients, String[] tags) {
        this.id = -1;     //placeholder id
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

    public String getDesc() {
        return this.desc;
    }

    public String[] getIngredients() {
        return this.ingredients;
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}

