package com.example.feasted;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipes> mRecipes;

    public RecipesAdapter(List<Recipes> recipes) {
        mRecipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View reciepView = inflater.inflate(R.layout.item_reciep, parent, false);

        ViewHolder viewHolder = new ViewHolder(reciepView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipes recipes = mRecipes.get(position);

        TextView textView = holder.nameTextView;
        textView.setText(recipes.getmName());
        Button button = holder.reciepButton;
        button.setText(recipes.isVegan() ? "Vegan" : "Not Vegan");
        button.setEnabled(recipes.isVegan());
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public Button reciepButton;


        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            reciepButton = (Button) itemView.findViewById(R.id.recipe_button);

        }
    }
}
