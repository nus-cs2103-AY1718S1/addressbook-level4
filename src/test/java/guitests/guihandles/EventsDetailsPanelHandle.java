package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

//@@author DarrenCzen
/**
 * A handle to the {@code EventsDetailsPanel} of the application.
 */
public class EventsDetailsPanelHandle extends NodeHandle<Node> {
    public static final String EVENTS_DETAILS_PANEL_ID = "#eventsDetailsPanelPlaceholder";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String DATE_FIELD_ID = "#date";
    private static final String ADDRESS_FIELD_FIELD_ID = "#addressField";
    private static final String DATE_FIELD_FIELD_ID = "#dateField";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label dateLabel;

    private final Text addressText;
    private final Text dateText;

    private String latestName;
    private String latestAddress;
    private String latestDate;

    public EventsDetailsPanelHandle(Node eventsDetailsPanelNode) {
        super(eventsDetailsPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);

        this.addressText = getChildNode(ADDRESS_FIELD_FIELD_ID);
        this.dateText = getChildNode(DATE_FIELD_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getAddressField() {
        return addressText.getText();
    }

    public String getDateField () {
        return dateText.getText();
    }

    //@@author archthegit
    /**
     * Remember the details of the event that was last selected
     */
    public void rememberSelectedEventDetails() {
        latestAddress = getAddress();
        latestName = getName();
        latestDate = getDate();
    }

    /**
     * Returns true if the selected {@code Event} is different from the value remembered by the most recent
     * {@code rememberSelectedEventDetails()} call.
     */
    public boolean isSelectedEventChanged() {
        return !getName().equals(latestName)
                || !getAddress().equals(latestAddress)
                || !getDate().equals(latestDate);
    }
    //@@author
}
