package seedu.address.ui;

import java.util.HashMap;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = { "red", "forestgreen", "olive", "orange", "teal", "wheat", "pink",
        "oldlace", "limegreen", "yellow", "aquamarine", "thistle", "cadetblue", "lightskyblue" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Integer colourNum = 0;
    private static final String FAVOURITE_IMAGE = "/images/favouriteIcon.png";
    private static final String NON_FAVOURITE_IMAGE = "/images/nonFavouriteIcon.png";
    private static Image favouritedImage = AppUtil.getImage(FAVOURITE_IMAGE);
    private static Image nonFavouritedImage = AppUtil.getImage(NON_FAVOURITE_IMAGE);
    private static final ObjectProperty<FavouriteStatus> favouriteTrue =
            new SimpleObjectProperty<>(new FavouriteStatus(true));

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
    private ImageView favouriteStatus;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }
    //@@author limyongsong
    /**
     * Assigns different colour to tags w/ different names
     * Defaults to Grey colour when all colours are used up
     * @param tagValue is the tagName
     * @return
     */
    private static String getColorForTag(String tagValue) {

        if ((!tagColors.containsKey(tagValue)) && (colourNum < colors.length)) {
            tagColors.put(tagValue, colors[colourNum++]);
        } else if ((colourNum >= colors.length) && (!tagColors.containsKey(tagValue))) {
            colourNum = 0; //Resets the color num for reuse
            tagColors.put(tagValue, colors[colourNum++]);
        } else if (tagColors.containsKey(tagValue)) {
            //if the tag already has a colour in the hasmap, we do not need to do anything
        } else {
            tagColors.put(tagValue, "grey"); //just in case anything gets past
        }
        return tagColors.get(tagValue);

    }
    //@@author
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        favouriteStatus.imageProperty().bind(Bindings.when(person.favouriteStatusProperty().isEqualTo(favouriteTrue))
                .then(favouritedImage).otherwise(nonFavouritedImage));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }
    //@@author limyongsong
    /**
     * Get the tags from a person and assign a colour to each tag
     * before add the tag as a children (on scenebuilder) of the person on the app list
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            String setColour = "-fx-background-color: " + getColorForTag(tag.tagName);
            tagLabel.setStyle("-fx-border-color:black; -fx-border-width: 1; -fx-border-style: solid; " + setColour);
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
