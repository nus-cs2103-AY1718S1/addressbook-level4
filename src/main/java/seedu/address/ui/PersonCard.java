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

    private static final String FXML = "PersonListCard.fxml";
    private static final String ICON = "/images/heart.png";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */
    private static HashMap<String, String> labelColor = new HashMap<String, String>();

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
    private Label remark;
    @FXML
    private Label favorite;
    @FXML
    private FlowPane tags;


    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        initLabelColor();
        this.person = person;
        id.setText(displayedIndex + ". ");
        initFavorite(person);
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
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.favoriteProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != oldValue) {
                favorite.setVisible(!favorite.isVisible());
            } else {
                favorite.setVisible(false);
            }
        });
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }
    //@@author A0143832J
    /**
     * Prepare a HashMap of some default colors to link {@code tagName} with a color
     */
    private void initLabelColor() {
        labelColor.put("colleagues", "red");
        labelColor.put("friends", "blue");
        labelColor.put("family", "brown");
        labelColor.put("neighbours", "purple");
        labelColor.put("classmates", "green");
    }

    /**
     * Instantiate tags
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label label = new Label(tag.tagName);
            String color = getColor(tag.tagName);
            label.setPrefHeight(23);
            label.setStyle("-fx-background-color: " + color + "; "
                    + "-fx-border-radius: 15 15 15 15; "
                    + "-fx-background-radius: 15 15 15 15;");
            tags.getChildren().add(label);
        });
    }

    /**
     * Instantiate favorite label
     */
    private void initFavorite(ReadOnlyPerson person) {
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream(ICON)));
        image.setFitHeight(25);
        image.setFitWidth(25);
        favorite.setGraphic(image);
        favorite.setVisible(person.getFavorite().favorite);
    }


    /**
     * Get color from the hashmap. If not found, generate a new index and a random color
     * @param tagName specify the index
     * @return a color string
     */
    private String getColor(String tagName) {
        if (labelColor.containsKey(tagName)) {
            return labelColor.get(tagName);
        } else {
            Random random = new Random();
            // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
            int nextInt = random.nextInt(256 * 256 * 256);
            // format it as hexadecimal string (with hashtag and leading zeros)
            String colorCode = String.format("#%06x", nextInt);
            labelColor.put(tagName, colorCode);
            return labelColor.get(tagName);
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
