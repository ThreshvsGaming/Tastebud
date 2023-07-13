package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.Arrays;
import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.logic.RecipeValidator;
import comp3350.g3.tasteBud.object.ImageSetter;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.object.ValidationStatusSetter;

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

        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());
        recipeValidator = new RecipeValidator();
    }

    public void insertRecipe(String validationError) {
        if (validationError == null) {
            try {
                recipeProcessor.updateRecipe(recipeId, recipeTitle, recipeDescription, recipeIngredients,recipeTags, recipeImageUri);
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

            CreateActivity createActivity = new CreateActivity();
            createActivity.mGetContent.launch(intent);
        });

        findViewById(R.id.returnButton).setOnClickListener(v -> finish());
    }

    private void initializeRecipe() {
        recipe = (Recipe) getIntent().getSerializableExtra("bean");
        previewRecipeTitle.setText(recipe.getName());
        previewRecipeDescription.setText(recipe.getDesc());
        recipeId = recipe.getId();
        recipeImageUri = recipe.getImageUri();

        previewRecipeImage.setImageURI(Uri.parse(recipe.getImageUri()));
    }

    private void initializeRecipeTags() {
        String tagCollection = "";
        for (int n = 0; n < recipe.getTags().size(); n++) {
            String tag = TextUtils.isEmpty(tagCollection) ? "" : "" + tagCollection + ",";
            tagCollection = tag + (recipe.getTags().get(n)); //to get appropriately indexed tag
        }
        previewRecipeTags.setText(tagCollection);
    }

    private void initializeRecipeIngredients() {
        String ingredientsCollection = "";
        for (int n = 0; n < recipe.getIngredients().size(); n++) {
            String ingredients = TextUtils.isEmpty(ingredientsCollection) ? "" : "" + ingredientsCollection + ",";
            ingredientsCollection = ingredients + (recipe.getIngredients().get(n)); //to get appropriately indexed ingredients
        }
        previewRecipeIngredients.setText(ingredientsCollection);
    }
}
