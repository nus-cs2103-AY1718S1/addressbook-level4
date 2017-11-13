package seedu.address.ui;

import java.io.File;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.util.TagColor;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final Integer IMAGE_WIDTH = 100;
    private static final Integer IMAGE_HEIGHT = 100;
    private static final String DIRECTORY_SAVING_PATH = "pictures/";
    private static final String DEFAULT_IMAGE_PATH = "/images/defaulddp.png";
    private static final String EMPTY_STRING = "";
    private static final String IMAGE_EXTENSION = ".png";

    private static String[] colors = {"#ff0000", "#0000ff", "#008000", "#ff00ff", "#00ffff"};
    private static Random random = new Random();

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyPerson person;
    private TagColor tagColorObject;


    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label nickname;
    @FXML
    private FlowPane tags;
    @FXML
    private Circle displayPicture;


    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        tagColorObject = TagColor.getInstance();
        initTags(person);
        bindListeners(person);
    }

    /**
     * Assigns a random color to a tag if it does not exist in the HashMap
     * returns a String containing the color
     */
    private String getTagColor(String tag) {
        if (!tagColorObject.containsTag(tag)) {
            tagColorObject.addColor(tag, colors[random.nextInt(colors.length)]);
        }
        return tagColorObject.getColor(tag);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        nickname.textProperty().bind(Bindings.convert(person.nicknameProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        assignImage(person);
    }
    //@@author tshradheya

    /**
     * Assigns image pattern to the shape to display image
     */
    private void assignImage(ReadOnlyPerson person) {

        if (!person.getDisplayPicture().getPath().equals(EMPTY_STRING)) {

            Image image = new Image("file:" + DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION, IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            // Defensive programming to take care of image corruption
            File file = new File(DIRECTORY_SAVING_PATH + person.getDisplayPicture().getPath()
                    + IMAGE_EXTENSION);
            if (!file.exists()) {
                image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                        IMAGE_WIDTH, IMAGE_HEIGHT, false, false);
            }
            displayPicture.setFill(new ImagePattern(image));

        } else {
            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_IMAGE_PATH),
                    IMAGE_WIDTH, IMAGE_HEIGHT, false, false);

            displayPicture.setFill(new ImagePattern(image));
        }
    }
    //@@author

    /**
     * Initialize tags for the respective person
     * @param person whose tags have to be added and assigned color
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: derive(" + getTagColor(tag.tagName) + ", -20%)");
            tags.getChildren().add(tagLabel);
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
