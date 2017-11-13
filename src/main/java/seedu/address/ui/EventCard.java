package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.ReadOnlyEvent;

// @@author HouDenghao
/**
 * An UI component that displays information of a {@code Event}.
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

    public final ReadOnlyEvent event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label time;
    @FXML
    private Label timer;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        name.textProperty().bind(Bindings.convert(event.eventNameProperty()));
        description.textProperty().bind(Bindings.convert(event.descriptionProperty()));
        time.textProperty().bind(Bindings.convert(event.timeProperty()));
        if (event.getEventTime().getDays() == 0) {
            timer.textProperty().bind(Bindings.convert(new SimpleObjectProperty<>("Today!")));
        } else {
            timer.textProperty().bind(Bindings.convert(event.daysProperty()));
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
