package comp3350.g3.tasteBud.object;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Arrays;

import comp3350.g3.tasteBud.R;

public class HomePageAdapter extends BaseQuickAdapter<Recipe, BaseViewHolder> {
    //BaseQuickAdapter is a more powerful(simpler) tool for converting Recipes into a list
    public HomePageAdapter() {
        super(R.layout.view_recipe);
    }

    @Override
    protected void convert(BaseViewHolder helper, Recipe recipe) {
        String tags = recipe.getTags().toString();;
        tags = tags.replace("[", "").replace("]", "");

        helper.setText(R.id.tvTitle, recipe.getName());
        helper.setText(R.id.num2, tags);
    }

}