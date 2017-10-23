package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

// @@author yangshuang

/**
 * Provides a handle to a person card in the person list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String TIMESLOT_FIELD_ID = "#timeslot";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label timeslotLabel;
    private final Label descriptionLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.timeslotLabel = getChildNode(TIMESLOT_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getTimeslot() {
        return timeslotLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

}
