package comp3350.g3.tasteBud.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TableRow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Singletons.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.Processors.RecipeProcessor;
import comp3350.g3.tasteBud.logic.Processors.RefineProcessor;
import comp3350.g3.tasteBud.logic.Processors.SearchProcessor;
import comp3350.g3.tasteBud.logic.Singletons.TagListKeySingleton;
import comp3350.g3.tasteBud.ui.Displays.HomePageAdapter;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.ui.Interface.IDeleteInteraction;
import comp3350.g3.tasteBud.ui.Interface.IListInteraction;
import comp3350.g3.tasteBud.ui.Displays.Messages;

public class SearchActivity extends Fragment implements IListInteraction, IDeleteInteraction {
    private RecyclerView recycler;
    private HomePageAdapter madapter;
    private SearchView searchView;
    private SearchProcessor searchProcessor;
    private RecipeProcessor recipeProcessor;
    private RefineProcessor refineProcessor;
    private RelativeLayout deleteLayout;
    private ImageView backButton, deleteButton, filterView;
    private String currentSearchQuery;
    public String tagList;
    private TableRow searchMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeTagList();

        return inflater.inflate(R.layout.search_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViewComponents(view);
        initializeListeners();

        searchProcessor = new SearchProcessor();
        recipeProcessor = new RecipeProcessor();
        refineProcessor = new RefineProcessor();
        madapter = new HomePageAdapter(getContext(), this);
        madapter.bindToRecyclerView(recycler);

        currentSearchQuery = "";
        searchView.clearFocus();
    }

    @Override
    public void onStart() {
        //Refresh page when adding new recipe
        super.onStart();
        filterRecipeList();
    }

    public void disableDeleteMenu() {
        deleteLayout.setVisibility(View.GONE);
        searchMenu.setVisibility(View.VISIBLE);
        madapter.offSelectionMode();
    }

    public void filterRecipeList() {
        List<Recipe> list;
        String[] tags = refineProcessor.constructSelectedTags(tagList);

        if (tags.length == 0) {
            list = searchProcessor.searchResults(currentSearchQuery);
        } else {
            list = searchProcessor.searchResultsWithTag(tags, currentSearchQuery);
        }

        madapter.setNewData(list);
    }

    public void onClickListItem(int position) {
        startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("bean", madapter.getData().get(position)));
    }

    public void onHoldListItem(int position) {
        deleteLayout.setVisibility(View.VISIBLE);
        searchMenu.setVisibility(View.GONE);
    }

    public void delete() {
        recipeProcessor.deleteListOfRecipe(madapter.getSelectedItems());
        filterRecipeList();
        disableDeleteMenu();
    }

    public void initializeTagList() {
        Bundle bundle = getActivity().getIntent().getExtras();

        if (bundle != null) {
            tagList = bundle.getString(TagListKeySingleton.getInstance().GetTagListKey());
        }
    }

    private void initializeViewComponents(View view) {
        searchView = view.findViewById(R.id.searchView);
        recycler = view.findViewById(R.id.recycler);
        deleteLayout = view.findViewById(R.id.deleteLayout);
        backButton = view.findViewById(R.id.ivBack);
        deleteButton = view.findViewById(R.id.delete);
        filterView = view.findViewById(R.id.ivFilterButton);
        searchMenu = view.findViewById(R.id.searchMenu);
    }

    private void initializeListeners() {
        searchView.setOnClickListener(v -> searchView.setIconified(false));
        //Enables a function that detects text changes in the Search View
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Filters recipes based on user input
                currentSearchQuery = newText;
                filterRecipeList();
                return true;
            }
        });

        backButton.setOnClickListener((view1) -> {
            disableDeleteMenu();
        });

        deleteButton.setOnClickListener((view1) -> {
            Messages.buildWarningDeleteDialogue(this.getContext(), "Are you sure you want to delete the following recipes?", this);
        });

        filterView.setOnClickListener(v -> {
            tagList = null; //to remove current filter

            Intent intent = new Intent(getActivity(), RefineActivity.class);
            startActivity(intent);
        });
    }
}