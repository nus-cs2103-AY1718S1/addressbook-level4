package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

// @@author HouDenghao
/**
 * Provides a handle to a event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String TIME_FIELD_ID = "#time";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label descriptionLabel;
    private final Label timeLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

}
