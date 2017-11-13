//@@author 17navasaw
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a schedule card in the schedule list panel.
 */
public class ScheduleCardHandle extends NodeHandle<Node> {
    private static final String NUMBER_FIELD_ID = "#number";
    private static final String ACTIVITY_FIELD_ID = "#activity";
    private static final String DATE_FIELD_ID = "#date";
    private static final String NAMES_FIELD_ID = "#personNames";

    private final Label numberLabel;
    private final Label activityLabel;
    private final Label dateLabel;
    private final List<Label> nameLabels;

    public ScheduleCardHandle(Node cardNode) {
        super(cardNode);

        this.numberLabel = getChildNode(NUMBER_FIELD_ID);
        this.activityLabel = getChildNode(ACTIVITY_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);

        Region namesContainer = getChildNode(NAMES_FIELD_ID);
        this.nameLabels = namesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getNumber() {
        return numberLabel.getText();
    }

    public List<String> getNames() {
        return nameLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public String getActivity() {
        return activityLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }
}
