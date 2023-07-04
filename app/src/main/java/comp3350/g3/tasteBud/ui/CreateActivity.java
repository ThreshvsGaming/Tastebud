package comp3350.g3.tasteBud.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.RecipeManager;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.object.Recipe;

public class CreateActivity extends Fragment {
    private Button submitRecipeButton;
    private String recipeTitle;
    private String recipeDescription;
    private String recipeTags;
    private List<String> recipeIngredients;
    private RecipeProcessor recipeProcessor;
    private TextView validationStatus;
    private ImageView backButton;
    RecipeManager recipeManager;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_activity, container, false);

        recipeManager = new RecipeManager();
        submitRecipeButton = view.findViewById(R.id.recipeSubmit);

        validationStatus = view.findViewById(R.id.textView2);

        recipeProcessor = new RecipeProcessor();

        backButton = view.findViewById(R.id.returnButton);
        submitRecipeButton.setOnClickListener(v -> {
            recipeTitle = ((EditText) view.findViewById(R.id.recipeTitle)).getText().toString();
            recipeDescription = ((EditText) view.findViewById(R.id.recipeDescription)).getText().toString();
            recipeIngredients = Arrays.asList(((EditText) view.findViewById(R.id.recipeIngredients)).getText().toString().split(","));
            recipeTags = ((EditText) view.findViewById(R.id.recipeTags)).getText().toString();



            String validationError = recipeProcessor.inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
            handleValidation(validationError);
        });

        backButton.setOnClickListener(v -> {
            if (getParentFragmentManager() != null) {
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }

    public void handleValidation(String validationError) {
        if (validationError == null) {

            try {
                //recipeProcessor.addRecipes(recipeTitle, recipeDescription, recipeIngredients, recipeTags);
                validationStatus.setText("Recipe Successfully Added!");
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.GREEN);
                recipeManager.addRecipe(new Recipe(recipeTitle, recipeDescription, recipeIngredients,recipeTags));
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