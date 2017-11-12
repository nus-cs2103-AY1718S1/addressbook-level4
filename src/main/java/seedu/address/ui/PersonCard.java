package seedu.address.ui;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.events.ui.PhotoChangeEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String FILE_PATH = "photos/";
    private static final String DEFAULT_FILE_PATH = "/images/default.jpeg";
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
    private FlowPane tags;
    @FXML
    private ImageView photo;

    //@@author JasmineSee
    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
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
        setImage();

        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    /**
     * Handles photo change
     */
    @Subscribe
    private void handlePhotoChange(PhotoChangeEvent event) {
        setImage();
    }

    /**
     * Handles logic and set images
     */
    private void setImage() {

        File file = new File(FILE_PATH + person.getEmail().toString() + ".png");
        Path path = Paths.get(FILE_PATH + person.getEmail().toString() + ".png");

        if (Files.exists(path)) {
            Image image = new Image(file.toURI().toString(), 150, 150, false, false);
            photo.setImage(image);

        } else {
            URL url = getClass().getResource(DEFAULT_FILE_PATH);
            try {
                Image img = new Image(url.openStream(), 150, 150, false, false);
                photo.setImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    //@@author

    /**
     * Init person tags with colour
     */
    //@@author blaqkrow
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            if (tag.tagName.equals("colleagues")) {
                tagLabel.setStyle("-fx-background-color: red;");
            } else if (tag.tagName.equals("friends")) {
                tagLabel.setStyle("-fx-background-color: greenyellow;");
            } else {
                tagLabel.setStyle("-fx-background-color: grey;");

            }
            tags.getChildren().add(tagLabel);
        });
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
