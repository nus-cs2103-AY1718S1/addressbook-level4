package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";
    private static final String TOTAL_PERSONS_STATUS_ID = "#totalPersons";
    private static final String TOTAL_GROUPS_STATUS_ID = "#totalGroups";

    private final StatusBar syncStatusNode;
    private final StatusBar saveLocationNode;
    private final StatusBar totalPersonsNode;
    private final StatusBar totalGroupsNode;

    private String lastRememberedSyncStatus;
    private String lastRememberedSaveLocation;
    private String lastRememberedTotalPersons;
    private String lastRememberedTotalGroups;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        this.syncStatusNode = getChildNode(SYNC_STATUS_ID);
        this.saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
        this.totalPersonsNode = getChildNode(TOTAL_PERSONS_STATUS_ID);
        this.totalGroupsNode = getChildNode(TOTAL_GROUPS_STATUS_ID);
    }

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    //@@author heiseish
    /**
     * Returns the text of the total number of people
     */
    public String getTotalPersons() {
        return totalPersonsNode.getText();
    }

    /**
     * Returns the text of the total number of group
     */
    public String getTotalGroups() {
        return totalGroupsNode.getText();
    }

    /**
     * Remembers the content of the 'total number of person' portion of the status bar.
     */
    public void rememberTotalPersons() {
        lastRememberedTotalPersons = getTotalPersons();
    }

    /**
     * Returns true if the current content of the 'total persons' is different from the value remembered by the most
     * recent {@code rememberTotalPersons()} call.
     */
    public boolean isTotalPersonsChanged() {
        return !lastRememberedTotalPersons.equals(getTotalPersons());
    }

    /**
     * Remembers the content of the 'total number of group' portion of the status bar.
     */
    public void rememberTotalGroups() {
        lastRememberedTotalGroups = getTotalGroups();
    }

    /**
     * Returns true if the current content of the 'total groups' is different from the value remembered by the most
     * recent {@code rememberTotalPersons()} call.
     */
    public boolean isTotalGroupsChanged() {
        return !lastRememberedTotalGroups.equals(getTotalPersons());
    }



    //@@author
    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberSyncStatus() {
        lastRememberedSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the most recent
     * {@code rememberSyncStatus()} call.
     */
    public boolean isSyncStatusChanged() {
        return !lastRememberedSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveLocation = getSaveLocation();
    }


    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by the most
     * recent {@code rememberSaveLocation()} call.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveLocation.equals(getSaveLocation());
    }

}
