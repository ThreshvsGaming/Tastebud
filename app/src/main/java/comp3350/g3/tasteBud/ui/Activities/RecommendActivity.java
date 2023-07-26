package comp3350.g3.tasteBud.ui.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Singletons.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.Processors.RecipeProcessor;
import comp3350.g3.tasteBud.logic.Processors.RecommendProcessor;
import comp3350.g3.tasteBud.logic.Processors.SearchProcessor;
import comp3350.g3.tasteBud.ui.Displays.HomePageAdapter;
import comp3350.g3.tasteBud.ui.Interface.IDeleteInteraction;
import comp3350.g3.tasteBud.ui.Interface.IListInteraction;
import comp3350.g3.tasteBud.ui.Displays.Messages;

public class RecommendActivity extends Fragment implements IListInteraction, IDeleteInteraction {
    private RecyclerView recycler;
    private HomePageAdapter madapter;
    private RelativeLayout deleteLayout;
    private ImageView backButton, deleteButton;
    private TextView selectedRecommendText;
    private Button recommendButton;
    private boolean[] selectedIngredients;
    private ArrayList<Integer> ingredientListChecks;
    private String[] completeIngredientList;
    private RecommendProcessor recommendProcessor;
    private SearchProcessor searchProcessor;
    private RecipeProcessor recipeProcessor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recommend_activity, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        searchProcessor = new SearchProcessor();
        recipeProcessor = new RecipeProcessor();
        recommendProcessor = new RecommendProcessor();

        initializeIngredientResources();
        initializeViewComponents(view);
        initializeListeners();

        madapter = new HomePageAdapter(getContext(), this);
        madapter.bindToRecyclerView(recycler);
    }

    private void initializeIngredientResources() {
        completeIngredientList = recommendProcessor.getIngredientList();
        selectedIngredients = new boolean[completeIngredientList.length];
        ingredientListChecks = new ArrayList<>();
    }

    private void initializeViewComponents(View view) {
        selectedRecommendText = view.findViewById(R.id.recommendFilter);
        recommendButton = view.findViewById(R.id.recommendButton);
        recycler = view.findViewById(R.id.recycler);
        deleteButton = view.findViewById(R.id.delete);
        deleteLayout = view.findViewById(R.id.deleteLayout);
        backButton = view.findViewById(R.id.ivBack);
    }

    private void initializeListeners() {
        selectedRecommendText.setOnClickListener(v -> builderBuilderComponent());

        recommendButton.setOnClickListener(v -> showAssemblableRecipes());

        backButton.setOnClickListener((view1) -> {
            disableDeleteMenu();
        });

        deleteButton.setOnClickListener((view1) -> {
            Messages.buildWarningDeleteDialogue(this.getContext(), "Are you sure you want to delete the following recipes?", this);
        });
    }

    private void builderBuilderComponent() {
        // Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Select available ingredients");

        builder.setCancelable(false);

        builder.setMultiChoiceItems(completeIngredientList, selectedIngredients, (dialogInterface, index, checkBoxSelected) -> {
            if (checkBoxSelected) {
                // Add position  in lang list
                ingredientListChecks.add(index);

                Collections.sort(ingredientListChecks);
            } else {
                // Remove position from langList
                ingredientListChecks.remove(Integer.valueOf(index));
            }
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> selectedRecommendText.setText(constructTextViewText(completeIngredientList).toString()));

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.setNeutralButton("Clear All", (dialogInterface, i) -> clearIngredientList());

        // show dialog
        builder.show();
    }

    private StringBuilder constructTextViewText(String[] completeIngredientList){
        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < ingredientListChecks.size(); j++) {
            stringBuilder.append(completeIngredientList[ingredientListChecks.get(j)]);

            //we don't wanna add comma at the end
            if (j != ingredientListChecks.size() - 1) {
                stringBuilder.append(",");
            }
        }

        return stringBuilder;
    }


    private void clearIngredientList() {
        for (int j = 0; j < selectedIngredients.length; j++) {
            // remove all selection
            selectedIngredients[j] = false;
            // clear ingredient list
            ingredientListChecks.clear();
            // clear text view value
            selectedRecommendText.setText("");
        }
    }

    public void showAssemblableRecipes() {
        String ingredientList = selectedRecommendText.getText().toString();

        madapter.setNewData(recommendProcessor.searchAssemblableRecipes(ingredientList));
    }

    @Override
    public void onClickListItem(int position) {
        startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("bean", madapter.getData().get(position)));
    }

    @Override
    public void onHoldListItem(int position) {
        deleteLayout.setVisibility(View.VISIBLE);
    }

    public void disableDeleteMenu() {
        deleteLayout.setVisibility(View.GONE);
        madapter.offSelectionMode();
    }

    @Override
    public void delete() {
        recipeProcessor.deleteListOfRecipe(madapter.getSelectedItems());
        showAssemblableRecipes();
        disableDeleteMenu();
    }

    @Override
    public void onStart() {
        super.onStart();
        showAssemblableRecipes();
    }
}