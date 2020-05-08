package com.example.feasted;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<FoodMeta> myFoodList;
    FoodMeta mFoodMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, myFoodList);
        recyclerView.setAdapter(myAdapter);
    }

    public void btn_uploadActivity(View view) {
        startActivity(new Intent(this, RecipeUploaderActivity.class));

    }
}
