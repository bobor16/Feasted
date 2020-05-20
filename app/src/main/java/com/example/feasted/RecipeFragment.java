package com.example.feasted;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class RecipeFragment extends Fragment {

    TextView description, type;
    ImageView img;
    FoodMeta foodMeta;

    public RecipeFragment newInstance(String data){
        RecipeFragment fragment = new RecipeFragment();

        Bundle args = new Bundle();
        args.putString("Data", data);
        fragment.setArguments(args);

        return fragment;
    }

    public int getIndex(){
        return getArguments().getInt("Index", 0);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipefragment, container, false);
        foodMeta = new FoodMeta();
        description = view.findViewById(R.id.recipeDescription);
        img = view.findViewById(R.id.detailed_image);
        type = view.findViewById(R.id.detailed_type);

        Bundle bundle = getArguments();
        if(bundle != null){
            Picasso.get().load(foodMeta.getImg()).into(img);
            //Picasso.get().load(bundle.getString("Image")).into(img);
            description.setText(bundle.getString("Description"));
//            Picasso.get().load("Image").into(img);
//            description.setText("Decsription");
        }

        return view;
    }

//        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recipe_description);
//
//        description = findViewById(R.id.recipeDescription);
//        img = findViewById(R.id.detailed_image);
//        type = findViewById(R.id.detailed_type);
//
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            Picasso.get().load(bundle.getString("Image")).into(img);
//            description.setText(bundle.getString("Description"));
//        }
//    }
}
