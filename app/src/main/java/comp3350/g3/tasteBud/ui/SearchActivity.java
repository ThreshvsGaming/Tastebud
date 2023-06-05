package comp3350.g3.tasteBud.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.data.RecipeStub;
import comp3350.g3.tasteBud.object.FirstBean;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.object.homePageAdapter;

public class SearchActivity extends Fragment {
    //Modify the Search Page
    RecyclerView recycler;
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
        recycler=view.findViewById(R.id.recycler);
        homePageAdapter madapter=new homePageAdapter();
        recycler.setAdapter(madapter);

        ArrayList<Recipe> list=new ArrayList<>();
        list= RecipeStub.storedRecipes();
        madapter.setNewData(list);
    }
}