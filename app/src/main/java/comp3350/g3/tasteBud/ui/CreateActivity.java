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
import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.object.Recipe;

public class CreateActivity extends Fragment {
    //The layout connect with "+" Button
    private Button submitRecipeButton;
    private String recipeTitle;
    private String recipeDescription;
    private String recipeTags;
    private String recipeIngredients;
    private RecipeProcessor recipeProcessor;
    private TextView validationStatus;
    private ImageView backButton;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_activity, container, false);

        submitRecipeButton = view.findViewById(R.id.recipeSubmit);

        validationStatus = view.findViewById(R.id.textView2);

        recipeProcessor = new RecipeProcessor();

        backButton = view.findViewById(R.id.returnButton);
        submitRecipeButton.setOnClickListener(v -> {
            recipeTitle = ((EditText) view.findViewById(R.id.recipeTitle)).getText().toString();
            recipeDescription = ((EditText) view.findViewById(R.id.recipeDescription)).getText().toString();
            recipeIngredients = ((EditText) view.findViewById(R.id.recipeIngredients)).getText().toString();
            recipeTags = ((EditText) view.findViewById(R.id.recipeTags)).getText().toString();

            String validationError = recipeProcessor.inputValidation(recipeTitle, recipeDescription, recipeIngredients, recipeTags);

            if (validationError == null) {

                try {
                    recipeProcessor.addRecipes(recipeTitle, recipeDescription, recipeIngredients, recipeTags);

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
                    validationStatus.setText("Recipe Creation Failed: " + e.getMessage());
                    validationStatus.setVisibility(View.VISIBLE);
                    validationStatus.setTextColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validationStatus.setVisibility(View.INVISIBLE);
                        }

                    }, 10000); //Show dialog for 10 seconds
                } catch (Exception e) {
                    validationStatus.setText("System Error: " + e.getMessage());
                    validationStatus.setVisibility(View.VISIBLE);
                    validationStatus.setTextColor(Color.RED);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            validationStatus.setVisibility(View.INVISIBLE);
                        }

                    }, 10000); //Show dialog for 10 seconds
                }
            } else {
                validationStatus.setText(validationError);
                validationStatus.setVisibility(View.VISIBLE);
                validationStatus.setTextColor(Color.RED);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validationStatus.setVisibility(View.INVISIBLE);
                    }

                }, 3000); //Show dialog for 3 seconds
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager() != null) {
                    getParentFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }
}