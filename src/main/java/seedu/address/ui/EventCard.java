package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.event.Event;

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
    private VBox members;

    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        initMembers(event);
        bindListeners(event);
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
