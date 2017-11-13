package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.meeting.ReadOnlyMeeting;

//@@author Melvin-leo
/**
 * An UI component that displays information of a {@code Meeting}.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";
    private static String[] colors = { "darkRed", "red", "orangeRed", "grey" };
    private static final String ICONIMPT = "/images/important.png";
    private static final String ICONHATE = "/images/dislike.png";

    public final ReadOnlyMeeting meeting;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label place;
    @FXML
    private ListView<Label> person;
    @FXML
    private ImageView icon;


    public MeetingCard(ReadOnlyMeeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
        bindListeners(meeting);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Meeting} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyMeeting meeting) {
        name.textProperty().bind(Bindings.convert(meeting.nameProperty()));
        date.textProperty().bind(Bindings.convert(meeting.dateProperty()));
        place.textProperty().bind(Bindings.convert(meeting.placeProperty()));
        List<Label> labels = new ArrayList<>();
        meeting.getPersonsMeet().forEach(person -> {
            Label newPersonLabel = new PersonLabel(person.getName().toString() + "\n"
                    + person.getPhone().toString());
            labels.add(newPersonLabel);
        });
        person.setItems(FXCollections.observableList(labels));
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime meetingDate = LocalDateTime.parse(meeting.getDate().toString(), formatter);
        LocalDateTime currDate = LocalDateTime.now();
        long daysBet = ChronoUnit.DAYS.between(currDate, meetingDate);
        if (daysBet == 0) {
            initMeeting(meeting, colors[0]);
        } else if (daysBet == 1) {
            initMeeting(meeting, colors[1]);
        } else if (daysBet == 2) {
            initMeeting(meeting, colors[2]);
        }
        if (meeting.getMeetTag().toString().equals("2")) {
            icon.setImage(new Image(ICONIMPT));
        }
        if (meeting.getMeetTag().toString().equals("0")) {
            icon.setImage(new Image(ICONHATE));
        }

    }

    /**
     * set colours to Meeting
     * @param meeting
     */
    private void initMeeting(ReadOnlyMeeting meeting, String color) {
        cardPane.setStyle("-fx-background-color: " + color);
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
