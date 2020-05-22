package com.example.feasted;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private TextView description, type, ingredient;
    private ImageView img;
    MyAdapter myAdapter;
    List<FoodMeta> myFoodList;


    public RecipeFragment newInstance(String data) {
        RecipeFragment fragment = new RecipeFragment();

        Bundle args = new Bundle();
        args.putString("Data", data);
        fragment.setArguments(args);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipefragment, container, false);

        Bundle bundle = getArguments();

        description = view.findViewById(R.id.detailedDescription);
        img = view.findViewById(R.id.detailedImage);
        ingredient = view.findViewById(R.id.detailedIngredient);

        if (bundle != null) {
            Picasso.get().load(bundle.getString("Image")).into(img);
//            Picasso.get().load("Image").into(img);
            description.setText(bundle.getString("Description"));
            ingredient.setText(bundle.getString("Ingredients"));
//            description.setText("Decsription");
        }
        return view;
    }

}
