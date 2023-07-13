package comp3350.g3.tasteBud.object;

import java.io.Serializable;


public class Ingredient implements Serializable {
    private String name;

    public Ingredient(String[] data) {
        this.name = data[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}








