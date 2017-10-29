package guitests;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.ui.ParcelListPanel.INDEX_FIRST_TAB;
import static seedu.address.ui.ParcelListPanel.INDEX_SECOND_TAB;

import org.junit.Test;

import guitests.guihandles.ParcelListPanelHandle;
import guitests.guihandles.TabHandle;
import guitests.guihandles.TabPaneHandle;

public class TabPaneTest extends AddressBookGuiTest {

    @Test
    public void clickTabs() {
        TabPaneHandle tabPane = getTabPane();

        // check startup is undelivered list
        assertActiveListSelected(tabPane, INDEX_FIRST_TAB.getZeroBased());

        // check second tab
        TabHandle tab =  getTabPane().getTabHandle(INDEX_SECOND_TAB.getZeroBased());
        tab.click();
        assertActiveListSelected(tabPane, INDEX_SECOND_TAB.getZeroBased());

        guiRobot.pauseForHuman();

        // revert back to first tab
        tab =  getTabPane().getTabHandle(INDEX_FIRST_TAB.getZeroBased());
        tab.click();
        assertActiveListSelected(tabPane, INDEX_FIRST_TAB.getZeroBased());
    }


    private void assertActiveListSelected(TabPaneHandle tabPane, int tabIndex) {
        ParcelListPanelHandle actualList = tabPane.getActiveParcelList();
        ParcelListPanelHandle expectedList = (tabIndex == INDEX_FIRST_TAB.getZeroBased()) ?
                tabPane.getUndeliveredParcelListPanel() : tabPane.getDeliveredParcelListPanel();

        assertEquals(tabIndex, tabPane.getSelectedTabIndex());
        assertEquals(expectedList, actualList);
        guiRobot.pauseForHuman();
    }
}
