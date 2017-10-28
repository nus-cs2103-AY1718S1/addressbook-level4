package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

import java.util.HashMap;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String PIN_ICON = "/images/pinned_icon.png";
    private HashMap<String, String> colourMap;

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
    private ImageView pinIcon;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex, HashMap<String, String> colourMap) {
        super(FXML);
        this.person = person;
        this.colourMap = colourMap;
        id.setText(displayedIndex + ". ");
        initTags(person);
        setPinIcon(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        pinIcon.setImage(setPinIcon(person));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    /**
     * Initialises all tags in FlowPane to show the person's tags and their colour.
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            setTagColour(tagLabel, tag);
            tags.getChildren().add(tagLabel);
        });
    }

    private void setTagColour(Label tagLabel, Tag tag) {
        if (colourMap.containsKey(tag.tagName)) {
            tagLabel.setStyle("-fx-background-color: " + colourMap.get(tag.tagName));
        } else {
            tagLabel.setStyle("-fx-background-color: blue");
        }
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

    private Image setPinIcon(ReadOnlyPerson person) {
        if (person.isPinned()) {
            return new Image(PIN_ICON);
        } else {
            return null;
        }
    }
}
