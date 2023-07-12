package comp3350.g3.tasteBud.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe implements Serializable {
    public static final int UNSET_ID = -1;
    private int id;
    private String name;
    private String desc;
    private List<String> ingredients;
    private ArrayList<String> tags;

    private String imageUri;

    final static boolean SELECTED = true;
    final static boolean NOT_SELECTED = false;
    private boolean isSelected;

    public Recipe(String name, String desc, List ingredients) throws IllegalArgumentException {
        if (desc == null) desc = "";

        this.id = UNSET_ID;
        this.name = name;
        this.desc = desc;
        this.ingredients = ingredients;

        //   setDirections(ingredients);

        if (tags == null) {
            this.tags = new ArrayList<>();
        }

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

    public ArrayList<String> getTags() {
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
        this.tags = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            String[] tempTags = tags.split(",");

            for (int i = 0; i < tempTags.length; i++) {
                tempTags[i] = tempTags[i].trim();
                if (!tempTags[i].equals("")) {
                    this.tags.add("" + tempTags[i]);

                }
            }
        }
    }

    public void setTags(String[] tags) {
        this.tags = new ArrayList<>();
        if (tags != null) {
            for (int i = 0; i < tags.length; i++) {
                tags[i] = tags[i].trim();
                if (!tags[i].equals("")) {
                    this.tags.add(tags[i]);
                }
            }
        }
    }

    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void changeSelection(boolean selection) {
        isSelected = selection;
    }


    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    /*
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

    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + desc + '\'' +
                ", directions=" + ingredients +
                ", tags=" + tags +
                '}';
    }

     */
}


