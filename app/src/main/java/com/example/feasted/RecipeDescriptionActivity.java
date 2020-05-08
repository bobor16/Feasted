package com.example.feasted;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDescriptionActivity extends AppCompatActivity {

    TextView description;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);

        description = findViewById(R.id.recipeDescription);
        img = findViewById(R.id.detailed_image);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            description.setText(bundle.getString("Description"));
            img.setImageResource(bundle.getInt("Image"));

        }
    }
}
