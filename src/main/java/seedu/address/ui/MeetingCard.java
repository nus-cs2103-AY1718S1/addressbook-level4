package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.meeting.Meeting;

//@@author LimYangSheng
/**
 * An UI component that displays meetings of a person.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";

    public final Meeting meeting;

    @FXML
    private HBox meetingCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label meetingName;
    @FXML
    private Label meetingTime;

    public MeetingCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
        bindListeners(meeting);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Meeting meeting) {
        name.textProperty().bind(Bindings.convert(meeting.nameProperty()));
        meetingName.textProperty().bind(Bindings.convert(meeting.meetingNameProperty()));
        meetingTime.textProperty().bind(Bindings.convert(meeting.meetingTimeProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingCard)) {
            return false;
        }

        // state check
        MeetingCard card = (MeetingCard) other;
        return id.getText().equals(card.id.getText())
                && meeting.equals(card.meeting);
    }
}
