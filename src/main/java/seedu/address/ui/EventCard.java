package seedu.address.ui;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.event.ReadOnlyEvent;

//@@author royceljh
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
     */

    public final ReadOnlyEvent event;

    @FXML
    private HBox eventPane;
    @FXML
    private Label header;
    @FXML
    private Label id;
    @FXML
    private Label desc;
    @FXML
    private Label eventDate;

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
        header.textProperty().bind(Bindings.convert(event.headerProperty()));
        desc.textProperty().bind(Bindings.convert(event.descProperty()));
        eventDate.textProperty().bind(Bindings.convert(event.eventDateProperty()));
        buildEventBackground();
    }

    /**
     * Change the background color of an event.
     */
    private void buildEventBackground() {
        ZoneId sgt = ZoneId.of("GMT+8");
        LocalDate currentDate = LocalDate.now(sgt);
        Period period = currentDate.until(event.getEventDate().eventLocalDate);
        String colorDate;
        if (period.getDays() < 0) {
            colorDate = "#CE5A57;"; // Red
        } else if (period.getDays() < 3 && period.getMonths() == 0 && period.getYears() == 0) {
            colorDate = "#E1B16A;"; // Orange
        } else {
            colorDate = "#78A5A3;"; // Green
        }
        eventPane.setStyle("-fx-background-color: " + colorDate + "-fx-border-width: 2;" + "-fx-border-color: black;");
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
