package guitests.guihandles;

import static seedu.address.ui.ParcelListPanel.INDEX_FIRST_TAB;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.ui.ParcelCard;

/**
 * Provides a handle for {@code ParcelListPanel} containing the list of {@code ParcelCard}.
 */
public class TabPanelHandle extends NodeHandle<TabPane> {
    public static final String TAB_PANE_ID = "#tabPanePlaceholder";
    private final ParcelListPanelHandle deliveredParcelListPanel;
    private final ParcelListPanelHandle undeliveredParcelListPanel;

    public TabPanelHandle(TabPane tabPaneNode) {
        super(tabPaneNode);
        undeliveredParcelListPanel = new ParcelListPanelHandle(getChildNode(ParcelListPanelHandle
                .UNDELIVERED_PARCEL_LIST_VIEW_ID));
        deliveredParcelListPanel = new ParcelListPanelHandle(getChildNode(ParcelListPanelHandle
                .DELIVERED_PARCEL_LIST_VIEW_ID));
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

    /**
     * Returns the index of the selected tab.
     */
    public int getSelectedTabIndex() {
         return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Selects the {@code ParcelCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }
}
