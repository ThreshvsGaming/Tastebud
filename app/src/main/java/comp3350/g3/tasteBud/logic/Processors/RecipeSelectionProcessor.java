package comp3350.g3.tasteBud.logic.Processors;

import java.util.ArrayList;
import java.util.List;

import comp3350.g3.tasteBud.object.Recipe;

public class RecipeSelectionProcessor {

    public final boolean[] NO_SELECTIONS = null;
    public final boolean SELECTMODE_ON = true;
    public final boolean SELECTMODE_OFF = false;
    public final boolean SELECT = true;
    public final boolean NO_SELECT = false;
    private boolean[] isSelected;
    private boolean selectionMode;
    public RecipeSelectionProcessor(){
        isSelected = NO_SELECTIONS;
        selectionMode = SELECTMODE_OFF;
    }
    public RecipeSelectionProcessor(int length){
        if(length > 0) {
            setDefaultIsSelected(length);
        }else{
            isSelected = NO_SELECTIONS;
        }
        selectionMode = SELECTMODE_OFF;
    }
    public void setDefaultIsSelected(int length) {
        isSelected = new boolean[length];
        for (int i = 0; i < length; i++) {
            isSelected[i] = false;
        }
    }

    public int getSelectionSize() {
        int result = 0;
        if(isSelected != NO_SELECTIONS){
            return isSelected.length;
        }
        return result;
    }

    public void setSelection(int index, boolean selection) {
        if(isSelected != NO_SELECTIONS && getSelectionSize() > index && index >= 0){
            isSelected[index] = selection;
        }
    }

    public boolean isSelectionTrue(int index){
        boolean result = false;
        if(isSelected != NO_SELECTIONS && getSelectionSize() > index && index >= 0){
            result = isSelected[index];
        }
        return result;
    }
    public void setSelectionMode(boolean mode){selectionMode = mode;}
    public boolean getSelectionMode(){return selectionMode;}

    public List<Recipe> getSelectedItems(List<Recipe> recipeList) {
        List<Recipe> selectedItems = new ArrayList<>();
        int size = recipeList.size();
        if(size == getSelectionSize()){
            for(int i = 0 ; i < size ; i++){
                if(isSelectionTrue(i)){
                    selectedItems.add(recipeList.get(i));
                }
            }
        }

        return selectedItems;
    }

}
