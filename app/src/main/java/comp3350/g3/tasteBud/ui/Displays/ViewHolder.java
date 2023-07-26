package comp3350.g3.tasteBud.ui.Displays;


import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;

import comp3350.g3.tasteBud.R;
import comp3350.g3.tasteBud.logic.Processors.RecipeSelectionProcessor;
import comp3350.g3.tasteBud.logic.Interaction.RecipeSelectionInteraction;
import comp3350.g3.tasteBud.object.Recipe;
import comp3350.g3.tasteBud.ui.Interface.IDisplaySelectInteractions;
import comp3350.g3.tasteBud.ui.Interface.IListInteraction;

public class ViewHolder extends BaseViewHolder implements IDisplaySelectInteractions {
    // initialize variables
    public LinearLayout background;
    public ImageView checkbox;
    public TextView title, tags;
    public ImageView img;
    private HomePageAdapter adapter;
    private IListInteraction listInteraction;
    private RecipeSelectionProcessor recipeSelectionProcessor;

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
        recipeSelectionProcessor = adapter.getRecipeSelectionProcessor();
        setClicks();

    }

    public void setDisplay(Recipe recipe)
    {
        int position = getAbsoluteAdapterPosition();
        String tags = recipe.getTags().toString();
        tags = tags.replace("[", "").replace("]", "");

        setText(R.id.tvTitle, recipe.getName());
        setText(R.id.num2, tags);

        RecipeSelectionInteraction.setDisplaySelections(this, recipeSelectionProcessor, position);

    }

    public void setDefaultDisplay(){
        checkbox.setVisibility(View.GONE);
        background.setBackgroundColor(Color.WHITE);
    }
    public void setSelectedDisplay(){
        checkbox.setVisibility(View.VISIBLE);
        background.setBackgroundColor(Color.LTGRAY);
    }

    private void setClicks() {
        itemView.setOnClickListener(v -> {
            int position = getAbsoluteAdapterPosition();
            RecipeSelectionInteraction.onClickRecipe(recipeSelectionProcessor, position, listInteraction);
            adapter.onBindViewHolder(this, position);
        });

        itemView.setOnLongClickListener(v -> {
            if (!recipeSelectionProcessor.getSelectionMode()) {
                int position = getAbsoluteAdapterPosition();
                listInteraction.onHoldListItem(position);
                recipeSelectionProcessor.setSelection(position, recipeSelectionProcessor.SELECT);
                adapter.onSelectionMode();
                adapter.onBindViewHolder(this, position);
            }
            return true;
        });
    }

}
