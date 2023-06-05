package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
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

import comp3350.g3.tasteBud.R;

public class CreateActivity extends Fragment {
    //The layout connect with "+" Button
    Button submitRecipeButton;
    String recipeTitle;
    String recipeDescription;

    TextView validationStatus;

  //  @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.create_activity, container, false);

        submitRecipeButton = view.findViewById(R.id.recipeSubmit);

        validationStatus = view.findViewById(R.id.textView2);

        submitRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeTitle = ((EditText) view.findViewById(R.id.recipeTitle)).getText().toString();
                recipeDescription = ((EditText) view.findViewById(R.id.recipeDescription)).getText().toString();

                if (inputValidation(recipeTitle, recipeDescription)) {

                    try {
                        validationStatus.setText("Recipe Successfully Added!");
                        validationStatus.setVisibility(View.VISIBLE);
                        validationStatus.setTextColor(Color.GREEN);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                validationStatus.setVisibility(View.INVISIBLE);
                            }

                        }, 3000); //Show dialog for 3 seconds
                    }
                    catch (IllegalArgumentException e) {
                        validationStatus.setText("Recipe Creation Failed: " + e.getMessage());
                        validationStatus.setVisibility(View.VISIBLE);
                        validationStatus.setTextColor(Color.RED);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                validationStatus.setVisibility(View.INVISIBLE);
                            }

                        }, 3000); //Show dialog for 3 seconds
                    }
                    catch (Exception e) {
                        validationStatus.setText("System Error: " + e.getMessage());
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
            }
        });


        return view;
    }


    private boolean inputValidation (String recipeName, String recipeInstructions){
        boolean validated = true;
        if(recipeName.isEmpty()) {
            validated = false;
            validationStatus.setText("Name cannot be empty!!");
            validationStatus.setVisibility(View.VISIBLE);
            validationStatus.setTextColor(Color.RED);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    validationStatus.setVisibility(View.INVISIBLE);
                }

            }, 3000); //Show dialog for 3 seconds
        }

        if(recipeName.matches("^\\s+$")) {
            validated = false;
            validationStatus.setText("Name cannot be spaces only!!");
            validationStatus.setVisibility(View.VISIBLE);
            validationStatus.setTextColor(Color.RED);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    validationStatus.setVisibility(View.INVISIBLE);
                }

            }, 3000); //Show dialog for 3 seconds
        }


        if(recipeName.matches("^\\p{Punct}+$") || recipeName.matches("\\d+$")) {
            validated = false;
            validationStatus.setText("Name cannot be numbers or symbols only!!");
            validationStatus.setVisibility(View.VISIBLE);
            validationStatus.setTextColor(Color.RED);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    validationStatus.setVisibility(View.INVISIBLE);
                }

            }, 3000); //Show dialog for 3 seconds
        }

        if(recipeInstructions.isEmpty()) {
            validated = false;
            validationStatus.setText("Description cannot be empty!!");
            validationStatus.setVisibility(View.VISIBLE);
            validationStatus.setTextColor(Color.RED);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    validationStatus.setVisibility(View.INVISIBLE);
                }

            }, 3000); //Show dialog for 3 seconds
        }

        if(recipeInstructions.matches("^\\p{Punct}+$") || recipeName.matches("\\d+$")) {
            validated = false;
            validationStatus.setText("Description cannot be numbers or symbols only!!");
            validationStatus.setVisibility(View.VISIBLE);
            validationStatus.setTextColor(Color.RED);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    validationStatus.setVisibility(View.INVISIBLE);
                }

            }, 3000); //Show dialog for 3 seconds
        }

        if(recipeInstructions.matches("^\\s+$")) {
            validated = false;
            validationStatus.setText("Description cannot be spaces only!!");
            validationStatus.setVisibility(View.VISIBLE);
            validationStatus.setTextColor(Color.RED);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    validationStatus.setVisibility(View.INVISIBLE);
                }

            }, 3000); //Show dialog for 3 seconds
        }

        return validated;
    }
}