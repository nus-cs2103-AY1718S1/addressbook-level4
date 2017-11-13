package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains the details of a person.
 */
public class ViewPersonPanel extends UiPart<Region> {
    //@@author charlesgoh
    public static final int DEFAULT_NAME_SIZE = 15;
    public static final int DEFAULT_ATTRIBUTE_SIZE = 10;
    public static final int FONT_SIZE_EXTENDER = 5;
    public static final int DEFAULT_FONT_SIZE_MULTIPLIER = 0;
    //@@author
    //@@author Esilocke
    private static final String FXML = "ViewPersonPanel.fxml";
    /**
     * Preset values for random selection later.
     */
    private enum Colours {
        blue, green, brown, purple, navy, crimson, firebrick, maroon, red, black
    }
    private static HashMap<String, String> colourHash = new HashMap<String, String>();
    private static Random randomNumber = new Random();
    private static int nameSize = DEFAULT_NAME_SIZE;
    private static int attributeSize = DEFAULT_ATTRIBUTE_SIZE;

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private ReadOnlyPerson person;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox personPanel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label remark;
    @FXML
    private ImageView avatarImage;
    @FXML
    private Label email;

    private int fontSizeMultipler;
    public ViewPersonPanel(ReadOnlyPerson person, int fontSizeMultiplier) {
        super(FXML);
        this.person = person;
        this.fontSizeMultipler = fontSizeMultiplier;
        initializeWithPerson(person);
        initializeAvatar();
        updateAttributeSizes();
        registerAsAnEventHandler(this);
    }

    public ViewPersonPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void initializeWithPerson(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
    }

    //author charlesgoh
    /**
     * Sets avatar to a URL or filepath and falls back to the placeholder avatar if specified path fits in
     * neither categories
     */
    private void initializeAvatar() {
        String avatarPath = person.getAvatar().value;
        try {
            logger.info("Attempting to set avatar to image at specified URL or filepath");
            Image newImage = new Image(avatarPath);
            avatarImage.setImage(newImage);
        } catch (IllegalArgumentException ex) {
            logger.warning("Saved path is not a valid filepath or URL path. Setting avatar to placeholder");
            Image imagePlaceholder = new Image("file:docs/images/Avatar.png");
            avatarImage.setImage(imagePlaceholder);
        }
    }
    //@@author
    //@@author charlesgoh
    /**
     * Set default size for all attributes
     */
    public void updateAttributeSizes() {
        nameSize = DEFAULT_NAME_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);
        attributeSize = DEFAULT_ATTRIBUTE_SIZE + (fontSizeMultipler * FONT_SIZE_EXTENDER);

        // Set styles using set name and attribute sizes
        name.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        phone.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        address.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        remark.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
        email.setStyle("-fx-font-size: " + Integer.toString(attributeSize));
    }
    //@@author


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPersonPanel)) {
            return false;
        }

        // state check
        ViewPersonPanel card = (ViewPersonPanel) other;
        return person.equals(card.person);
    }

    public Label getName() {
        return name;
    }

    public Label getPhone() {
        return phone;
    }

    public Label getAddress() {
        return address;
    }

    public Label getRemark() {
        return remark;
    }

    public Label getEmail() {
        return email;
    }
    //@@author charlesgoh
    public int getFontSizeMultipler() {
        return fontSizeMultipler;
    }

    public void setFontSizeMultipler(int fontSizeMultipler) {
        this.fontSizeMultipler = fontSizeMultipler;
    }
    //@@author
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.person = event.getNewSelection().person;
        initializeWithPerson(person);
        initializeAvatar();
    }
}
