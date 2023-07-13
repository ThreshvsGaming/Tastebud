package comp3350.g3.tasteBud.object;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.ui.IListInteraction;

public class ViewHolder extends BaseViewHolder {
    // initialize variables
    public LinearLayout background;
    public ImageView checkbox;
    public TextView title, tags;
    public ImageView img;
    private HomePageAdapter adapter;
    private IListInteraction listInteraction;

    public ViewHolder(@NonNull View itemView, HomePageAdapter adapter, IListInteraction listInteraction) {
        super(itemView);

        // assign variables
        background = itemView.findViewById(R.id.linearLayout);
        checkbox = itemView.findViewById(R.id.imageViewCheckMark);
        title = itemView.findViewById(R.id.tvTitle);
        tags = itemView.findViewById(R.id.num2);
        img = itemView.findViewById(R.id.img); // Add this line
        this.adapter = adapter;
        this.listInteraction = listInteraction;
        setClicks();

    }

    private void setClicks() {
        itemView.setOnClickListener(v -> {
            int position = getAbsoluteAdapterPosition();
            if (adapter.getSelectionMode()) {
                Recipe recipe = adapter.getData().get(position);
                if (!adapter.getRecipeSelection(position)) {
                    addSelections(position, recipe);

                } else {
                    removeSelections(position, recipe);
                }
            } else {
                listInteraction.onClickListItem(position);
            }
            adapter.onBindViewHolder(this, position);
        });

        itemView.setOnLongClickListener(v -> {
            if (!adapter.getSelectionMode()) {
                int position = getAbsoluteAdapterPosition();
                Recipe recipe = adapter.getData().get(position);
                listInteraction.onHoldListItem(position);
                adapter.onSelectionMode();
                addSelections(position, recipe);
                adapter.onBindViewHolder(this, position);
            }
            return true;
        });
    }

    private void addSelections(int position, Recipe recipe) {
        adapter.addSelectedItem(recipe);
        adapter.setRecipeSelection(position, adapter.IS_SELECTED);

    }

    private void removeSelections(int position, Recipe recipe) {
        if (adapter.getSelectionMode()) {
            adapter.removeSelectedItem(recipe);
            adapter.setRecipeSelection(position, adapter.NOT_SELECTED);
        }
    }
}
