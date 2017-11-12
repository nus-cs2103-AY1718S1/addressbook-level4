package seedu.address.ui;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.StorageManager;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    public static final String PROFILE_PHOTO_FILENAME_FORMAT = "img_%1$d.jpg";
    public static final String DEFAULT_PROFILE_PHOTO_FILENAME = "default_profile_photo.jpg";
    private static final String FXML = "PersonListCard.fxml";

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
    private ImageView gravatar;
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
        initPicture(person);
        initTags(person);
        bindListeners(person);
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
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    /**
     * Initializes the tags with text values and alternating colours between each index
     * in the list
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            if (Integer.parseInt(id.getText().replace(". " , "")) % 2 == 0) {
                tagLabel.setStyle("-fx-background-color: deepskyblue;"
                        + " -fx-font-size: 15px;"
                        + " -fx-text-fill: black");
            } else {
                tagLabel.setStyle("-fx-background-color: #adcbe3;"
                        + " -fx-font-size: 15px;"
                        + " -fx-text-fill: black");
            }
            tags.getChildren().add(tagLabel);
        });
    }

    //@@author liuhang0213
    /**
     * Initializes the profile picture using Gravatar
     */
    private void initPicture(ReadOnlyPerson person) {

        Image image;

        try {
            FileInputStream imageFile = StorageManager.loadCacheFile(String.format(PROFILE_PHOTO_FILENAME_FORMAT,
                    person.getInternalId().value));
            image = new Image(imageFile);
            imageFile.close();
            gravatar.setImage(image);
        } catch (IOException e) {
            // Likely download failed, use default
            LogsCenter.getLogger("").fine("Unable to read profile image file, using default profile photo.");
        }
    }

    //@@author
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
