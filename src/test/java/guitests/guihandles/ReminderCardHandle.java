//@@author duyson98

package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a reminder card in the reminder list panel.
 */
public class ReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TASK_FIELD_ID = "#task";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DATE_FIELD_ID = "#datentime";
    private static final String MESSAGE_FIELD_ID = "#message";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label taskLabel;
    private final Label priorityLabel;
    private final Label dateLabel;
    private final Label messageLabel;
    private final List<Label> tagLabels;

    public ReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.taskLabel = getChildNode(TASK_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.messageLabel = getChildNode(MESSAGE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTask() {
        return taskLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getMessage() {
        return messageLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
