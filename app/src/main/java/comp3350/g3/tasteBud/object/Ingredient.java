package comp3350.g3.tasteBud.object;


import java.io.Serializable;

//public class Ingredient implements Serializable
//{
//    private static final long serialVersionUID = 1L;
//    private String name;
//    private float quantity;
//    private IngredientUnits unit;
//
//    public Ingredient(String name, float quantity, IngredientUnits unit) throws IllegalArgumentException
//    {
//        // validate parameters
//        new IngredientValidator().isValidOrThrowException(name,quantity,unit);
//
//        this.name = name;
//        this.quantity = quantity;
//        this.unit = unit;
//    }
//
//    public float getQuantity()
//    {
//        return quantity;
//    }
//
//    public IngredientUnits getUnit()
//    {
//        return unit;
//    }
//
//    public String getName()
//    {
//        return name;
//    }
//
//    public void setName(String name)
//    {
//        this.name=name;
//    }
//
//    public void setUnit(IngredientUnits unit){this.unit=unit;}
//
//    public void setQuantity(float quantity){this.quantity=quantity;}
//}


public class Ingredient implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private float quantity;
    private IngredientUnits unit;

    public Ingredient(String[] data) {
        if (data.length >= 3) {
            this.name = data[0];
            this.quantity = Float.parseFloat(data[1]);
            this.unit = IngredientUnits.valueOf(data[2]);
        } else {
            throw new IllegalArgumentException("Invalid data array for Ingredient");
        }
    }

    public String getName() {
        return name;
    }

    public float getQuantity() {
        return quantity;
    }

    public IngredientUnits getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setUnit(IngredientUnits unit) {
        this.unit = unit;
    }
}








