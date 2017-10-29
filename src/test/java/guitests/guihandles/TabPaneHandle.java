package guitests.guihandles;

import static seedu.address.ui.ParcelListPanel.INDEX_FIRST_TAB;

import javafx.scene.control.TabPane;

/**
 * Provides a handle for {@code ParcelListPanel} containing the list of {@code ParcelCard}.
 */
public class TabPaneHandle extends NodeHandle<TabPane> {
    public static final String TAB_PANE_ID = "#tabPanePlaceholder";
    private final ParcelListPanelHandle deliveredParcelListPanel;
    private final ParcelListPanelHandle undeliveredParcelListPanel;

    public TabPaneHandle(TabPane tabPaneNode) {
        super(tabPaneNode);
        undeliveredParcelListPanel = new ParcelListPanelHandle(getChildNode(ParcelListPanelHandle
                .UNDELIVERED_PARCEL_LIST_VIEW_ID));
        deliveredParcelListPanel = new ParcelListPanelHandle(getChildNode(ParcelListPanelHandle
                .DELIVERED_PARCEL_LIST_VIEW_ID));
    }

    public TabHandle getTabHandle(int index) {
        if (index == INDEX_FIRST_TAB.getZeroBased()) {
            return new TabHandle(getChildNode(TabHandle.UNDELIVERED_PARCEL_TAB_ID));
        } else {
            return new TabHandle(getChildNode(TabHandle.DELIVERED_PARCEL_TAB_ID));
        }
    }

    /**
     * Returns a handle to the current {@code ParcelListPanelHandle} and list of parcels
     * Only one list can be available at a point of time.
     * @throws AssertionError if no tab is selected.
     */
    public ParcelListPanelHandle getActiveParcelList() {
        int selectedTabIndex = getRootNode().getSelectionModel().getSelectedIndex();

        if (selectedTabIndex == INDEX_FIRST_TAB.getZeroBased()) {
            return undeliveredParcelListPanel;
        } else {
            return deliveredParcelListPanel;
        }
    }

    public ParcelListPanelHandle getDeliveredParcelListPanel() {
        return deliveredParcelListPanel;
    }

    public ParcelListPanelHandle getUndeliveredParcelListPanel() {
        return undeliveredParcelListPanel;
    }

    /**
     * Returns the index of the selected tab.
     */
    public int getSelectedTabIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }
}
