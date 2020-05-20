package com.example.feasted;

import android.widget.EditText;

import java.util.ArrayList;

public class FoodMeta {

    private String name, description, img, type;

    public FoodMeta() {
    }

    public FoodMeta(String name, String description, String img, String type) {
        this.name = name;
        this.description = description;
        this.img = img;
        this.type = type;
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

    public String getType() { return type; }

}
