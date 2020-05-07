package com.example.feasted;

import java.util.ArrayList;

public class Recipes {
    private String mName;
    private boolean mVegan;

    public Recipes(String name, boolean vegan) {
        mVegan = vegan;
        mName = name;
    }

    public String getmName() {
        return mName;
    }

    public boolean isVegan() {
        return mVegan;
    }

    private static int lastContactId = 0;

    public static ArrayList<Recipes> createContactList(int numRecipes) {
        ArrayList<Recipes> recipes = new ArrayList<>();

        for (int i = 1; i <= numRecipes; i++) {
            recipes.add(new Recipes("Recipe " + lastContactId++, i <= numRecipes / 2));
        }
        return recipes;
    }
}
