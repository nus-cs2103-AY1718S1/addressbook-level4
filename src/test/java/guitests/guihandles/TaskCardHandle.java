package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String START_DATE_FIELD_ID = "#startDate";
    private static final String DEADLINE_FIELD_ID = "#deadline";

    private final Label idLabel;
    private final Label descriptionLabel;
    private final Label startDateLabel;
    private final Label deadlineLabel;

    public TaskCardHandle(Node taskNode) {
        super(taskNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.startDateLabel = getChildNode(START_DATE_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getStartDate() {
        return startDateLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }
}
