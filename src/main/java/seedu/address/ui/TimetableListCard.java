package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.ReadOnlyEvent;

//@@author reginleiff

/**
 * An UI component that displays information of a {@code Event} on the schedule.
 *
 */
public class TimetableListCard extends UiPart<Region> {

    private static final String FXML = "TimetableListCard.fxml";
    private static final int BASE_WIDTH = 150;
    private static final int WIDTH_CONST = 50;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable titles cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyEvent event;

    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label timing;

    public TimetableListCard(ReadOnlyEvent event) {
        super(FXML);
        this.event = event;
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Event} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        title.textProperty().bind(Bindings.convert(event.titleProperty()));
        timing.textProperty().bind(Bindings.convert(event.timingProperty()));
    }

    /**
     * Corrects width of TimetableListCard with respect to duration of event.
     */
    public TimetableListCard setWidth() {
        double widthMultiplier = event.getTimeslot().getTiming().getDuration();
        double newWidth = BASE_WIDTH + WIDTH_CONST * widthMultiplier;
        cardPane.setPrefWidth(newWidth);
        return this;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TimetableListCard)) {
            return false;
        }

        // state check
        TimetableListCard card = (TimetableListCard) other;
        return event.equals(card.event);
    }
}
