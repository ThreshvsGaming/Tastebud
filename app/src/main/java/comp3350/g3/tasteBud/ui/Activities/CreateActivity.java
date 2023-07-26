package comp3350.g3.tasteBud.ui.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Processors.ImageProcessor;
import comp3350.g3.tasteBud.logic.Processors.RecipeProcessor;
import comp3350.g3.tasteBud.logic.Validators.RecipeValidator;
import comp3350.g3.tasteBud.ui.Displays.ValidationStatusSetter;
import comp3350.g3.tasteBud.ui.HighCoupled.AndroidImageReader;


public class CreateActivity extends Fragment {
    private Button submitRecipeButton;
    private String recipeTitle;
    private String recipeDescription;
    private String recipeTags;
    private List<String> recipeIngredients;
    private RecipeProcessor recipeProcessor;
    private RecipeValidator recipeValidator;
    private TextView validationStatus;
    private ValidationStatusSetter validationStatusSetter;
    private ImageView backButton;

    private ImageView recipeImage;

    private String recipeImageUri;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_activity, container, false);

        initializeViewComponents(view);

        initializeListeners(view);

        recipeProcessor = new RecipeProcessor();
        recipeValidator = new RecipeValidator();
        validationStatusSetter = new ValidationStatusSetter();

        return view;
    }

    //For selecting, handling and viewing images
    public final ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        Intent data = result.getData();
                        assert data != null;

                        // For moving image info to phone in-memory
                        Uri selectedImage = data.getData();

                        AndroidImageReader imageReader = new AndroidImageReader(getActivity());

                        Uri fileUri = imageReader.buildImageUri(selectedImage);

                        recipeImage.setImageURI(fileUri);

                        recipeImageUri = fileUri.toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    public void handleValidation(String validationError) {
        if (validationError == null) {

            try {
                validationStatus.setText("Recipe Successfully Added!");
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.GREEN);
                recipeProcessor.addRecipe(recipeTitle, recipeDescription, recipeIngredients, recipeTags, recipeImageUri);
                new Handler().postDelayed(() -> validationStatus.setVisibility(View.INVISIBLE), 3000); //Show dialog for 3 seconds
            } catch (IllegalArgumentException e) {
                validationStatusSetter.setIllegalArgumentValidation(validationStatus, e);
            } catch (Exception e) {
                validationStatusSetter.setSystemErrorValidation(validationStatus, e);
            }
        } else {
            validationStatusSetter.setAndroidSystemFailure(validationStatus, validationError);
        }
    }

    private void initializeViewComponents(View view) {
        submitRecipeButton = view.findViewById(R.id.recipeSubmit);

        validationStatus = view.findViewById(R.id.textView2);

        backButton = view.findViewById(R.id.returnButton);

        recipeImage = view.findViewById(R.id.recipeImageDetail);
    }

    private void initializeListeners(View view) {
        submitRecipeButton.setOnClickListener(v -> {
            recipeTitle = ((EditText) view.findViewById(R.id.recipeTitle)).getText().toString();
            recipeDescription = ((EditText) view.findViewById(R.id.recipeDescription)).getText().toString();
            recipeIngredients = Arrays.asList(((EditText) view.findViewById(R.id.recipeIngredients)).getText().toString().split(","));
            recipeTags = ((EditText) view.findViewById(R.id.recipeTags)).getText().toString();
            String validationError = recipeValidator.inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
            handleValidation(validationError);
        });

        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        recipeImage.setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mGetContent.launch(intent);
        });
    }
}