package com.example.feasted;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedRecipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedRecipe extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private TextView textViewFragment;
    private ImageView imageViewFragment;
    private CardView cardView;

    public DetailedRecipe() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailedRecipe newInstance(String param1) {
        DetailedRecipe fragment = new DetailedRecipe();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detailed_recipe, container, false);

        textViewFragment = view.findViewById(R.id.fragment_recipeDescription);
        imageViewFragment = view.findViewById(R.id.fragment_detailed_image);
        textViewFragment.setText(mParam1);
        textViewFragment.requestFocus();


        return view;
    }
}
