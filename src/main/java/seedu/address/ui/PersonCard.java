package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] randomColors = { "navy", "teal", "violet", "green", "purple", "black" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
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
    private ImageView favorite;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox socialInfos;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        initSocialInfos(person);
        bindListeners(person);
    }

    /**
     * Binds a label with a specific or random color and store it into tagColors HashMap.
     */
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            if (tagValue.equalsIgnoreCase("colleagues")
                    || tagValue.equalsIgnoreCase("colleague")) {
                tagColors.put(tagValue, "red");
            } else if (tagValue.equalsIgnoreCase("friends")
                    || tagValue.equalsIgnoreCase("friend")) {
                tagColors.put(tagValue, "grey");
            } else { // Randomly assign colors from randomColors String array
                tagColors.put(tagValue, randomColors[random.nextInt(randomColors.length)]);
            }
        }
        return tagColors.get(tagValue);
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
        initFav(person);
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        person.socialInfoProperty().addListener((observable, oldValue, newValue) -> {
            socialInfos.getChildren().clear();
            initSocialInfos(person);
        });
    }

    /**
     * Changes the star metaphor image for each {@code Person} according to their favorite status
     */
    private void initFav(ReadOnlyPerson person) {
        if (!person.getFavorite().isFavorite()) {
            favorite.setImage(null);
        }
    }

    /**
     * Creates a tag label for each {@code Person} and assign a color to the style of each tag label.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Creates a social info label for each {@code Person}
     */
    private void initSocialInfos(ReadOnlyPerson person) {
        person.getSocialInfos().forEach(socialInfo -> {
            String labelText = socialInfo.getSocialType() + ": " + socialInfo.getUsername();
            Label socialLabel = new Label(labelText);
            socialLabel.getStyleClass().add("cell_small_label");
            socialInfos.getChildren().add(socialLabel);
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
