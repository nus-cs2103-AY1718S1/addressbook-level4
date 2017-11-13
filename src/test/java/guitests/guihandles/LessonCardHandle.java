package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class LessonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String CODE_FIELD_ID = "#code";
    private static final String CLASS_TYPE_FIELD_ID = "#classType";
    private static final String GROUP_FIELD_ID = "#group";
    private static final String LOCATION_FIELD_ID = "#venue";
    private static final String TIME_SLOT_FIELD_ID = "#timeSlot";
    private static final String LECTURERS_FIELD_ID = "#lecturers";

    private final Label idLabel;
    private final Label codeLabel;
    private final Label classTypeLabel;
    private final Label groupLabel;
    private final Label locationLabel;
    private final Label timeSlotLabel;
    private final List<Label> lecturerLabels;

    public LessonCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.codeLabel = getChildNode(CODE_FIELD_ID);
        this.classTypeLabel = getChildNode(CLASS_TYPE_FIELD_ID);
        this.groupLabel = getChildNode(GROUP_FIELD_ID);
        this.locationLabel = getChildNode(LOCATION_FIELD_ID);
        this.timeSlotLabel = getChildNode(TIME_SLOT_FIELD_ID);

        Region lecturersContainer = getChildNode(LECTURERS_FIELD_ID);
        this.lecturerLabels = lecturersContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getCode() {
        return codeLabel.getText();
    }

    public String getClassType() {
        return classTypeLabel.getText();
    }

    public String getGroup() {
        return groupLabel.getText();
    }

    public String getLocation() {
        return locationLabel.getText();
    }

    public String getTimeSlot() {
        return timeSlotLabel.getText();
    }

    public List<String> getLecturers() {
        return lecturerLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
