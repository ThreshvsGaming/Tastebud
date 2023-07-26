package comp3350.g3.tasteBud.ui.Displays;


import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Processors.RecipeSelectionProcessor;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.ui.Interface.IListInteraction;

import java.util.ArrayList;
import java.util.List;

//BaseQuickAdapter is a more powerful(simpler) tool for converting Recipes into a list
public class HomePageAdapter extends BaseQuickAdapter<Recipe, ViewHolder> {
    private boolean selectionMode;
    private IListInteraction listInteraction;
    private Context context;
    private ImageSetter imageSetter;
    public RecipeSelectionProcessor recipeSelectionProcessor;
    public final boolean IS_SELECTED = true;
    public final boolean NOT_SELECTED = false;

    public HomePageAdapter(Context context, IListInteraction listInteraction) {
        super(R.layout.view_recipe);
        this.context = context;
        this.selectionMode = false;
        this.listInteraction = listInteraction;
        this.imageSetter = new ImageSetter();
        this.recipeSelectionProcessor = new RecipeSelectionProcessor(getData().size());
    }

    @Override
    public ViewHolder createBaseViewHolder(android.view.View view) {
        return new ViewHolder(view, this, listInteraction);
    }

    @Override
    protected void convert(ViewHolder helper, Recipe recipe) {

        helper.setDisplay(recipe);

        if (getData().size() != recipeSelectionProcessor.getSelectionSize()) {
            recipeSelectionProcessor.setDefaultIsSelected(getData().size());
        }

        imageSetter.setImageResource(context, recipe, helper.img);
    }
    public void removeAllSelections() {
        final int dataSize = this.getData().size();
        for (int i = 0; i < dataSize; i++) {
            notifyItemChanged(i);
        }
        recipeSelectionProcessor.setDefaultIsSelected(dataSize);
    }

    public List<Recipe> getSelectedItems() {return recipeSelectionProcessor.getSelectedItems(getData());}

    public RecipeSelectionProcessor getRecipeSelectionProcessor(){return recipeSelectionProcessor;}
    public void onSelectionMode() {
        recipeSelectionProcessor.setSelectionMode(recipeSelectionProcessor.SELECTMODE_ON);
    }
    public void offSelectionMode() {
        recipeSelectionProcessor.setSelectionMode(recipeSelectionProcessor.SELECTMODE_OFF);
        removeAllSelections();
    }

}