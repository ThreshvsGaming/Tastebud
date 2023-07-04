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

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + desc + '\'' +
                ", directions=" + ingredients +
                ", tags=" + tags +
                '}';
    }

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
        this(name, desc, ingredients );

        setTags(tags);
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


}

