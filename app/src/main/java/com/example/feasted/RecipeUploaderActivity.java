package com.example.feasted;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class RecipeUploaderActivity extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText upload_description, recipeName;
    String imageUrl;
    RadioButton vegan_Button, lchf_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_recipe);

        recipeImage = findViewById(R.id.uploadImage);
        recipeName = findViewById(R.id.recipeName);
        upload_description = findViewById(R.id.upload_description);
        vegan_Button = findViewById(R.id.vegan_Button);
        lchf_Button = findViewById(R.id.lchf_Button);
    }

    public void btnSelectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            uri = data.getData();
            recipeImage.setImageURI(uri);

        } else {
            Toast.makeText(this, "Please pick an image", Toast.LENGTH_SHORT).show();
        }
    }

    public void uploadImage() {
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference().child("RecipeImage").child(uri.getLastPathSegment());

        ProgressDialog progressDialog = new ProgressDialog(this);
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
                progressDialog.dismiss();
                System.out.println("THIS IS THE IMAGE URL: " + imageUrl);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });
    }

    public void recipeBtnUpload(View view) {
        uploadImage();
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
                recipeType()
        );

        String myCurrentDateTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Recipe");
        myRef.child(myCurrentDateTime).setValue(foodMeta).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RecipeUploaderActivity.this, "Recipe Uploaded", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RecipeUploaderActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
