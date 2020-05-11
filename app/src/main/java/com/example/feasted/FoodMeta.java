package com.example.feasted;

public class FoodMeta {

    private String name, description, img;

    public FoodMeta() {
    }

    public FoodMeta(String name, String description, String img) {
        this.name = name;
        this.description = description;
        this.img = img;
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

}
