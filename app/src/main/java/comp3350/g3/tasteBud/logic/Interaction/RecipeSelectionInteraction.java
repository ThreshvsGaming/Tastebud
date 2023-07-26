package comp3350.g3.tasteBud.logic.Interaction;
import android.util.Log;

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
            Log.d(null, "SELECTION MODE");
            if (!processor.isSelectionTrue(position)) {
                Log.d(null, "SELECT");
                processor.setSelection(position, processor.SELECT);

            } else {
                processor.setSelection(position, processor.NO_SELECT);
            }
        } else {
            listInteraction.onClickListItem(position);
        }
    }

}
