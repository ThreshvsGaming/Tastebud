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
public class HomePageAdapter extends BaseQuickAdapter<Recipe, HomePageAdapter.ViewHolder> {
    private boolean selectionMode;
    private IListInteraction listInteraction;
    private ArrayList<Recipe> selectedItems;
    private Context context;
    private ImageSetter imageSetter;

    public HomePageAdapter(Context context, IListInteraction listInteraction, RecyclerView recycler) {
        super(R.layout.view_recipe);
        this.context = context;
        this.selectionMode = false;
        this.listInteraction = listInteraction;
        this.imageSetter = new ImageSetter();
        this.selectedItems = new ArrayList<>();
        bindToRecyclerView(recycler);
    }

    @Override
    public ViewHolder createBaseViewHolder(android.view.View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void convert(ViewHolder helper, Recipe recipe) {
        String tags = recipe.getTags().toString();
        tags = tags.replace("[", "").replace("]", "");

        helper.setText(R.id.tvTitle, recipe.getName());
        helper.setText(R.id.num2, tags);

        if (getData().get(helper.getAbsoluteAdapterPosition()).isSelected()) {
            helper.checkbox.setVisibility(View.VISIBLE);
            helper.background.setBackgroundColor(Color.LTGRAY);
        } else {
            helper.checkbox.setVisibility(View.GONE);
            helper.background.setBackgroundColor(Color.WHITE);
        }

        imageSetter.setImageResource(context, recipe, helper.img );
    }


    public void removeAllSelections() {
        final int dataSize = this.getData().size();
        for (int i = 0; i < dataSize; i++) {
            getData().get(i).changeSelection(Recipe.NOT_SELECTED);
            notifyItemChanged(i);
        }
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


    public class ViewHolder extends BaseViewHolder {
        // initialize variables
        LinearLayout background;
        ImageView checkbox;
        TextView title, tags;

        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // assign variables
            background = itemView.findViewById(R.id.linearLayout);
            checkbox = itemView.findViewById(R.id.imageViewCheckMark);
            title = itemView.findViewById(R.id.tvTitle);
            tags = itemView.findViewById(R.id.num2);
            img = itemView.findViewById(R.id.img); // Add this line
            setClicks();

        }

        private void setClicks() {
            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                if (selectionMode) {
                    Recipe recipe = getData().get(position);
                    if (!recipe.isSelected()) {
                        addSelections(position, recipe);

                    } else {
                        removeSelections(position, recipe);
                    }
                } else {
                    listInteraction.onClickListItem(position);
                }
                Log.d(null, selectedItems.size() + " : " + position);
                onBindViewHolder(this, position);
            });

            itemView.setOnLongClickListener(v -> {
                if (!selectionMode) {
                    int position = getAbsoluteAdapterPosition();
                    Recipe recipe = getData().get(position);
                    listInteraction.onHoldListItem(position);
                    onSelectionMode();
                    addSelections(position, recipe);
                    onBindViewHolder(this, position);
                }
                return true;
            });
        }

        private void addSelections(int position, Recipe recipe) {
            selectedItems.add(getData().get(position));
            recipe.changeSelection(Recipe.SELECTED);

        }

        private void removeSelections(int position, Recipe recipe) {
            if (selectionMode) {
                recipe.changeSelection(Recipe.NOT_SELECTED);
                selectedItems.remove(recipe);
            }

        }
    }
}