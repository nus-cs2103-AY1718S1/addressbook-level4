package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
//@@author Melvin-leo
/**
 * Provides a handle to a meeting card in the meeting list panel.
 */
public class MeetingCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PLACE_FIELD_ID = "#place";
    private static final String PERSONTOMEET_FIELD_ID = "#person";
    private static final String DATETIME_FIELD_ID = "#date";

    private final Label idLabel;
    private final Label nameMeetingLabel;
    private final Label dateTimeLabel;
    private final Label placeLabel;

    public MeetingCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameMeetingLabel = getChildNode(NAME_FIELD_ID);
        this.placeLabel = getChildNode(PLACE_FIELD_ID);
        this.dateTimeLabel = getChildNode(DATETIME_FIELD_ID);


    }

    public String getId() {
        return idLabel.getText();
    }

    public String getNameMeeting() {
        return nameMeetingLabel.getText();
    }

    public String getPlace() {
        return placeLabel.getText();
    }

    public String getDateTime() {
        return dateTimeLabel.getText();
    }
}
