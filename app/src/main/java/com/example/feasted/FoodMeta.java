package com.example.feasted;

public class FoodMeta {

    private String name, description, img, type, ingredient;

    public FoodMeta() {
    }


    public FoodMeta(String name, String description, String img, String type, String ingredient) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.type = type;
        this.ingredient = ingredient;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public String getType() {
        return type;
    }

    public String getIngredient() {
        return ingredient;
    }

}
