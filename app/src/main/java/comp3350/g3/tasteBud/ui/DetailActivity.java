package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.RatingsProcessor;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.object.ImageSetter;
import comp3350.g3.tasteBud.object.Ratings;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;

public class DetailActivity extends FragmentActivity implements DeleteInteraction {
    private TextView recipeTitle;
    private TextView recipeDescription;
    private TextView recipeTags;
    private TextView recipeIngredients;
    private RecipeProcessor recipeProcessor;
    private Recipe recipe;

    private Ratings ratings;
    private ImageView recipeImage;

    private TextView ratingText;

    private RatingBar recipeRatings;

    private RatingsProcessor ratingsProcessor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Connect with Layout File:"detail_activity"
        setContentView(R.layout.detail_activity);

        initializeViewComponents();
        initializeListeners();

        initializeRecipe();
        initializeRatings();
        initializeRecipeTags();
        initializeRecipeIngredients();

        ImageSetter imageSetter = new ImageSetter();
        imageSetter.setImageResource(this, recipe, recipeImage);

        //Create a Recipe Processor to link to the logic layer
        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());

        //Create a ratings processor to link to the logic layer
        ratingsProcessor = new RatingsProcessor(PersistenceSingleton.getInstance().GetIsPersistence());

    }



    public void delete() {
        recipeProcessor.deleteRecipe(recipe.getId());
        finish();
    }

    // Checks for when a user edits a recipe with the EditActivity page; displays new version of recipe if it has been edited
    ActivityResultLauncher<Intent> startResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // If user cancels the editing, it returns to this screen. Otherwise, it displays the edited recipe
            if (result != null && result.getResultCode() == RESULT_OK) {
                finish();
                Intent replace = getIntent();
                replace.removeExtra("bean");
                replace.putExtra("bean", recipeProcessor.getRecipe(recipe.getId()));
                startActivity(getIntent());
            }
        }
    });

    private void initializeViewComponents() {
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeTags = findViewById(R.id.recipeTags);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeDescription = findViewById(R.id.recipeDescription);
        recipeImage = findViewById(R.id.recipeImageDetail);
        ratingText = findViewById(R.id.textViewRating);
        recipeRatings = findViewById(R.id.ratingBar);
    }

    private void initializeListeners() {
        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        findViewById(R.id.delete).setOnClickListener(v -> {
            Messages.buildWarningDeleteDialogue(findViewById(R.id.delete).getContext(), "Are you sure you want to delete this recipe?", this);
        });

        Intent intent = new Intent(this, EditActivity.class).putExtra("bean", getIntent().getSerializableExtra("bean"));
        findViewById(R.id.edit).setOnClickListener(
                v -> {
                    startResult.launch(intent);
                }
        );

        recipeRatings.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               // Log.d("APP:", "Number of Stars: " + rating);
                ratings.setRecipeRatings((int) rating);
                initializeRecipeRatings();
            }
        });
       // Log.d("APP:", "Number of Stars: " + recipeRatings.getRating());
    }

    private void initializeRatings() {
        ratings = new Ratings();
        //if()
       // Log.d("TAG", "initializeRatings: " +  ratingsProcessor.getRating(recipe.getId()));
        //recipeRatings.setRating((float) ratingsProcessor.getRating(recipe.getId()));
    }

    private void initializeRecipe() {
        //Create the instance of Recipe to get information of each recipe
        recipe = (Recipe) getIntent().getSerializableExtra("bean");
        recipeTitle.setText(recipe.getName());
        recipeDescription.setText(recipe.getDesc());
    }

    private void initializeRecipeTags() {
        String tagCollection = "";
        for (int n = 0; n < recipe.getTags().size(); n++) {
            String tag = TextUtils.isEmpty(tagCollection) ? "" : "" + tagCollection + ",";
            tagCollection = tag + (recipe.getTags().get(n)); //to get appropriately indexed tag
        }
        recipeTags.setText(tagCollection);
    }

    private void initializeRecipeIngredients() {
        String ingredientsCollection = "";
        for (int n = 0; n < recipe.getIngredients().size(); n++) {
            String ingredients = TextUtils.isEmpty(ingredientsCollection) ? "" : "" + ingredientsCollection + ",";
            ingredientsCollection = ingredients + (recipe.getIngredients().get(n)); //to get appropriately indexed ingredients
        }
        recipeIngredients.setText(ingredientsCollection);
    }


    private void initializeRecipeRatings () {
        ratingsProcessor.addRatings(recipe.getId(), ratings);
        Log.d("APP", "initializeRecipeRatings: " + (float)ratingsProcessor.getRating(recipe.getId()));
        //recipeRatings.setNumStars(ratingsProcessor.getRating(recipe.getId()));
    }
}
