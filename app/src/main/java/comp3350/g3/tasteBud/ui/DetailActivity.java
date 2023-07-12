package comp3350.g3.tasteBud.ui;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.Fragment;



import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Messages;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;

public class DetailActivity extends FragmentActivity implements DeleteInteraction {
    TextView recipeTitle;
    TextView recipeDescription;
    TextView recipeTags;
    TextView recipeIngredients;
    RecipeProcessor recipeProcessor;
    Recipe recipe;

    Intent refresh;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Connect with Layout File:"detail_activity"
        setContentView(R.layout.detail_activity);
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeTags = findViewById(R.id.recipeTags);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeDescription = findViewById(R.id.recipeDescription);

        //Create a Recipe Processor to link to the logic layer
        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());

        //Create the instance of Recipe to get information of each recipe
        recipe = (Recipe) getIntent().getSerializableExtra("bean");
        recipeTitle.setText(recipe.getName());
        recipeDescription.setText(recipe.getDesc());

        String tagCollection = "";
        for (int n = 0; n < recipe.getTags().size(); n++) {
            String tag = TextUtils.isEmpty(tagCollection) ? "" : "" + tagCollection + ",";
            tagCollection = tag + (recipe.getTags().get(n)); //to get appropriately indexed tag
        }
        recipeTags.setText(tagCollection);

        String ingredientsCollection = "";
        for (int n = 0; n < recipe.getIngredients().size(); n++) {
            String ingredients = TextUtils.isEmpty(ingredientsCollection) ? "" : "" + ingredientsCollection + ",";
            ingredientsCollection = ingredients + (recipe.getIngredients().get(n)); //to get appropriately indexed ingredients
        }
        recipeIngredients.setText(ingredientsCollection);

        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        findViewById(R.id.delete).setOnClickListener(v-> {
            Messages.buildWarningDeleteDialogue(findViewById(R.id.delete).getContext(), "Are you sure you want to delete this recipe?", this);
        });

        Intent intent = new Intent(this, EditActivity.class).putExtra("bean",  getIntent().getSerializableExtra("bean"));

        findViewById(R.id.edit).setOnClickListener(
                v -> {startResult.launch(intent);}
        );
    }

    public void delete()
    {
        recipeProcessor.deleteRecipe(recipe.getId());
        finish();
    }

    // Checks for when a user edits a recipe with the EditActivity page; displays new version of recipe if it has been edited
    ActivityResultLauncher<Intent> startResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // If user cancels the editing, it returns to this screen. Otherwise, it displays the edited recipe
            if(result != null && result.getResultCode() == RESULT_OK) {
                finish();
                Intent replace = getIntent();
                replace.removeExtra("bean");
                replace.putExtra("bean", recipeProcessor.getRecipe(recipe.getId()));
                startActivity(getIntent());
            }
        }
    });
}
