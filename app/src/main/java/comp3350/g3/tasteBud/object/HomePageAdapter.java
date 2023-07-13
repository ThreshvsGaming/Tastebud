package comp3350.g3.tasteBud.object;


import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.ui.IListInteraction;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//BaseQuickAdapter is a more powerful(simpler) tool for converting Recipes into a list
public class HomePageAdapter extends BaseQuickAdapter<Recipe, ViewHolder> {
    private boolean selectionMode;
    private IListInteraction listInteraction;
    private ArrayList<Recipe> selectedItems;
    private Context context;
    private ImageSetter imageSetter;
    private boolean[] isSelected;
    public final boolean IS_SELECTED = true;
    public final boolean NOT_SELECTED = false;

    public HomePageAdapter(Context context, IListInteraction listInteraction, RecyclerView recycler) {
        super(R.layout.view_recipe);
        this.context = context;
        this.selectionMode = false;
        this.listInteraction = listInteraction;
        this.imageSetter = new ImageSetter();
        this.selectedItems = new ArrayList<>();
        setDefaultIsSelected(this.selectedItems.size());
        bindToRecyclerView(recycler);
    }

    @Override
    public ViewHolder createBaseViewHolder(android.view.View view) {
        return new ViewHolder(view, this, listInteraction);
    }

    @Override
    protected void convert(ViewHolder helper, Recipe recipe) {
        String tags = recipe.getTags().toString();
        tags = tags.replace("[", "").replace("]", "");

        helper.setText(R.id.tvTitle, recipe.getName());
        helper.setText(R.id.num2, tags);

        int position = helper.getAbsoluteAdapterPosition();

        if (isSelected.length > position && isSelected[position]) {
            helper.checkbox.setVisibility(View.VISIBLE);
            helper.background.setBackgroundColor(Color.LTGRAY);
        } else {
            helper.checkbox.setVisibility(View.GONE);
            helper.background.setBackgroundColor(Color.WHITE);
        }
        if (getData().size() != isSelected.length) {
            setDefaultIsSelected(getData().size());
        }
        imageSetter.setImageResource(context, recipe, helper.img);
    }

    public void setDefaultIsSelected(int length) {
        isSelected = new boolean[length];
        for (int i = 0; i < length; i++) {
            isSelected[i] = false;
        }
    }

    public void removeAllSelections() {
        final int dataSize = this.getData().size();
        for (int i = 0; i < dataSize; i++) {
            notifyItemChanged(i);
        }
        setDefaultIsSelected(dataSize);
    }

    public List<Recipe> getSelectedItems() {

        return selectedItems;
    }

    public void onSelectionMode() {
        selectionMode = true;
    }

    public void offSelectionMode() {
        selectionMode = false;
        selectedItems.clear();
        removeAllSelections();
    }

    public void addSelectedItem(Recipe recipe) {
        selectedItems.add(recipe);
    }

    public void removeSelectedItem(Recipe recipe) {
        selectedItems.remove(recipe);
    }

    public boolean getSelectionMode() {
        return selectionMode;
    }

    public void setRecipeSelection(int position, boolean isSelect) {
        isSelected[position] = isSelect;
    }

    public boolean getRecipeSelection(int position) {
        return isSelected[position];
    }

}