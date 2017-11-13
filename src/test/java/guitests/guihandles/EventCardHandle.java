package guitests.guihandles;

//@@author chernghann
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#date";
    private static final String ADDRESS_FIELD_ID = "#address";

    private Label nameLabel;
    private Label idLabel;
    private Label dateLabel;
    private Label addressLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEventName() {
        return nameLabel.getText();
    }

    public String getEventDate() {
        return dateLabel.getText();
    }

    public String getEventAddress() {
        return addressLabel.getText();
    }

}
