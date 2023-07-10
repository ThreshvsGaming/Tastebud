package comp3350.g3.tasteBud.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.RecipeProcessor;


public class CreateActivity extends Fragment {
    private Button submitRecipeButton;
    private String recipeTitle;
    private String recipeDescription;
    private String recipeTags;
    private List<String> recipeIngredients;
    private RecipeProcessor recipeProcessor;
    private TextView validationStatus;
    private ImageView backButton;

    private ImageView recipeImage;

    private String recipeImageUri;


    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_activity, container, false);

        submitRecipeButton = view.findViewById(R.id.recipeSubmit);

        validationStatus = view.findViewById(R.id.textView2);

        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());

        backButton = view.findViewById(R.id.returnButton);

        recipeImage = view.findViewById(R.id.recipeImageDetail);


        submitRecipeButton.setOnClickListener(v -> {
            recipeTitle = ((EditText) view.findViewById(R.id.recipeTitle)).getText().toString();
            recipeDescription = ((EditText) view.findViewById(R.id.recipeDescription)).getText().toString();
            //recipeIngredients = ((EditText) view.findViewById(R.id.recipeIngredients)).getText().toString();
            recipeIngredients = Arrays.asList(((EditText) view.findViewById(R.id.recipeIngredients)).getText().toString().split(","));
            recipeTags = ((EditText) view.findViewById(R.id.recipeTags)).getText().toString();
            String validationError = recipeProcessor.inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
            handleValidation(validationError);
        });

        backButton.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mGetContent.launch(intent);
            }
        });

        return view;
    }

    //For selecting, handling and viewing images in Create Activity
    private final ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                getActivity();
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        Intent data = result.getData();
                        assert data != null;
                        Uri selectedImage = data.getData();
                        File saveDirectory = Objects.requireNonNull(getActivity()).getFilesDir();
                        int fileNumber = 0;
                        File saveFile;

                        // Get InputStream from the Uri
                        InputStream is = getActivity().getContentResolver().openInputStream(selectedImage);


                        //Keep incrementing the image number so that we can have multiple images stored in the local app storage
                        do {
                            saveFile = new File(saveDirectory, "recipe_Image" + fileNumber++ + ".jpg");
                        }
                        while (saveFile.exists());


                        // Copy InputStream to the File
                        try (OutputStream os = new FileOutputStream(saveFile)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = is.read(buffer)) > 0) {
                                os.write(buffer, 0, length);
                            }
                        }

                        // Close the InputStream
                        is.close();

                        // Get the Uri from the File that we just saved
                        Uri fileUri = Uri.fromFile(saveFile);

                        // Display the image
                        recipeImage.setImageURI(fileUri);

                        // Save the Uri as a string
                        recipeImageUri = fileUri.toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    public void handleValidation(String validationError) {
        if (validationError == null) {

            try {
                //recipeProcessor.addRecipes(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
                validationStatus.setText("Recipe Successfully Added!");
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.GREEN);
                recipeProcessor.addRecipe(recipeTitle, recipeDescription, recipeIngredients, recipeTags, recipeImageUri);
                new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 3000); //Show dialog for 3 seconds
            } catch (IllegalArgumentException e) {
                validationStatus.setText("Recipe Creation Failed: " + e.getMessage());
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.RED);
                new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 10000); //Show dialog for 10 seconds
            } catch (Exception e) {
                validationStatus.setText("System Error: " + e.getMessage());
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.RED);
                new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 10000); //Show dialog for 10 seconds
            }
        } else {
            validationStatus.setText(validationError);
            validationStatus.setVisibility(View.VISIBLE);
            validationStatus.setTextColor(Color.RED);
            new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 3000); //Show dialog for 3 seconds
        }
    }
}