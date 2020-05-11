package com.example.feasted;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<FoodMeta> myFoodList;
    private DatabaseReference dbRef;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Items....");

        myFoodList = new ArrayList<>();

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, myFoodList);
        recyclerView.setAdapter(myAdapter);

        dbRef = FirebaseDatabase.getInstance().getReference("Recipe");
        progressDialog.show();
        eventListener = dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myFoodList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodMeta meta = snapshot.getValue(FoodMeta.class);
                    myFoodList.add(meta);
                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }

    public void btn_uploadActivity(View view) {
        startActivity(new Intent(this, RecipeUploaderActivity.class));

    }
}
