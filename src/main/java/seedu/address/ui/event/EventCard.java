package seedu.address.ui.event;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.reminder.Reminder;
import seedu.address.ui.UiPart;

//@@author junyango
/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {
    private static final String FXML = "event/EventListCard.fxml";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd MMM, yyyy HH:mm", Locale.ENGLISH);

    // The event that is displayed in this card.
    public final ReadOnlyEvent event;

    private Image greenNotification = new Image("/images/notifications_green.png");
    private Image redNotification = new Image("/images/notifications_red.png");
    private Image orangeNotification = new Image("/images/notifications_orange.png");

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    @FXML
    private HBox cardPane;
    @FXML
    private Label idEvent;
    @FXML
    private Label name;
    @FXML
    private Label dateTime;
    @FXML
    private Label venue;
    @FXML
    private ImageView notifications;

    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        this.event = event;
        idEvent.setText(displayedIndex + ". ");
        bindListeners(event);
        registerAsAnEventHandler(this);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        name.textProperty().bind(Bindings.convert(event.nameProperty()));
        venue.textProperty().bind(Bindings.convert(event.addressProperty()));
        dateTime.textProperty().bind(Bindings.convert(event.timeProperty()));

        for (Reminder r : event.getReminders()) {
            LocalDate dateToCompare = LocalDate.parse(r.getEvent().getTime().getValue(), formatter);
            LocalDate now = LocalDate.now();
            LocalDate twoDaysBefore = dateToCompare.minus(Period.ofDays(2));
            LocalDate oneDayBefore = dateToCompare.minus(Period.ofDays(1));

            if (now.isEqual(dateToCompare)) {
                notifications.setImage(redNotification);
            } else if (now.isEqual(twoDaysBefore)) {
                notifications.setImage(greenNotification);
            } else if (now.isEqual(oneDayBefore)) {
                notifications.setImage(orangeNotification);
            }
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
        return idEvent.getText().equals(card.idEvent.getText())
                && event.equals(card.event);
    }

}
