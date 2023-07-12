package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

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
import comp3350.g3.tasteBud.object.Recipe;

public class EditActivity extends FragmentActivity {
    // TextViews that populate the DetailActivity with the recipe currently being updated
    TextView previewRecipeTitle;
    TextView previewRecipeDescription;
    TextView previewRecipeTags;
    TextView previewRecipeIngredients;
    ImageView previewRecipeImage;

    // Stores what the user changes in the page
    String recipeTitle;
    String recipeDescription;
    String recipeTags;
    List<String> recipeIngredients;
    String recipeImageUri;


    int recipeId;
    private TextView validationStatus;
    private RecipeProcessor recipeProcessor;
    private Button submitRecipeButton;



    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Connect with Layout File:"detail_activity"
        setContentView(R.layout.create_activity);
        previewRecipeTitle = findViewById(R.id.recipeTitle);
        previewRecipeTags = findViewById(R.id.recipeTags);
        previewRecipeIngredients = findViewById(R.id.recipeIngredients);
        previewRecipeDescription = findViewById(R.id.recipeDescription);
        previewRecipeImage = findViewById(R.id.recipeImageDetail);

        submitRecipeButton = findViewById(R.id.recipeSubmit);

        validationStatus = findViewById(R.id.textView2);
        //Create the instance of Recipe to get information of each recipe
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("bean");
        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());
        previewRecipeTitle.setText(recipe.getName());
        previewRecipeDescription.setText(recipe.getDesc());
        recipeId = recipe.getId();
        recipeImageUri = recipe.getImageUri();

        String tagCollection = "";
        for (int n = 0; n < recipe.getTags().size(); n++) {
            String tag = TextUtils.isEmpty(tagCollection) ? "" : "" + tagCollection + ",";
            tagCollection = tag + (recipe.getTags().get(n)); //to get appropriately indexed tag
        }
        previewRecipeTags.setText(tagCollection);

        String ingredientsCollection = "";
        for (int n = 0; n < recipe.getIngredients().size(); n++) {
            String ingredients = TextUtils.isEmpty(ingredientsCollection) ? "" : "" + ingredientsCollection + ",";
            ingredientsCollection = ingredients + (recipe.getIngredients().get(n)); //to get appropriately indexed ingredients
        }
        previewRecipeIngredients.setText(ingredientsCollection);

        // Updates the selected recipe
        submitRecipeButton.setOnClickListener(v -> {
            recipeTitle = ((EditText) findViewById(R.id.recipeTitle)).getText().toString();
            recipeDescription = ((EditText) findViewById(R.id.recipeDescription)).getText().toString();
            recipeIngredients = Arrays.asList(((EditText) findViewById(R.id.recipeIngredients)).getText().toString().split(","));
            recipeTags = ((EditText) findViewById(R.id.recipeTags)).getText().toString();
            String validationError = recipeProcessor.inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
            handleValidation(validationError);
        });

        previewRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                mGetContent.launch(intent);
            }
        });


        previewRecipeImage.setImageURI(Uri.parse(recipe.getImageUri()));

        findViewById(R.id.returnButton).setOnClickListener(v -> finish());

    }

    //For selecting, handling and viewing images in Create Activity
    private final ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        Intent data = result.getData();
                        assert data != null;
                        Uri selectedImage = data.getData();
                        File saveDirectory = Objects.requireNonNull(this).getFilesDir();
                        int fileNumber = 0;
                        File saveFile;

                        // Get InputStream from the Uri
                        InputStream is = this.getContentResolver().openInputStream(selectedImage);


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
                        previewRecipeImage.setImageURI(fileUri);

                        // Save the Uri as a string
                        recipeImageUri = fileUri.toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    // Note: refactor when possible, mostly duplicates DetailProcessor
    public void handleValidation(String validationError) {
        if (validationError == null) {
            try {
                recipeProcessor.updateRecipe(recipeId, recipeTitle, recipeDescription, recipeIngredients,recipeTags, recipeImageUri);
                setResult(RESULT_OK);
                finish();
            } catch (IllegalArgumentException e) {
                validationStatus.setText("Recipe Editing Failed: " + e.getMessage());
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
