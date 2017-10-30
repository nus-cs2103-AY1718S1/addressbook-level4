package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a schedule card in the schedule list panel.
 */
public class ScheduleCardHandle extends NodeHandle<Node> {
    private static final String NUMBER_FIELD_ID = "#number";
    private static final String ACTIVITY_FIELD_ID = "#activity";
    private static final String DATE_FIELD_ID = "#date";
    private static final String NAME_FIELD_ID = "#personName";

    private final Label numberLabel;
    private final Label activityLabel;
    private final Label dateLabel;
    private final Label nameLabel;

    public ScheduleCardHandle(Node cardNode) {
        super(cardNode);

        this.numberLabel = getChildNode(NUMBER_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.activityLabel = getChildNode(ACTIVITY_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
    }

    public String getNumber() {
        return numberLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getActivity() {
        return activityLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }
}
