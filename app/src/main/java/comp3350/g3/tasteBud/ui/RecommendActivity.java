package comp3350.g3.tasteBud.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.RefineProcessor;
import comp3350.g3.tasteBud.logic.TagListKeySingleton;
import comp3350.g3.tasteBud.object.HomePageAdapter;

public class RecommendActivity extends FragmentActivity {
    private RecyclerView recycler;
    private HomePageAdapter madapter;
    private TextView selectedRecommendText;
    private Button recommendButton;
    private boolean[] selectedIngredients;
    private ArrayList<Integer> ingredientListChecks;
    private String[] completeIngredientList;
    private RefineProcessor refineProcessor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refine_activity);

        refineProcessor = new RefineProcessor(PersistenceSingleton.getInstance().GetIsPersistence());

        initializeTagResource();

        initializeViewComponents();
        initializeListeners();
    }

    private void initializeTagResource() {
        completeIngredientList = refineProcessor.getTagList();
        selectedIngredients = new boolean[completeIngredientList.length];
        ingredientListChecks = new ArrayList<>();
    }

    private void initializeViewComponents() {
        selectedRecommendText = findViewById(R.id.tagFilter);
        recommendButton = findViewById(R.id.filterButton);
    }

    private void initializeListeners() {
        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        selectedRecommendText.setOnClickListener(view -> builderBuilderComponent());

        recommendButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(TagListKeySingleton.getInstance().GetTagListKey(), getTagList());
            startActivity(intent);
            finish();
        });
    }

    public String getTagList(){
        return selectedRecommendText.getText().toString();
    }

    private void builderBuilderComponent() {
        // Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RecommendActivity.this);

        builder.setTitle("Select Tags");

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
}