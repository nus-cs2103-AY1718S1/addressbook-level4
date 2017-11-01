package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.SearchBoxHandle;
import guitests.guihandles.SortMenuHandle;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class SortFindPanelTest extends GuiUnitTest {

    private SortMenuHandle sortMenuHandle;
    private SearchBoxHandle searchBoxHandle;
    private SortFindPanel sortFindPanel;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);
        sortFindPanel = new SortFindPanel(logic);
        uiPartRule.setUiPart(sortFindPanel);

        sortMenuHandle = new SortMenuHandle(sortFindPanel.getSortMenu());
        searchBoxHandle = new SearchBoxHandle(sortFindPanel.getSearchBox());
    }

    //@@author Alim95
    @Test
    public void sortMenuHighlight() {
        sortFindPanel.highlightSortMenu();
        assertEquals(sortMenuHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void searchBoxHighlight() {
        sortFindPanel.highlightSearchBox();
        assertEquals(searchBoxHandle.getStyle(), "-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    @Test
    public void sortFindPanelUnhighlight() {
        sortFindPanel.unhighlight();
        assertEquals(sortMenuHandle.getStyle(), "");
        assertEquals(searchBoxHandle.getStyle(), "");
    }
}
