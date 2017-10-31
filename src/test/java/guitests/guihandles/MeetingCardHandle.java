package guitests.guihandles;

//import java.util.List;
//import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
//import javafx.scene.layout.Region;

/**
 * Provides a handle to a meeting card in the meeting list panel.
 */
public class MeetingCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#nameMeeting";
    private static final String PLACE_FIELD_ID = "#place";
    private static final String PHONENUM_FIELD_ID = "#phoneNum";
    private static final String PERSONTOMEET_FIELD_ID = "#personToMeet";
    private static final String DATETIME_FIELD_ID = "#dateTime";

    private final Label idLabel;
    private final Label nameMeetingLabel;
    private final Label dateTimeLabel;
    private final Label phoneNumLabel;
    private final Label personToMeetLabel;
    private final Label placeLabel;

    public MeetingCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameMeetingLabel = getChildNode(NAME_FIELD_ID);
        this.placeLabel = getChildNode(PLACE_FIELD_ID);
        this.phoneNumLabel = getChildNode(PHONENUM_FIELD_ID);
        this.personToMeetLabel = getChildNode(PERSONTOMEET_FIELD_ID);
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

    public String getPhoneNum() {
        return phoneNumLabel.getText();
    }

    public String getDateTime() {
        return dateTimeLabel.getText();
    }

    public String getPersonToMeet() {
        return personToMeetLabel.getText();
    }

}
