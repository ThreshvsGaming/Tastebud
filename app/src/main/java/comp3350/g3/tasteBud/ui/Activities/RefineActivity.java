package comp3350.g3.tasteBud.ui.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.Collections;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Singletons.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.Processors.RefineProcessor;
import comp3350.g3.tasteBud.logic.Singletons.TagListKeySingleton;

public class RefineActivity extends FragmentActivity {
    private TextView selectedTagText;
    private Button filterButton;
    private boolean[] selectedTags;
    private ArrayList<Integer> tagListChecks;
    private String[] completeTagList;
    private RefineProcessor refineProcessor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refine_activity);

        refineProcessor = new RefineProcessor();

        initializeTagResource();

        initializeViewComponents();
        initializeListeners();
    }

    private void initializeTagResource() {
        completeTagList = refineProcessor.getTagList();
        selectedTags = new boolean[completeTagList.length];
        tagListChecks = new ArrayList<>();
    }

    private void initializeViewComponents() {
        selectedTagText = findViewById(R.id.tagFilter);
        filterButton = findViewById(R.id.filterButton);
    }

    private void initializeListeners() {
        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        selectedTagText.setOnClickListener(view -> builderBuilderComponent());

        filterButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(TagListKeySingleton.getInstance().GetTagListKey(), getTagList());
            startActivity(intent);
            finish();
        });
    }

    public String getTagList(){
        return selectedTagText.getText().toString();
    }

    private void builderBuilderComponent() {
        // Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RefineActivity.this);

        builder.setTitle("Select Tags");

        builder.setCancelable(false);

        builder.setMultiChoiceItems(completeTagList, selectedTags, (dialogInterface, index, checkBoxSelected) -> {
            if (checkBoxSelected) {
                // Add position  in lang list
                tagListChecks.add(index);

                Collections.sort(tagListChecks);
            } else {
                // Remove position from langList
                tagListChecks.remove(Integer.valueOf(index));
            }
        });

        builder.setPositiveButton("OK", (dialogInterface, i) -> selectedTagText.setText(constructTextViewText(completeTagList).toString()));

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.setNeutralButton("Clear All", (dialogInterface, i) -> clearTagList());

        // show dialog
        builder.show();
    }

    private StringBuilder constructTextViewText(String[] completeTagList){
        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < tagListChecks.size(); j++) {
            stringBuilder.append(completeTagList[tagListChecks.get(j)]);

            //we don't wanna add comma at the end
            if (j != tagListChecks.size() - 1) {
                stringBuilder.append(",");
            }
        }

        return stringBuilder;
    }

    private void clearTagList() {
        for (int j = 0; j < selectedTags.length; j++) {
            // remove all selection
            selectedTags[j] = false;
            // clear tag list
            tagListChecks.clear();
            // clear text view value
            selectedTagText.setText("");
        }
    }
}