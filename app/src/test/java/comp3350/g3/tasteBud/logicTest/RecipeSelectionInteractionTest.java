package comp3350.g3.tasteBud.logicTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import comp3350.g3.tasteBud.logic.Interaction.RecipeSelectionInteraction;
import comp3350.g3.tasteBud.logic.Processors.RecipeSelectionProcessor;
import comp3350.g3.tasteBud.ui.Interface.IDisplaySelectInteractions;
import comp3350.g3.tasteBud.ui.Interface.IListInteraction;

import static org.mockito.Mockito.*;

public class RecipeSelectionInteractionTest {

    @Mock
    private IDisplaySelectInteractions displayMock;

    @Mock
    private RecipeSelectionProcessor processorMock;

    @Mock
    private IListInteraction listInteractionMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetDisplaySelections_SelectionTrue() {
        int position = 0;
        when(processorMock.isSelectionTrue(position)).thenReturn(true);

        RecipeSelectionInteraction.setDisplaySelections(displayMock, processorMock, position);

        verify(displayMock).setSelectedDisplay();
        verify(displayMock, never()).setDefaultDisplay();
    }

    @Test
    public void testSetDisplaySelections_SelectionFalse() {
        int position = 0;
        when(processorMock.isSelectionTrue(position)).thenReturn(false);

        RecipeSelectionInteraction.setDisplaySelections(displayMock, processorMock, position);

        verify(displayMock, never()).setSelectedDisplay();
        verify(displayMock).setDefaultDisplay();
    }

    @Test
    public void testOnClickRecipe_SelectionModeOn_NoSelection() {
        int position = 0;
        when(processorMock.getSelectionMode()).thenReturn(true);
        when(processorMock.isSelectionTrue(position)).thenReturn(false);

        RecipeSelectionInteraction.onClickRecipe(processorMock, position, listInteractionMock);

        verify(processorMock).setSelection(position, true);
        verify(processorMock, never()).setSelection(position, false);
        verify(listInteractionMock, never()).onClickListItem(position);
    }

    @Test
    public void testOnClickRecipe_SelectionModeOn_WithSelection() {
        int position = 0;
        when(processorMock.getSelectionMode()).thenReturn(true);
        when(processorMock.isSelectionTrue(position)).thenReturn(true);

        RecipeSelectionInteraction.onClickRecipe(processorMock, position, listInteractionMock);

        verify(processorMock).setSelection(position, false);
        verify(processorMock, never()).setSelection(position, true);
        verify(listInteractionMock, never()).onClickListItem(position);
    }

    @Test
    public void testOnClickRecipe_SelectionModeOff() {
        int position = 0;
        when(processorMock.getSelectionMode()).thenReturn(false);

        RecipeSelectionInteraction.onClickRecipe(processorMock, position, listInteractionMock);

        verify(processorMock, never()).setSelection(anyInt(), anyBoolean());
        verify(listInteractionMock).onClickListItem(position);
    }
}
