package comp3350.g3.tasteBud.object;

import java.util.*;

public class Recipe {
    private String name;
    private String desc;
    private List<String> ingredients;
    private List<String> tags;

    // Class constructor
    public Recipe(String name, String desc, List<String> ingredients, List <String> tags) {
        this.name = name;
        this.desc = desc;
        this.ingredients = ingredients;
        this.tags = tags;
    }


    public String getName() {
        return this.name;
    }

    public String getdesc() {
        return this.name;
    }

    public List<String> getIngredients() {
        return this.ingredients;
    }

    public List<String> tags() {
        return this.tags;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setdesc(String desc) {
        this.desc = desc;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

