package com.example.feasted;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StartScreenFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<FoodMeta> myFoodList;
    private MyAdapter myAdapter;
    private DatabaseReference dbRef;
    private ValueEventListener eventListener;
    private CardView cardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.startscreenfragment, container, false);
        FloatingActionButton btn_uploadActivity = (FloatingActionButton) view.findViewById(R.id.uploadButton);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();

        myAdapter = new MyAdapter(view.getContext(), myFoodList);

        recyclerView.setAdapter(myAdapter);


        dbRef = FirebaseDatabase.getInstance().getReference("Recipe");

//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity) getActivity()).setViewPager(2);
//
//            }
//        });


        btn_uploadActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MainActivity) getActivity()).setViewPager(1);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                RecipeUploaderFragment recipeUploaderFragment = new RecipeUploaderFragment();

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.rc, recipeUploaderFragment);
                transaction.addToBackStack(null);
                transaction.commit();


                Toast.makeText(getActivity(), "Opening new fragment", Toast.LENGTH_SHORT).show();
//                System.out.println(myFoodList.get(position).getImg() + "\n" + myFoodList.get(position).getDescription());

            }
        });

        eventListener = dbRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myFoodList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodMeta meta = snapshot.getValue(FoodMeta.class);
                    myFoodList.add(meta);
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void filter(String text) {
        ArrayList<FoodMeta> filterList = new ArrayList<>();
        for (FoodMeta item : myFoodList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            } else if (item.getType().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        myAdapter.filteredList(filterList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_lchf:
                filter("lchf");
                break;
            case R.id.sort_vegan:
                filter("vegan");
                break;
            case R.id.show_all:
                filter("");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.my_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

}

