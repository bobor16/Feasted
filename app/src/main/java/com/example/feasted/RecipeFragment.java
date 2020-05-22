package com.example.feasted;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class RecipeFragment extends Fragment {

    private TextView description, ingredient;
    private ImageView img;
    private Menu menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipefragment, container, false);
        Bundle bundle = getArguments();
        setHasOptionsMenu(true);
        description = view.findViewById(R.id.detailedDescription);
        img = view.findViewById(R.id.detailedImage);
        ingredient = view.findViewById(R.id.detailedIngredient);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (bundle != null) {
            Picasso.get()
                    .load(bundle.getString("Image"))
                    .into(img);
            description.setText(bundle.getString("Description"));
            ingredient.setText(bundle.getString("Ingredients"));
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
