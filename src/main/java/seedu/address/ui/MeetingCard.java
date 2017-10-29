package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays meetings of a person.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";

    public final Meeting meeting;

    @javafx.fxml.FXML
    private HBox meetingCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane meetings;

    public MeetingCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
//        initMeetings(meeting);
        bindListeners(meeting);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Meeting meeting) {
        name.textProperty().bind(Bindings.convert(meeting.nameProperty()));
        Label tagLabel = new Label(meeting.value);
        meetings.getChildren().add(tagLabel);

//        person.tagProperty().addListener((observable, oldValue, newValue) -> {
//            meetings.getChildren().clear();
//
//            /*
//            * Initalise meetings for a person by assigning it with a label
//            * */
//            initMeetings(person);
//        });
    }

    /**
     *Initialises a label with an assigned colour for a given person
     * @param person
     */
//    private void initMeetings(ReadOnlyPerson person) {
//        person.getMeetings().forEach(meeting -> {
//            Label tagLabel = new Label(meeting.value);
//            meetings.getChildren().add(tagLabel);
//        });
//    }

}
