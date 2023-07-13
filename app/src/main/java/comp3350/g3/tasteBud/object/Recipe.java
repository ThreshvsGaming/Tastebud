package comp3350.g3.tasteBud.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Recipe implements Serializable {
    public static final int UNSET_ID = -1;
    private int id;
    private String name;
    private String desc;
    private List<String> ingredients;
    private List<String> tags;
    private String imageUri;


    public Recipe(String name, String desc, List ingredients) throws IllegalArgumentException {
        if (desc == null) {
            desc = "";
        }

        if (tags == null) {
            this.tags = new LinkedList<>();
        }

        this.id = UNSET_ID;
        this.name = name;
        this.desc = desc;
        this.ingredients = ingredients;
    }

    public Recipe(String name, String desc, List ingredients, String tags) throws IllegalArgumentException {
        this(name, desc, ingredients);

        setTags(tags);
    }

    public Recipe(String name, String desc, List ingredients, String tags, String imageUri) throws IllegalArgumentException {
        this(name, desc, ingredients, tags);

        // Initialize imageUri field
        this.imageUri = imageUri;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws IllegalArgumentException, IllegalStateException {
        if (this.id != UNSET_ID) {
            throw new IllegalStateException("Cannot set an already set recipe id");
        }
        this.id = id;
    }

    public void setTags(String tags) {
        this.tags = new LinkedList<>();

        if (tags != null && !tags.isEmpty()) {
            String[] tempTags = tags.split(",");
            for (String i : tempTags) {
                i = i.trim();
                if (!i.equals("")) {
                    this.tags.add("" + i);
                }
            }
        }
    }

    //for handling db operation regarding splitting tags
    public void dbsetTags(String[] tags) {
        this.tags = new LinkedList<>();
        if (tags != null) {
            for (String i : tags) {
                i = i.trim();
                if (!i.equals("")) {
                    this.tags.add(i);
                }
            }
        }
    }

    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}


