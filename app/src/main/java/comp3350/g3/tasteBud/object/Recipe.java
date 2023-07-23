package comp3350.g3.tasteBud.object;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
        return formatIngredients();
    }

    private List<String> formatIngredients(){
        //the database stores new recipe's ingredients as a string (done to avoid a more complicated database structure)
        //thus, pushing ingredients initially as a list converts them to a format of: "ingredient1\ningredient2" or "ingredient1\n ingredient2"
        //thus, when retrieving, we return them in a nicely formatted way, free of \n and actually as a list instead of a string
        String ingredient = ingredients.get(0);
        List<String> formattedIngredients;

        //see if ingredient already previously formatted,
        //if not(as shown by "\n" in ingredient) format it
        //else just return the formatted ingredient list
        if(ingredient.contains("\n")){
            formattedIngredients = Arrays.asList(ingredient.replaceAll(" ", "").split("\\r?\\n"));
        }
        else{
            formattedIngredients = ingredients;
        }

        return formattedIngredients;
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


