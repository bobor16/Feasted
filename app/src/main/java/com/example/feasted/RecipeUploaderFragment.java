package com.example.feasted;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import java.util.Calendar;
import java.util.Date;

public class RecipeUploaderFragment extends Fragment {

    private ImageView recipeImage;
    private Uri uri;
    private EditText upload_description, recipeName, ingredient;
    private String imageUrl;
    private RadioButton vegan_Button, lchf_Button;

    /**
     * Creates the view which enables the user to upload new recipes to the database and furthermore
     * displays the data to the user.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upload_recipe_fragment, container, false);
        setHasOptionsMenu(true);
        Button btnSelectImage = view.findViewById(R.id.selectImageButton);
        Button btnUploadRecipe = view.findViewById(R.id.uploadRecipeButton);

        recipeImage = view.findViewById(R.id.uploadImage);
        recipeName = view.findViewById(R.id.recipeName);
        upload_description = view.findViewById(R.id.upload_description);
        vegan_Button = view.findViewById(R.id.vegan_Button);
        lchf_Button = view.findViewById(R.id.lchf_Button);
        ingredient = view.findViewById(R.id.upload_ingredients);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            /**
             * Enables the user to select an image from local storage
             * with permission from the manifest
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        btnUploadRecipe.setOnClickListener(new View.OnClickListener() {
            /**
             * Uploads the image by tapping the button.
             * @param v
             */
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        return view;
    }

    /**
     * Ensures the search and actionbar is invisible for the user when the user is displayed
     * the upload fragment.
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    /**
     * Ensures the user has selected an image to upload.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * Redirects the user back to the start screen.
     */
    public void backToStart() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        StartScreenFragment startScreenFragment = new StartScreenFragment();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.rc, startScreenFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * uploads the image selected to the database with a reference to "RecipeImage" child.
     * prompts the user a progress dialog bar.
     */
    public void uploadImage() {
        if (uri != null) {

            backToStart();

            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("RecipeImage").child(uri.getLastPathSegment());

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Recipe Uploading....");
            progressDialog.show();

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                /**
                 * gets the url where the image is to be uploaded. Uploads the image and the recipe
                 * @param taskSnapshot
                 */
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
                /**
                 * Dismisses the progress dialog message.
                 * prompts a toast to the user with further information.
                 * @param e
                 */
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Select an image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Switch case which returns the type of recipes the user wants uploaded.
     *
     * @return
     */
    public String recipeType() {
        if (vegan_Button.isChecked()) {
            return "Vegan";
        } else if (lchf_Button.isChecked()) {
            return "LCHF";
        } else {
            return "";
        }
    }

    /**
     * Creates an object of FoodMeta which is to be uploaded to the database with reference to
     * parent (currentTime), child (Recipe). structured in json format.
     */
    public void uploadRecipe() {
        FoodMeta foodMeta = new FoodMeta(
                recipeName.getText().toString(),
                upload_description.getText().toString(),
                imageUrl,
                recipeType(),
                ingredient.getText().toString()
        );

        Date currentTime = Calendar.getInstance().getTime();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Recipe")
                .child(currentTime.toString())
                .setValue(foodMeta)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    /**
                     * Prompts the user a toast when upload is completed.
                     * @param task
                     */
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Recipe Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            /**
             * Prompts the user a toast if the upload fails.
             * @param e
             */
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
