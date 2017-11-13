package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author justinpoh
/**
 * Provides a handle to a reminder card in the reminder list panel.
 */
public class ReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String REMINDER_FIELD_ID = "#reminder";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TIME_FIELD_ID = "#time";

    private final Label idLabel;
    private final Label reminderLabel;
    private final Label dateLabel;
    private final Label timeLabel;

    public ReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.reminderLabel = getChildNode(REMINDER_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getReminder() {
        return reminderLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }
}
