package com.example.feasted;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class RecipeUploaderFragment extends Fragment {

    private ImageView recipeImage;
    private Uri uri;
    private EditText upload_description, recipeName, ingredient;
    private String imageUrl;
    private RadioButton vegan_Button, lchf_Button;

    private Object Button;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_recipe_fragment, container, false);

        Button btnSelectImage = view.findViewById(R.id.selectImageButton);
        Button btnUploadRecipe = view.findViewById(R.id.uploadRecipeButton);

        recipeImage = view.findViewById(R.id.uploadImage);
        recipeName = view.findViewById(R.id.recipeName);
        upload_description = view.findViewById(R.id.upload_description);
        vegan_Button = view.findViewById(R.id.vegan_Button);
        lchf_Button = view.findViewById(R.id.lchf_Button);
        ingredient = view.findViewById(R.id.upload_ingredients);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        btnUploadRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            recipeImage.setImageURI(uri);
        } else {
            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    public void backToStart(View v) {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        StartScreenFragment startScreenFragment = new StartScreenFragment();

        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.rc, startScreenFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void uploadImage() {
        if (uri != null) {
            AppCompatActivity activity = (AppCompatActivity) getContext();
            StartScreenFragment startScreenFragment = new StartScreenFragment();

            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.rc, startScreenFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("RecipeImage").child(uri.getLastPathSegment());

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Recipe Uploading....");
            progressDialog.show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    uploadRecipe();
//                    ((MainActivity) getActivity()).setViewPager(0);
                    progressDialog.dismiss();
                    System.out.println("THIS IS THE IMAGE URL: " + imageUrl);
                }


            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "Select an image", Toast.LENGTH_SHORT).show();
        }
    }

    public String recipeType() {
        if (vegan_Button.isChecked()) {
            return "Vegan";
        } else if (lchf_Button.isChecked()) {
            return "LCHF";
        } else {
            return "";
        }
    }

    public void uploadRecipe() {
        FoodMeta foodMeta = new FoodMeta(
                recipeName.getText().toString(),
                upload_description.getText().toString(),
                imageUrl,
                recipeType(),
                ingredient.getText().toString()
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Recipe");
        myRef.child(myCurrentDateTime).setValue(foodMeta).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Recipe Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
