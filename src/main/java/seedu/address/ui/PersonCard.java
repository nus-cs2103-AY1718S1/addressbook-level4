package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FontSizeChangeRequestEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final int DEFAULT_TAG_FONT_SIZE = 11;
    private static final int DEFAULT_SMALL_FONT_SIZE = 13;
    private static final int DEFAULT_BIG_FONT_SIZE = 16;

    public final ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private int fontSizeChange = 0;

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
        registerAsAnEventHandler(this);
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

    @Subscribe
    private void handleFontSizeChangeEvent(FontSizeChangeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        fontSizeChange = event.sizeChange;
        refreshFontSizes();
    }

    /**
     * Updates the font size of this card.
     */
    private void refreshFontSizes() {
        name.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));
        id.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));

        for (Label l : new Label[] { phone, address, email, socialMedia, remark, accesses }) {
            l.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
        }

        for (Node tag : tags.getChildren()) {
            tag.setStyle("-fx-font-size: " + (DEFAULT_TAG_FONT_SIZE + fontSizeChange));
        }
    }
}
