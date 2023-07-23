package comp3350.g3.tasteBud.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.logic.RecommendProcessor;
import comp3350.g3.tasteBud.logic.RefineProcessor;
import comp3350.g3.tasteBud.logic.SearchProcessor;
import comp3350.g3.tasteBud.logic.TagListKeySingleton;
import comp3350.g3.tasteBud.object.HomePageAdapter;

public class RecommendActivity extends Fragment implements IListInteraction {
    private RecyclerView recycler;
    private HomePageAdapter madapter;
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


        searchProcessor = new SearchProcessor(PersistenceSingleton.getInstance().GetIsPersistence());
        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());
        recommendProcessor = new RecommendProcessor(PersistenceSingleton.getInstance().GetIsPersistence());

        initializeIngredientResources();
        initializeViewComponents(view);
        initializeListeners();

        madapter = new HomePageAdapter(getContext(), this, recycler);
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
    }

    private void initializeListeners() {
        selectedRecommendText.setOnClickListener(v -> builderBuilderComponent());

        recommendButton.setOnClickListener(v -> showAssemblableRecipes());
    }

    public String getTagList(){
        return selectedRecommendText.getText().toString();
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

        builder.setNeutralButton("Clear All", (dialogInterface, i) -> clearTagList());

        // show dialog
        builder.show();
    }

    private StringBuilder constructTextViewText(String[] completeTagList){
        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < ingredientListChecks.size(); j++) {
            stringBuilder.append(completeTagList[ingredientListChecks.get(j)]);

            //we don't wanna add comma at the end
            if (j != ingredientListChecks.size() - 1) {
                stringBuilder.append(",");
            }
        }

        return stringBuilder;
    }


    private void clearTagList() {
        for (int j = 0; j < selectedIngredients.length; j++) {
            // remove all selection
            selectedIngredients[j] = false;
            // clear tag list
            ingredientListChecks.clear();
            // clear text view value
            selectedRecommendText.setText("");
        }
    }

    public void showAssemblableRecipes() {
        String tagList = selectedRecommendText.getText().toString();

        madapter.setNewData(recommendProcessor.searchAssemblableRecipes(tagList));
    }

    @Override
    public void onClickListItem(int position) {

    }

    @Override
    public void onHoldListItem(int position) {

    }
}