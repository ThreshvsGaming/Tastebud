package comp3350.g3.tasteBud.logic;

import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.Recipe;

public class CreateActivityLogic {
    private TextView validationStatus;
    private RecipeStub database;
    private Handler handler;

    public CreateActivityLogic(TextView validationStatus) {
        this.validationStatus = validationStatus;
        this.database = new RecipeStub();
        this.handler = new Handler();
    }

    public void setDatabase(RecipeStub database) {
        this.database = database;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
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

                handler.postDelayed(new Runnable() {
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

    public boolean inputValidation(String recipeName, String recipeDescription, String recipeIngredients, String recipeTags) {
        boolean validated = true;
        if (recipeName.isEmpty() || recipeName.matches("^\\s+$") || recipeName.matches("^\\p{Punct}+$") || recipeName.matches("\\d+$")) {
            handleValidationFailure("Invalid recipe name!");
            validated = false;
        }

        if (recipeDescription.isEmpty() || recipeDescription.matches("^\\p{Punct}+$") || recipeDescription.matches("\\d+$") || recipeDescription.matches("^\\s+$")) {
            handleValidationFailure("Invalid recipe description!");
            validated = false;
        }

        return validated;
    }

    private void handleValidationFailure(String message) {
        validationStatus.setText(message);
        validationStatus.setVisibility(View.VISIBLE);
        validationStatus.setTextColor(Color.RED);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                validationStatus.setVisibility(View.INVISIBLE);
            }
        }, 3000); //Show dialog for 3 seconds
    }
}

