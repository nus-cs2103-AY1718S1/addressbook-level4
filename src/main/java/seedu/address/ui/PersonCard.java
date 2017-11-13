package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static HashMap<String, int[]> tagColour = new HashMap<>();
    private static Random random = new Random();

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
    private ImageView imageView;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        initProfilePic(person);
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
        person.profilePicProperty().addListener((observable, oldValue, newValue) -> {
            imageView.setImage(new Image(person.getProfilePic().toString(), 128, 128, true, false));
        });
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    //@@author soonwj
    private int[] getTagColour(String tag) {
        int[] rgb = {random.nextInt(256), random.nextInt(256), random.nextInt(256)};

        if (!tagColour.containsKey(tag)) {
            tagColour.put(tag, rgb);
        }

        return tagColour.get(tag);
    }
    //@@author

    /**
     * Initalizes the tags with same colours for same tags
     *
     * @param person the person for which the tags are being initialized
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            int[] rgb = getTagColour(tag.tagName);

            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")");
            tags.getChildren().add(tagLabel);
        });
    }

    //@@author soonwj
    /**
     * Initializes the profile picture to be displayed by the PersonCard
     * @param person The person whose information is to be displayed in the PersonCard
     */
    private void initProfilePic(ReadOnlyPerson person) {
        String url = person.getProfilePic().toString();
        if (!ProfilePic.isValidUrl(url)) {
            url = ProfilePic.DEFAULT_URL;
        }
        imageView.setImage(new Image(url, 128, 128, true, false));
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
