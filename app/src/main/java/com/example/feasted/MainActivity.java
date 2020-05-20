package com.example.feasted;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;



    ProgressDialog progressDialog;
    ArrayAdapter<String> arrayAdapter;
    MyAdapter myAdapter;
    private SectionStagePagerAdapter sectionStagePagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
        sectionStagePagerAdapter = new SectionStagePagerAdapter(getSupportFragmentManager());


//        setContentView(R.layout.activity_main);
//
//
//        viewPager = (ViewPager) findViewById(R.id.container);
//        setupViewPager(viewPager);
//
//        recyclerView = findViewById(R.id.recyclerView);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
//        recyclerView.setLayoutManager(gridLayoutManager);
//
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading Items....");
//
//        myFoodList = new ArrayList<>();
//
//        myAdapter = new MyAdapter(MainActivity.this, myFoodList);
//        recyclerView.setAdapter(myAdapter);
//
//        dbRef = FirebaseDatabase.getInstance().getReference("Recipe");
//        progressDialog.show();
//        eventListener = dbRef.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                myFoodList.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    FoodMeta meta = snapshot.getValue(FoodMeta.class);
//                    myFoodList.add(meta);
//                }
//
//                myAdapter.notifyDataSetChanged();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                progressDialog.dismiss();
//            }
//        });
    }

        private void setupViewPager(ViewPager viewPager) {
            SectionStagePagerAdapter adapter = new SectionStagePagerAdapter(getSupportFragmentManager());

            adapter.addFragment(new StartScreenFragment(), "Start Screen");
            adapter.addFragment(new RecipeUploaderFragment(), "Uploader Fragment");
            adapter.addFragment(new RecipeFragment(), "Recipe Fragment");

            viewPager.setAdapter(adapter);
        }



        public void setViewPager(int fragmentNumber){
            viewPager.setCurrentItem(fragmentNumber);
        }


//

//

//    }
}
