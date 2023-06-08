package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.object.Recipe;

public class DetailActivity extends FragmentActivity {
    TextView recipeTitle;
    TextView recipeDescription;
    TextView recipeTags;
    TextView recipeIngredients;

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

        //Create the instance of Recipe to get information of each recipe
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("bean");
        recipeTitle.setText(recipe.getName());
        recipeDescription.setText(recipe.getDesc());

        String tagCollection = "";
        for (int n = 0; n < recipe.getTags().length; n++) {
            String tag = TextUtils.isEmpty(tagCollection) ? "" : "" + tagCollection + ",";
            tagCollection = tag + (recipe.getTags()[n]); //to get appropriately indexed tag
        }
        recipeTags.setText(tagCollection);

        String ingredientsCollection = "";
        for (int n = 0; n < recipe.getIngredients().length; n++) {
            String ingredients = TextUtils.isEmpty(ingredientsCollection) ? "" : "" + ingredientsCollection + ",";
            ingredientsCollection = ingredients + (recipe.getIngredients()[n]); //to get appropriately indexed ingredients
        }
        recipeIngredients.setText(ingredientsCollection);

        findViewById(R.id.ivBack).setOnClickListener(v -> finish());
    }
}
