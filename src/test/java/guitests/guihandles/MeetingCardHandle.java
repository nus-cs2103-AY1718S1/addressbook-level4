package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author LimYangSheng
/**
 * Provides a handle to a meeting card in the meeting list panel.
 */
public class MeetingCardHandle extends NodeHandle<Node>  {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String MEETING_NAME_FIELD_ID = "#meetingName";
    private static final String MEETING_TIME_FIELD_ID = "#meetingTime";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label meetingNameLabel;
    private final Label meetingTimeLabel;

    public MeetingCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.meetingNameLabel = getChildNode(MEETING_NAME_FIELD_ID);
        this.meetingTimeLabel = getChildNode(MEETING_TIME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getMeetingName() {
        return meetingNameLabel.getText();
    }

    public String getMeetingTime() {
        return meetingTimeLabel.getText();
    }

}
