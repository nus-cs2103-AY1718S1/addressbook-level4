package seedu.address.ui;

import java.util.HashMap;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = { "red", "blue", "orange", "brown", "purple", "black", "gray", "maroon", "coral",
        "blueviolet", "slategrey", "darkseagreen", "darkturquoise", "darkkhaki", "firebrick", "darkcyan" };
    private static HashMap<String, String> tagColors = new HashMap<>();
    private static int tagNumber = 0;

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
    private Label birthday;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initFavouriteLabel(person);
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
        person.favouriteProperty().addListener((observable, oldValue, newValue) -> initFavouriteLabel(person));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Sets the colour of a favourite label based on its favourite status
     */
    private void initFavouriteLabel(ReadOnlyPerson person) {
        boolean favouriteStatus = person.getFavourite().getFavourite();
        String textToDisplay = favouriteStatus ? "Fav" : "";
        //Label favouriteLabel = new Label(textToDisplay);
        Button favouriteButton = new Button(textToDisplay);
        Image starFilled = new Image(getClass().getResource("/images/Gold_Star.png").toExternalForm(),
                30, 30, true, true);
        Image starTransparent = new Image(getClass().getResource("/images/Star-star.png").toExternalForm(),
                30, 30, true, true);
        if (favouriteStatus) {
            //favouriteLabel.setStyle("-fx-background-color: orangered");
            favouriteButton.setGraphic(new ImageView(starFilled));
            favouriteButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");
        } else {
            //favouriteLabel.setStyle("-fx-background-color: cornflowerblue");
            favouriteButton.setGraphic(new ImageView((starTransparent)));
            favouriteButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");

        }
        //cardPane.getChildren().add(favouriteLabel);
        cardPane.getChildren().add(favouriteButton);
    }

    /**
     * Binds the individual tags shown for each contact to a different color
     * so that it is clearer for the user.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach((Tag tag) -> {
            Label tagLabel = new Label(tag.tagName);
            if (tag.tagName.equalsIgnoreCase("friends") || tag.tagName.equalsIgnoreCase("friend")) {
                tagLabel.setStyle("-fx-background-color: green");
            } else {
                tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            }
            tags.getChildren().add(tagLabel);
        });
    }

    private String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[tagNumber]);
            tagNumber++;
        }
        if (tagNumber >= colors.length) {
            tagNumber = 0;
        }

        return tagColors.get(tagValue);
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
