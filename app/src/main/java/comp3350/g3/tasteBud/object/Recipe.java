package comp3350.g3.tasteBud.object;

import java.io.Serializable;

public class Recipe implements Serializable {
    private int id;
    private String name;
    private String desc;
    private String[] ingredients;
    private String[] tags;

    public Recipe(int id, String name, String desc, String[] ingredients, String[] tags) {
        this.id = id;
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
        return this.id;
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
}

