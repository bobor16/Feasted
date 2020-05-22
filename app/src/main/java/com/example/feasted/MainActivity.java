package com.example.feasted;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

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
//        adapter.addFragment(new RecipeUploaderFragment(), "Uploader Fragment");
//        adapter.addFragment(new RecipeFragment(), "Recipe Fragment");

        viewPager.setAdapter(adapter);
    }


    public void setViewPager(int fragmentNumber) {
        viewPager.setCurrentItem(fragmentNumber);
    }

}
