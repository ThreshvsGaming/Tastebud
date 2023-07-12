package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import comp3350.g3.tasteBud.object.Recipe;

public class EditActivity extends FragmentActivity {
    TextView previewRecipeTitle;
    TextView previewRecipeDescription;
    TextView previewRecipeTags;
    TextView previewRecipeIngredients;
    private Button submitRecipeButton;

    String recipeTitle;
    String recipeDescription;
    String recipeTags;
    List<String> recipeIngredients;

    int recipeId;
    private TextView validationStatus;
    private RecipeProcessor recipeProcessor;


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

        submitRecipeButton = findViewById(R.id.recipeSubmit);

        validationStatus = findViewById(R.id.textView2);
        //Create the instance of Recipe to get information of each recipe
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("bean");
        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());
        previewRecipeTitle.setText(recipe.getName());
        previewRecipeDescription.setText(recipe.getDesc());
        recipeId = recipe.getId();

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

        submitRecipeButton.setOnClickListener(v -> {
            recipeTitle = ((EditText) findViewById(R.id.recipeTitle)).getText().toString();
            recipeDescription = ((EditText) findViewById(R.id.recipeDescription)).getText().toString();
            //recipeIngredients = ((EditText) view.findViewById(R.id.recipeIngredients)).getText().toString();
            recipeIngredients = Arrays.asList(((EditText) findViewById(R.id.recipeIngredients)).getText().toString().split(","));
            recipeTags = ((EditText) findViewById(R.id.recipeTags)).getText().toString();
            String validationError = recipeProcessor.inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
            handleValidation(validationError);
        });

        findViewById(R.id.returnButton).setOnClickListener(v -> finish());

    }
    public void handleValidation(String validationError) {
        if (validationError == null) {
            try {
                //recipeProcessor.addRecipes(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
                validationStatus.setText("Recipe Successfully Added!");
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.GREEN);
                recipeProcessor.updateRecipe(recipeId, recipeTitle, recipeDescription, recipeIngredients,recipeTags);
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
