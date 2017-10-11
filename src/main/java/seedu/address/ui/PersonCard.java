package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static HashMap<String, String> tagToColor = new HashMap<String, String>();


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;
    private Random random = new Random(System.currentTimeMillis());

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    /**
     * Generate random colour with slight dark tint
     * and return it as hexadecimal String
     */
    private String generateRandomColor() {
        // Factor to divide down the random color to better contrast white text
        final double darkColorBase = 1.2;

        final int red = (int) Math.round((random.nextInt(256)) / darkColorBase);
        final int green = (int) Math.round((random.nextInt(256)) / darkColorBase);
        final int blue = (int) Math.round((random.nextInt(256)) / darkColorBase);

        // Convert RBG to Hex String
        return String.format("#%02x%02x%02x", red, blue, green);
    }

    /**
     * Store initialized tag into HashMap to remember the colour already given
     * so that subsequent occurence will use the same colour
     */
    private String getTagColor(String tagName) {
        if (!tagToColor.containsKey(tagName)) {
            tagToColor.put(tagName, generateRandomColor());
        }
        return tagToColor.get(tagName);
    }


    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Retrieve all tags from a person and initialize them
     * with a unique tag colour
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label uniqueTagLabel = new Label(tag.tagName);
            uniqueTagLabel.setStyle("-fx-background-color: " + getTagColor(tag.tagName));
            tags.getChildren().add(uniqueTagLabel);
        });
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
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
