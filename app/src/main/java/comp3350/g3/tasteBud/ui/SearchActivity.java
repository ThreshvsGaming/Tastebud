package comp3350.g3.tasteBud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.RecipeManager;
import comp3350.g3.tasteBud.logic.SearchProcessor;
import comp3350.g3.tasteBud.object.HomePageAdapter;
import comp3350.g3.tasteBud.object.Recipe;

public class SearchActivity extends Fragment {
    private RecyclerView recycler;
    private HomePageAdapter madapter;
    private SearchView searchView;
    private SearchProcessor searchProcessor;
    RecipeManager recipeManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeManager = new RecipeManager();

        searchProcessor = new SearchProcessor();

        madapter = new HomePageAdapter();
        recycler = view.findViewById(R.id.recycler);
        recycler.setAdapter(madapter);
        madapter.setOnItemClickListener((adapter, view1, position) -> {
            startActivity(new Intent(getActivity(), DetailActivity.class)
                    .putExtra("bean", madapter.getData().get(position)));
        });

        searchView = view.findViewById(R.id.searchView);
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
                filterRecipeList(newText);
                return true;
            }
        });
    }

    @Override
    public void onStart() {
        //Refresh page when adding new recipe
        super.onStart();
        filterRecipeList("");
    }

    private void filterRecipeList(String text) {
        //List<Recipe> list = searchProcessor.searchResults(text);
        List<Recipe> list = recipeManager.getAllRecipes();
        List<Recipe> date = new ArrayList<>();
        for (Recipe bean : list) {
            if (bean.getName().indexOf(text) != -1){
                date.add(bean);
            }
        }
        if (TextUtils.isEmpty(text)){
            madapter.setNewData(list);
        }else {
            madapter.setNewData(date);
        }

    }
}