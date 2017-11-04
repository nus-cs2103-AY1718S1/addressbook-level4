package seedu.address.ui.person;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.TagColorChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.TagColorManager;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    private static final String FXML = "person/PersonListCard.fxml";
    private static final String TAG_COLOR_CSS = "-fx-background-color: %s";

    // Keep a record of the displayed persons.
    public final ReadOnlyPerson person;

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
        initTags();
        bindListeners(person);
        registerAsAnEventHandler(this);
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
            initTags();
        });
    }

    //@@author yunpengn
    /**
     * Initializes all the tags of a person displayed in different random colors.
     */
    private void initTags() {
        person.getTags().forEach(tag -> {
            String tagName = tag.tagName;
            Label newTagLabel = new Label(tagName);
            try {
                newTagLabel.setStyle(String.format(TAG_COLOR_CSS, TagColorManager.getColor(tag)));
            } catch (TagNotFoundException e) {
                System.err.println("An existing must have a color.");
            }
            tags.getChildren().add(newTagLabel);
        });
    }

    @Subscribe
    public void handleTagColorChange(TagColorChangedEvent event) {
        // TODO: improve efficiency here. Update rather than re-create all labels.
        tags.getChildren().clear();
        initTags();
    }
    //@@author yunpengn

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
