package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a task card in the task list panel.
 */
//@@author charlesgoh
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TASK_NAME_ID = "#taskName";
    private static final String DESCRIPTION_ID = "#description";
    private static final String DEADLINE_ID = "#deadline";
    private static final String PRIORITY_ID = "#priority";

    private final Label idLabel;
    private final Label taskNameLabel;
    private final Label descriptionLabel;
    private final Label deadlineLabel;
    private final Label priorityLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.taskNameLabel = getChildNode(TASK_NAME_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_ID);
        this.priorityLabel = getChildNode(PRIORITY_ID);
        this.deadlineLabel = getChildNode(DEADLINE_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTaskName() {
        return taskNameLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getDeadline() {
        return deadlineLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }
}
