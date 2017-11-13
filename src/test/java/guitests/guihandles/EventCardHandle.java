package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String HEADER_FIELD_ID = "#header";
    private static final String DESC_FIELD_ID = "#desc";
    private static final String EVENT_DATE_FIELD_ID = "#eventDate";

    private final Label idLabel;
    private final Label headerLabel;
    private final Label descLabel;
    private final Label eventDateLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.headerLabel = getChildNode(HEADER_FIELD_ID);
        this.descLabel = getChildNode(DESC_FIELD_ID);
        this.eventDateLabel = getChildNode(EVENT_DATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getHeader() {
        return headerLabel.getText();
    }

    public String getDesc() {
        return descLabel.getText();
    }

    public String getEventDate() {
        return eventDateLabel.getText();
    }
}
