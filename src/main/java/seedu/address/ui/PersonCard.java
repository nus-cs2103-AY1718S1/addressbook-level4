package seedu.address.ui;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static HashMap<String, String> tagColors = new HashMap<String, String>();

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
    private Label emailAddress;
    @FXML
    private Label birthdate;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView photo;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
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
        emailAddress.textProperty().bind(Bindings.convert(person.emailAddressProperty()));
        birthdate.textProperty().bind(Bindings.convert(person.birthdateProperty()));


        try {
            StringExpression filePath = Bindings.convert(person.photoProperty());
            FileInputStream imageInputStream = new FileInputStream(filePath.getValue());
            Image image = new Image(imageInputStream, 100, 200, true, true);
            photo.setImage(image);
            imageInputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        getPhoto();

        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Initializes all the Tags for a given person
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    //@@author ritchielq
    private static String getColorForTag(String tagName) {
        if (!tagColors.containsKey(tagName)) {
            tagColors.put(tagName, getRandomDarkColor());
        }

        return tagColors.get(tagName);
    }

    private static String getRandomDarkColor() {
        Random random = new Random();

        int red;
        int green;
        int blue;

        do {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
        } while ((red * 0.299) + (green * 0.587) + (blue * 0.114) > 186);

        return "rgb(" + red + "," + green + "," + blue + ")";
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

    //@@author wenzongteo
    /**
     * Bind the path of a contact's display picture into an Image and set the image into the ImageView photo.
     */
    public void getPhoto() {
        try {
            StringExpression filePath = Bindings.convert(person.photoProperty());
            FileInputStream imageInputStream = new FileInputStream(filePath.getValue());
            Image image = new Image(imageInputStream, 100, 200, true, true);
            photo.setImage(image);
            imageInputStream.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
