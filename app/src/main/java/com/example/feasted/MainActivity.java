package com.example.feasted;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Recipes> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvRecipes = (RecyclerView) findViewById(R.id.rv_recipes);

        recipes = Recipes.createContactList(20);

        RecipesAdapter adapter = new RecipesAdapter(recipes);

        rvRecipes.setAdapter(adapter);

        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
    }
}
