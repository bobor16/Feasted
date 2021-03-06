package com.example.feasted;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolderFood> {

    private Context mContext;
    private List<FoodMeta> myFoodList;
    private int lastPos = -1;

    /**
     * Constructor of the class. Takes context and a list of type FoodMeta
     *
     * @param context
     * @param myFoodList
     */
    public MyAdapter(Context context, List<FoodMeta> myFoodList) {
        this.mContext = context;
        this.myFoodList = myFoodList;
    }

    /**
     * inflates recycler_row_item
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolderFood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new ViewHolderFood(view);
    }

    /**
     * Displays the data to the recyclerview.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolderFood holder, final int position) {
        Bundle bundle = new Bundle();
        Picasso.get().load(myFoodList.get(position).getImg()).into(holder.imageView);
        holder.mTitle.setText(myFoodList.get(position).getName());
        holder.mDescription.setText(myFoodList.get(position).getDescription());
        holder.mDetailedType.setText(myFoodList.get(position).getType());

        /**
         * Displays the data to the user when cardview is clicked.
         */
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                RecipeFragment recipeFragment = new RecipeFragment();
                bundle.putString("Image", myFoodList.get(holder.getAdapterPosition()).getImg());
                bundle.putString("Description", myFoodList.get(holder.getAdapterPosition()).getDescription());
                bundle.putString("Ingredients", myFoodList.get(holder.getAdapterPosition()).getIngredient());
                recipeFragment.setArguments(bundle);

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                transaction.replace(R.id.rc, recipeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        setAnimation(holder.itemView, position);
    }

    /**
     * Animation for the cardview when loaded into the view.
     *
     * @param viewToAnimate
     * @param pos
     */
    public void setAnimation(View viewToAnimate, int pos) {
        if (pos > lastPos) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(500);
            viewToAnimate.startAnimation(animation);
            lastPos = pos;
        }
    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }

    public void filteredList(ArrayList<FoodMeta> filterList) {
        myFoodList = filterList;
        notifyDataSetChanged();
    }

    /**
     * Nested class which holds the references from the user input
     */
    class ViewHolderFood extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView mTitle, mDescription, mDetailedType, mIngredient;
        CardView mCardView;
        SearchView mSearch;

        public ViewHolderFood(View itemView) {
            super(itemView);

            mIngredient = itemView.findViewById(R.id.detailedIngredient);
            imageView = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.description);
            mCardView = itemView.findViewById(R.id.cardView);
            mDetailedType = itemView.findViewById(R.id.detailed_type);
            mSearch = itemView.findViewById(R.id.search_icon);
        }
    }


}
