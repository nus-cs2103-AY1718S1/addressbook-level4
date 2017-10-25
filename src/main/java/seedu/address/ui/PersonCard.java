package seedu.address.ui;

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

    private static final int DEFAULT_SMALL_FONT_SIZE = 13;
    private static final int DEFAULT_BIG_FONT_SIZE = 16;
    private static int fontSizeChange = 0;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;

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
    private Label socialMedia;
    @FXML
    private Label remark;
    @FXML
    private Label accesses;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
        refreshFontSizes();
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
        socialMedia.textProperty().bind(Bindings.convert(person.socialMediaProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        accesses.textProperty().bind(Bindings.convert(person.accessCountProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
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

    /**
     * Changes the font size of all text inside this class by the amount of {@code change}.
     * Note that existing cards will not have its font size changed. Call {@code refreshFontSizes}
     * on existing cards to update their fonts.
     */
    public static void changeFontSize(int change) {
        fontSizeChange += change;
    }

    /**
     * Resets the font size of all text inside this class into its defaults.
     * Note that existing cards will not have its font size changed. Call {@code refreshFontSizes}
     * on existing cards to update their fonts.
     */
    public static void resetFontSize() {
        fontSizeChange = 0;
    }

    /**
     * Updates the font size of this card.
     */
    public void refreshFontSizes() {
        name.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));
        id.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));
        phone.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
        address.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
        email.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
        remark.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
    }
}
