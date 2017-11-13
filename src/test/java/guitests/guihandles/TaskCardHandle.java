//@@author srishag
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class TaskCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String HEADER_FIELD_ID = "#header";
    private static final String DESC_FIELD_ID = "#desc";
    private static final String DEADLINE_FIELD_ID = "#deadline";

    private final Label idLabel;
    private final Label headerLabel;
    private final Label descLabel;
    private final Label deadlineLabel;

    public TaskCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.headerLabel = getChildNode(HEADER_FIELD_ID);
        this.descLabel = getChildNode(DESC_FIELD_ID);
        this.deadlineLabel = getChildNode(DEADLINE_FIELD_ID);
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

    public String getDeadline() {
        return deadlineLabel.getText();
    }

}
