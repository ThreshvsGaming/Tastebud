package comp3350.g3.tasteBud.logic.Interaction;

import comp3350.g3.tasteBud.logic.Processors.RecipeSelectionProcessor;
import comp3350.g3.tasteBud.ui.Interface.IDisplaySelectInteractions;
import comp3350.g3.tasteBud.ui.Interface.IListInteraction;

public class RecipeSelectionInteraction {

    public static void setDisplaySelections(IDisplaySelectInteractions display, RecipeSelectionProcessor processor, int position){
        if (processor.isSelectionTrue(position)) {
            display.setSelectedDisplay();
        } else {
            display.setDefaultDisplay();
        }
    }

    public static void onClickRecipe(RecipeSelectionProcessor processor, int position, IListInteraction listInteraction){
        if (processor.getSelectionMode()) {
            if (!processor.isSelectionTrue(position)) {
                processor.setSelection(position, processor.SELECT);

            } else {
                processor.setSelection(position, processor.NO_SELECT);
            }
        } else {
            listInteraction.onClickListItem(position);
        }
    }

}
