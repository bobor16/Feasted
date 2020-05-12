package com.example.feasted;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class RecipeDescriptionActivity extends AppCompatActivity {

    TextView description, type;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_description);

        description = findViewById(R.id.recipeDescription);
        img = findViewById(R.id.detailed_image);
        type = findViewById(R.id.detailed_type);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            description.setText(bundle.getString("Description"));
            Picasso.get().load(bundle.getString("Image")).into(img);

        }
    }
}
