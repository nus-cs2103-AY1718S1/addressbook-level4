package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {
    private static final String FXML = "EventListCard.fxml";

    /**
     * The upper (exclusive) bound should be equal to {@code Math.pow(16, 6)}.
     */
    private static final int RGB_BOUND = 16777216;

    // Random number generator (non-secure purpose)
    private static final Random randomGenerator = new Random();

    /**
     * Stores the colors for all existing tags here so that the same tag always has the same color. Notice this
     * {@code HashMap} has to be declared as a class variable.
     */
    private static HashMap<String, String> tagColors = new HashMap<>();

    // Keep a list of all persons.
    public final ReadOnlyEvent event;

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


    public EventCard(ReadOnlyEvent event, int displayedIndex) {
        super(FXML);
        this.event = event;
        idEvent.setText(displayedIndex + ". ");
        bindListeners(event);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyEvent event) {
        name.textProperty().bind(Bindings.convert(event.nameProperty()));
        dateTime.textProperty().bind(Bindings.convert(event.timeProperty()));
        venue.textProperty().bind(Bindings.convert(event.venueProperty()));
    }

    /**
     * Gets the RGB value of a randomly-selected color. Notice the selection is not cryptographically random. It will
     * use the same color if a tag with the same name already exists.
     *
     * @return a 6-character string representation of the hexadecimal RGB value.
     */
    private String getRandomColorValue(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            tagColors.put(tagName, Integer.toHexString(randomGenerator.nextInt(RGB_BOUND)));
        }

        return tagColors.get(tagName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return idEvent.getText().equals(card.idEvent.getText())
                && event.equals(card.event);
    }

}
