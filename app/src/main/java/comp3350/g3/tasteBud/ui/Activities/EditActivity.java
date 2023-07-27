package comp3350.g3.tasteBud.ui.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Processors.ImageProcessor;
import comp3350.g3.tasteBud.logic.Processors.RecipeProcessor;
import comp3350.g3.tasteBud.logic.Validators.RecipeValidator;
import comp3350.g3.tasteBud.ui.Displays.ImageSetter;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.ui.Displays.ValidationStatusSetter;
import comp3350.g3.tasteBud.ui.HighCoupled.AndroidImageReader;

public class EditActivity extends FragmentActivity {
    private TextView previewRecipeTitle;
    private TextView previewRecipeDescription;
    private TextView previewRecipeTags;
    private TextView previewRecipeIngredients;
    private ImageView previewRecipeImage;
    private TextView validationStatus;
    private Button submitRecipeButton;

    public Recipe recipe;
    private int recipeId;
    private String recipeTitle;
    private String recipeDescription;
    private String recipeTags;
    private List<String> recipeIngredients;
    private String recipeImageUri;

    private RecipeProcessor recipeProcessor;
    private RecipeValidator recipeValidator;
    private ValidationStatusSetter validationStatusSetter;

    private ImageProcessor imageProcessor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Connect with Layout File:"detail_activity"
        setContentView(R.layout.create_activity);

        initializeViewComponents();
        initializeListeners();

        initializeRecipe();
        initializeRecipeTags();
        initializeRecipeIngredients();

        ImageSetter imageSetter = new ImageSetter();
        imageSetter.setImageResource(this, recipe, previewRecipeImage);

        recipeProcessor = new RecipeProcessor();
        recipeValidator = new RecipeValidator();
        imageProcessor = new ImageProcessor();
    }

    public void insertRecipe(String validationError) {
        if (validationError == null) {
            try {
                recipeProcessor.updateRecipe(recipeId, recipeTitle, recipeDescription, recipeIngredients, recipeTags, recipeImageUri);
                setResult(RESULT_OK);
                finish();
            } catch (IllegalArgumentException e) {
                validationStatusSetter.setIllegalArgumentValidation(validationStatus, e);
            } catch (Exception e) {
                validationStatusSetter.setSystemErrorValidation(validationStatus, e);
            }
        } else {
            validationStatusSetter.setAndroidSystemFailure(validationStatus, validationError);
        }
    }

    private void initializeViewComponents() {
        previewRecipeTitle = findViewById(R.id.recipeTitle);
        previewRecipeTags = findViewById(R.id.recipeTags);
        previewRecipeIngredients = findViewById(R.id.recipeIngredients);
        previewRecipeDescription = findViewById(R.id.recipeDescription);
        previewRecipeImage = findViewById(R.id.recipeImageDetail);

        submitRecipeButton = findViewById(R.id.recipeSubmit);

        validationStatus = findViewById(R.id.textView2);
    }

    private void initializeListeners() {
        submitRecipeButton.setOnClickListener(v -> {
            recipeTitle = ((EditText) findViewById(R.id.recipeTitle)).getText().toString();
            recipeDescription = ((EditText) findViewById(R.id.recipeDescription)).getText().toString();
            recipeIngredients = Arrays.asList(((EditText) findViewById(R.id.recipeIngredients)).getText().toString().split(","));
            recipeTags = ((EditText) findViewById(R.id.recipeTags)).getText().toString();
            String validationError = recipeValidator.inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
            insertRecipe(validationError);
        });

        previewRecipeImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            mGetContent.launch(intent);
        });

        findViewById(R.id.returnButton).setOnClickListener(v -> finish());
    }


    public final ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        Intent data = result.getData();
                        assert data != null;

                        // For moving image info to phone in-memory
                        Uri selectedImage = data.getData();

                        AndroidImageReader imageReader = new AndroidImageReader(this);

                        Uri fileUri = imageReader.buildImageUri(selectedImage);

                        previewRecipeImage.setImageURI(fileUri);

                        recipeImageUri = fileUri.toString();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private void initializeRecipe() {
        recipe = (Recipe) getIntent().getSerializableExtra("bean");
        previewRecipeTitle.setText("Recipe Name: " + recipe.getName());
        previewRecipeDescription.setText(recipe.getDesc());
        recipeId = recipe.getId();
        recipeImageUri = recipe.getImageUri();

        if (recipe.getImageUri() != null) {
            previewRecipeImage.setImageURI(Uri.parse(recipe.getImageUri()));
        }
    }

    private void initializeRecipeTags() {
        String tagCollection = "";
        for (int n = 0; n < recipe.getTags().size(); n++) {
            String tag = TextUtils.isEmpty(tagCollection) ? "" : "" + tagCollection + ",";
            tagCollection = tag + (recipe.getTags().get(n)); //to get appropriately indexed tag
        }
        previewRecipeTags.setText("Tags: " + tagCollection);
    }

    private void initializeRecipeIngredients() {
        String ingredientsCollection = "";
        for (int n = 0; n < recipe.getIngredients().size(); n++) {
            String ingredients = TextUtils.isEmpty(ingredientsCollection) ? "" : "" + ingredientsCollection + ",";
            ingredientsCollection = ingredients + (recipe.getIngredients().get(n)); //to get appropriately indexed ingredients
        }
        previewRecipeIngredients.setText("Ingredients: " + ingredientsCollection);
    }
}
