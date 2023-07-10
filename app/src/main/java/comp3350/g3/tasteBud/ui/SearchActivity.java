package comp3350.g3.tasteBud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Messages;
import comp3350.g3.tasteBud.logic.PersistenceSingleton;
import comp3350.g3.tasteBud.logic.RecipeProcessor;
import comp3350.g3.tasteBud.logic.SearchProcessor;
import comp3350.g3.tasteBud.object.HomePageAdapter;
import comp3350.g3.tasteBud.object.Recipe;

public class SearchActivity extends Fragment implements IListInteraction, DeleteInteraction {
    private RecyclerView recycler;
    private HomePageAdapter madapter;
    private SearchView searchView;
    private SearchProcessor searchProcessor;
    private RecipeProcessor recipeProcessor;
    private RelativeLayout deleteLayout;
    private ImageView backButton, deleteButton;
    private String currentSearchQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Grab Views from search_activity
        searchView = view.findViewById(R.id.searchView);
        recycler = view.findViewById(R.id.recycler);
        deleteLayout = view.findViewById(R.id.deleteLayout);
        backButton = view.findViewById(R.id.ivBack);
        deleteButton = view.findViewById(R.id.delete);


        searchProcessor = new SearchProcessor(PersistenceSingleton.getInstance().GetIsPersistence());
        recipeProcessor = new RecipeProcessor(PersistenceSingleton.getInstance().GetIsPersistence());
        madapter = new HomePageAdapter(getContext(), this, recycler);

        currentSearchQuery = "";

        searchView.clearFocus();
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
    }

    @Override
    public void onStart() {
        //Refresh page when adding new recipe
        super.onStart();
        filterRecipeList();
    }

    public void disableDeleteMenu() {
        deleteLayout.setVisibility(View.GONE);
        searchView.setVisibility(View.VISIBLE);
        madapter.offSelectionMode();
    }

    public void filterRecipeList() {
        List<Recipe> list = searchProcessor.searchResults(currentSearchQuery);
        madapter.setNewData(list);
    }

    public void onClickListItem(int position) {
        startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("bean", madapter.getData().get(position)));
    }


    public void onHoldListItem(int position) {
        deleteLayout.setVisibility(View.VISIBLE);
        searchView.setVisibility(View.GONE);
    }

    public void delete() {
        recipeProcessor.deleteListOfRecipe(madapter.getSelectedItems());
        filterRecipeList();
        disableDeleteMenu();
    }
}