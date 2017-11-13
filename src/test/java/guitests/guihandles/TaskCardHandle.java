// @@author ahmadalkaff
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String APPOINTMENT_FIELD_ID = "#appointment";
    private static final String DATE_FIELD_ID = "#date";
    private static final String START_TIME_FIELD_ID = "#startTime";

    private final Label appointmentLabel;
    private final Label dateLabel;
    private final Label startTimeLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.appointmentLabel = getChildNode(APPOINTMENT_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.startTimeLabel = getChildNode(START_TIME_FIELD_ID);
    }

    public String getAppointment() {
        return appointmentLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getStartTime() {
        return startTimeLabel.getText();
    }

}
