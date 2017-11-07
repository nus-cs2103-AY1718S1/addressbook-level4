package seedu.address.ui;

import java.time.LocalDate;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventTime;


//@@author eldriclim
/**
 * An UI component that displays information of a {@code Person}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;
    public final LocalDate selectedDate;

    @FXML
    private HBox cardPane;
    @FXML
    private Label eventName;
    @FXML
    private Label id;
    @FXML
    private Label eventDateTime;
    @FXML
    private Label eventDuration;

    @FXML
    private Label eventStatus;
    @FXML
    private VBox members;

    public EventCard(Event event, int displayedIndex, LocalDate selectedDate) {
        super(FXML);
        this.event = event;
        this.selectedDate = selectedDate;

        id.setText(displayedIndex + ". ");
        initMembers(event);
        bindListeners(event);

        updateEventStatusLabel(eventStatus, event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Event event) {
        eventName.textProperty().bind(Bindings.convert(event.eventNameProperty()));
        eventDateTime.textProperty().bind(Bindings.convert(event.eventTimeProperty()));
        eventDuration.textProperty().bind(Bindings.convert(event.eventDurationProperty()));
        event.eventMemberListProperty().addListener((observable, oldValue, newValue) -> {
            members.getChildren().clear();
            event.getMemberList().asReadOnlyMemberList().forEach(member -> members.getChildren().add(
                    new Label(member.getName().toString())));
        });
    }

    private void initMembers(Event event) {
        event.getMemberList().asReadOnlyMemberList().forEach(member -> members.getChildren().add(
                new Label(member.getName().toString())));
    }

    /**
     * Update the event status label to reflect the relevance of the event.
     * @param eventStatusLabel
     * @param event
     */
    private void updateEventStatusLabel(Label eventStatusLabel, Event event) {
        EventTime eventTime = event.getEventTime();

        if (eventTime.getStart().toLocalDate().isEqual(LocalDate.now())) {
            eventStatusLabel.setText("Today");
            eventStatusLabel.setStyle("-fx-background-color: #fd720f");

        } else if (eventTime.isUpcoming()) {
            eventStatusLabel.setText("Upcoming");
            eventStatusLabel.setStyle("-fx-background-color: #009e73");
        } else {
            eventStatusLabel.setText("Past");
            eventStatusLabel.setStyle("-fx-background-color: #a31621");
        }

        if (event.getEventTime().getStart().toLocalDate().isEqual(selectedDate)
                || DateTimeUtil.containsReferenceDate(event, selectedDate)) {
            eventStatusLabel.setText("Selected");
            eventStatusLabel.setStyle("-fx-background-color: #b91372");
        }
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }


}
