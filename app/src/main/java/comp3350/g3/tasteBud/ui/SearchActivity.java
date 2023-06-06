package comp3350.g3.tasteBud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.FirstBean;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.object.HomePageAdapter;

public class SearchActivity extends Fragment {
    //Modify the Search Page
    RecyclerView recycler;
    private HomePageAdapter madapter;
    List<Recipe> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //To display the Recipe on the "SearchRecipePage"
        super.onViewCreated(view, savedInstanceState);
        madapter = new HomePageAdapter();
        recycler = view.findViewById(R.id.recycler);
        recycler.setAdapter(madapter);
        list = RecipeStub.getStoredRecipes();
        madapter.setNewData(list);
        madapter.setOnItemClickListener((adapter, view1, position) -> {
            startActivity(new Intent(getActivity(),DetailActivity.class).putExtra("bean",madapter.getData().get(position)));
        });
    }

    @Override
    public void onStart() {
        //Refresh page when adding new recipe
        super.onStart();
        madapter.setNewData(RecipeStub.getStoredRecipes());
    }
}