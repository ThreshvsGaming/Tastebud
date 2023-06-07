package comp3350.g3.tasteBud.logic;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.Recipe;

public class CreateActivityLogic {
    private TextView validationStatus;

    public CreateActivityLogic(TextView validationStatus) {
        this.validationStatus = validationStatus;
    }

    public void submitRecipe(View view, EditText recipeTitleEditText, EditText recipeDescriptionEditText, EditText recipeIngredientsEditText, EditText recipeTagsEditText) {
        String recipeTitle = recipeTitleEditText.getText().toString();
        String recipeDescription = recipeDescriptionEditText.getText().toString();
        String recipeIngredients = recipeIngredientsEditText.getText().toString();
        String recipeTags = recipeTagsEditText.getText().toString();

        if (inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags)) {
            try {
                String[] ingredientsArray = recipeIngredients.split(",");
                String[] tags = recipeTags.split(",");

                RecipeStub database = new RecipeStub();

                Recipe newRecipe = new Recipe(
                        recipeTitle,
                        recipeDescription,
                        ingredientsArray,
                        tags
                );

                database.add(newRecipe);

                validationStatus.setText("Recipe Successfully Added!");
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.GREEN);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validationStatus.setVisibility(View.INVISIBLE);
                    }
                }, 3000); //Show dialog for 3 seconds
            } catch (IllegalArgumentException e) {
                handleValidationFailure("Recipe Creation Failed: " + e.getMessage());
            } catch (Exception e) {
                handleValidationFailure("System Error: " + e.getMessage());
            }
        }
    }

    private boolean inputValidation(String recipeName, String recipeInstructions, String recipeIngredients, String recipeTags) {
        boolean validated = true;
        if (recipeName.isEmpty() || recipeName.matches("^\\s+$") || recipeName.matches("^\\p{Punct}+$") || recipeName.matches("\\d+$")) {
            handleValidationFailure("Invalid recipe name!");
            validated = false;
        }

        if (recipeInstructions.isEmpty() || recipeInstructions.matches("^\\p{Punct}+$") || recipeInstructions.matches("\\d+$") || recipeInstructions.matches("^\\s+$")) {
            handleValidationFailure("Invalid recipe description!");
            validated = false;
        }

        return validated;
    }

    private void handleValidationFailure(String message) {
        validationStatus.setText(message);
        validationStatus.setVisibility(View.VISIBLE);
        validationStatus.setTextColor(Color.RED);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                validationStatus.setVisibility(View.INVISIBLE);
            }
        }, 3000); //Show dialog for 3 seconds
    }
}
