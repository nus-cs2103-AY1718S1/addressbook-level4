//@@author Hailinx
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a todoCard in the todoList panel.
 */
public class TodoCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TIME_FIELD_ID = "#time";
    private static final String TASK_FIELD_ID = "#task";

    private final Label idLabel;
    private final Label timeLabel;
    private final Label taskLabel;

    public TodoCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);
        this.taskLabel = getChildNode(TASK_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

    public String getTask() {
        return taskLabel.getText();
    }

    @Override
    public String toString() {
        return getId() + getTime() + getTask();
    }
}
