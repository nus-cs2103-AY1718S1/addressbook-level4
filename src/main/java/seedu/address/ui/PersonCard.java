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
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {
    public static final int DEFAULT_NAME_SIZE = 15;
    public static final int DEFAULT_ATTRIBUTE_SIZE = 10;
    public static final int FONT_SIZE_EXTENDER = 5;
    public static final int DEFAULT_FONT_SIZE_MULTIPLIER = 0;
    private static final String FXML = "PersonListCard.fxml";
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
    private Label remark;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    private int fontSizeMultiplier;
    public PersonCard(ReadOnlyPerson person, int displayedIndex, int fontSizeMultiplier) {
        super(FXML);
        this.person = person;
        this.fontSizeMultiplier = fontSizeMultiplier;
        id.setText(displayedIndex + ". ");
        initTags(person);
        initFavourite(person);
        bindListeners(person);
        updateAttributeSizes();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        person.favouriteProperty().addListener((observable, oldValue, newValue) -> initFavourite(person));
    }

    //@@author wangyiming1019
    /**
     * display the heart for favourite person
     */
    private void initFavourite(ReadOnlyPerson person) {
        boolean favouriteStatus = person.getFavourite();
        Label favouriteLabel = new Label();
        Image starFilled = new Image(getClass().getResource("/images/heart.png").toExternalForm());
        if (favouriteStatus) {
            favouriteLabel.setGraphic(new ImageView(starFilled));
            favouriteLabel.setStyle("-fx-background-color: transparent; -fx-border-color: transparent");
        }
        cardPane.getChildren().add(favouriteLabel);
    }
    //@@author

    //@@author wangyiming1019
    /**
     * Locate hashed colour for tag. If not found, new colour is assigned to tag
     * @param tag
     * @return
     */
    private String getTagColour(String tag) {
        if (!colourHash.containsKey(tag)) {
            int randomiser = randomNumber.nextInt(Colours.values().length);
            String colour = Colours.values()[randomiser].toString();
            colourHash.put(tag, colour);
        }
        return colourHash.get(tag);
    }

    /**
     * Assigns each tag a colour
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label newTagLabel = new Label(tag.getTagName());

            newTagLabel.setStyle("-fx-background-color: " + this.getTagColour(tag.getTagName()));

            tags.getChildren().add(newTagLabel);
        });
    }
    //@@author
    //@@author charlesgoh
    /**
     * Set default size for all attributes
     */
    public void updateAttributeSizes() {
        nameSize = DEFAULT_NAME_SIZE + (fontSizeMultiplier * FONT_SIZE_EXTENDER);
        attributeSize = DEFAULT_ATTRIBUTE_SIZE + (fontSizeMultiplier * FONT_SIZE_EXTENDER);

        // Set styles using set name and attribute sizes
        name.setStyle("-fx-font-size: " + Integer.toString(nameSize));
        id.setStyle("-fx-font-size: " + Integer.toString(nameSize));
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
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }

    //@@author charlesgoh
    public Label getName() {
        return name;
    }

    public Label getId() {
        return id;
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

    public int getFontSizeMultiplier() {
        return fontSizeMultiplier;
    }

    public void setFontSizeMultiplier(int fontSizeMultiplier) {
        this.fontSizeMultiplier = fontSizeMultiplier;
    }
    //author
}
