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
import seedu.address.commons.events.ui.FontSizeRefreshRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String FXML_WITHOUT_ACCESSES = "PersonListCardNoAccess.fxml";

    private static final int DEFAULT_TAG_FONT_SIZE = 11;
    private static final int DEFAULT_SMALL_FONT_SIZE = 13;
    private static final int DEFAULT_BIG_FONT_SIZE = 16;

    public final ReadOnlyPerson person;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Logic logic;

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

    private boolean isAccessDisplayed;

    public PersonCard(ReadOnlyPerson person, int displayedIndex, boolean isAccessDisplayed, Logic logic) {
        super(isAccessDisplayed ? FXML : FXML_WITHOUT_ACCESSES);
        this.person = person;
        this.isAccessDisplayed = isAccessDisplayed;
        this.logic = logic;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person, isAccessDisplayed);
        registerAsAnEventHandler(this);
        refreshFontSizes();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person, boolean isAccessDisplayed) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        socialMedia.textProperty().bind(Bindings.convert(person.socialMediaProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        if (isAccessDisplayed) {
            accesses.textProperty().bind(Bindings.convert(person.accessCountProperty()));
        }
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

    // @@author donjar
    @Subscribe
    private void handleFontSizeChangeEvent(FontSizeRefreshRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        refreshFontSizes();
    }

    /**
     * Refreshes the font size of this card.
     */
    private void refreshFontSizes() {
        int fontSizeChange = logic.getFontSizeChange();
        name.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));
        id.setStyle("-fx-font-size: " + (DEFAULT_BIG_FONT_SIZE + fontSizeChange));

        for (Label l : new Label[] { phone, address, email, socialMedia, remark }) {
            l.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
        }
        if (isAccessDisplayed) {
            accesses.setStyle("-fx-font-size: " + (DEFAULT_SMALL_FONT_SIZE + fontSizeChange));
        }

        for (Node tag : tags.getChildren()) {
            tag.setStyle("-fx-font-size: " + (DEFAULT_TAG_FONT_SIZE + fontSizeChange));
        }
    }
    // @@author
}
