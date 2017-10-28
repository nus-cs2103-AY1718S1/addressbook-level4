package seedu.address.ui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.Meeting;

/**
 *  UI component that displays information about upcoming meetings
 */
public class MeetingPanelCard extends UiPart<Region> {

    private static final String FXML = "MeetingPanelCard.fxml";

    @FXML
    private Label meetingDateLabel;

    @FXML
    private Label meetingPersonLabel;

    @FXML
    private Label meetingLocationLabel;

    @FXML
    private Label meetingTimeLabel;

    @FXML
    private Label meetingNotesLabel;

    public MeetingPanelCard(Meeting meeting, ArrayList<String> names) {
        super(FXML);
        meetingDateLabel.textProperty().setValue(meeting.getDate());
        meetingPersonLabel.textProperty().setValue(names.toString());
        meetingLocationLabel.textProperty().setValue(meeting.getLocation());
        meetingTimeLabel.textProperty().setValue(meeting.getTime());
        meetingNotesLabel.textProperty().setValue(meeting.getNotes());
    }
}
