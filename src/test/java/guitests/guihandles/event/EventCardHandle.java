package guitests.guihandles.event;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
//@@author junyango
/**
 * Provides a handle to an event card in the {@code EventListPanel}.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#idEvent";
    private static final String NAME_FIELD_ID = "#name";
    private static final String VENUE_FIELD_ID = "#venue";
    private static final String TIME_FIELD_ID = "#dateTime";


    private final Label idLabel;
    private final Label nameLabel;
    private final Label venueLabel;
    private final Label timeLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.venueLabel = getChildNode(VENUE_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEventName() {
        return nameLabel.getText();
    }

    public String getVenue() {
        return venueLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

}
