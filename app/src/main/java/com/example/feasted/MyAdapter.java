package com.example.feasted;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolderFood> {

    private Context mContext;
    private List<FoodMeta> myFoodList;

    public MyAdapter(Context context, List<FoodMeta> myFoodList) {
        this.mContext = context;
        this.myFoodList = myFoodList;
    }

    @NonNull
    @Override
    public ViewHolderFood onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new ViewHolderFood(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderFood holder, final int position) {

//        Glide.with(mContext).load(myFoodList.get(position).getImg()).into(holder.imageView);
        Picasso.get().load(myFoodList.get(position).getImg()).into(holder.imageView);
//        holder.imageView.setImageResource(Integer.parseInt(myFoodList.get(position).getImg()));
        holder.mTitle.setText(myFoodList.get(position).getName());
        holder.mDescription.setText(myFoodList.get(position).getDescription());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDescriptionActivity.class);
                intent.putExtra("Image", myFoodList.get(holder.getAdapterPosition()).getImg());
                intent.putExtra("Description", myFoodList.get(holder.getAdapterPosition()).getDescription());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myFoodList.size();
    }

    class ViewHolderFood extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mTitle, mDescription;
        CardView mCardView;


        public ViewHolderFood(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivImage);
            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.description);
            mCardView = itemView.findViewById(R.id.cardView);
        }
    }
}
