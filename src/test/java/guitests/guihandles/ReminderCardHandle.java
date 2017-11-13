package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author RonakLakhotia
/**
 * Provides a handle to a person card in the person list panel.
 */
public class ReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String ABOUT_FIELD_ID = "#about";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DUE_DATE_FIELD_ID = "#date";

    private final Label idLabel;
    private final Label aboutLabel;
    private final Label priorityLabel;
    private final Label dateLabel;


    public ReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.aboutLabel = getChildNode(ABOUT_FIELD_ID);
        this.priorityLabel = getChildNode(PRIORITY_FIELD_ID);
        this.dateLabel = getChildNode(DUE_DATE_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getDetails() {
        return aboutLabel.getText();
    }

    public String getPriority() {
        return priorityLabel.getText();
    }

    public String getDueDate() {
        return dateLabel.getText();
    }
}
