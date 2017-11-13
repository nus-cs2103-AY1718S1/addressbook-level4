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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.commons.events.ui.PersonFacebookOpenEvent;
import seedu.address.commons.events.ui.SearchMajorEvent;
import seedu.address.commons.events.ui.SearchNameEvent;
import seedu.address.commons.events.ui.ToggleFavoritePersonEvent;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

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
    private Label picture;
    @FXML
    private Label facebookPage;
    @FXML
    private Label major;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        this(person, displayedIndex, new IconImage());
    }

    public PersonCard(ReadOnlyPerson person, int displayedIndex, IconImage image) {
        super(FXML);
        initLabelColor();
        this.person = person;
        id.setText(displayedIndex + ". ");
        picture.setGraphic(new Circle(25, new ImagePattern(image.getCirclePerson())));
        initFavorite(person, image);
        initFbIcon(person, image);
        initTags(person);
        bindListeners(person, image);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person, IconImage image) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        //@@author heiseish
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        major.textProperty().bind(Bindings.convert(person.majorProperty()));
        person.favoriteProperty().addListener((observable, oldValue, newValue) -> initFavorite(person, image));
        person.facebookProperty().addListener((observable, oldValue, newValue) -> initFbIcon(person, image));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

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
            label.setStyle("-fx-background-color: transparent; "
                    + "-fx-font-size: 10px;"
                    + "-fx-font-family: Segoe UI;"
                    + "-fx-text-fill: #010504;"
                    + "-fx-border-color: " + color + "; "
                    + "-fx-border-width: 2;"
                    + "-fx-padding: 3 5 3 5;"
                    + "-fx-border-radius: 15 15 15 15; "
                    + "-fx-background-radius: 15 15 15 15;");
            tags.getChildren().add(label);
        });
    }

    /**
     * Instantiate favorite label
     */
    private void initFavorite(ReadOnlyPerson person, IconImage image) {
        favorite.setGraphic(person.getFavorite().favorite
                ? new ImageView(image.getHeart())
                : new ImageView(image.getHeartOutline()));
    }

    /**
     * Instantiate the facebook icon if a facebook account is linked with the person
     */
    private void initFbIcon(ReadOnlyPerson person, IconImage image) {
        facebookPage.setGraphic(new ImageView(image.getFbicon()));
        facebookPage.setVisible(!person.getFacebook().value.equals(""));
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

    /**
     * Handles press on the facebook icon
     */
    @FXML
    private void openFacebookPage() {
        raise(new PersonFacebookOpenEvent(person));
    }


    /**
     * Handles toggling the favorite attribute of a person
     */
    @FXML
    private void toggleFavorite() {
        raise(new ToggleFavoritePersonEvent(id.getText().substring(0, id.getText().indexOf("."))));
    }

    /**
     * Handles google searching for person's name
     */
    @FXML
    private void searchName() {
        raise(new SearchNameEvent(name.getText()));
    }

    /**
     * Handles google searching for person's major
     */
    @FXML
    private void searchMajor() {
        raise(new SearchMajorEvent(major.getText()));
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
