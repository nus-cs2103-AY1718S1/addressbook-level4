package seedu.room.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.room.model.person.ReadOnlyPerson;


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
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ResidentBook level 4</a>
     */

    //tagColor matches a specific tag with a color
    private static ArrayList<String> colors = new ArrayList<String>(Arrays.asList(
            "#cc6600", "#cc0000", "#d11141", "#00b159", "#00aedb",
            "#f37735", "#ffc425", "#a200ff", "#742323", "#757a25", "#237629",
            "#2c2972", "#732474"));
    private static HashMap<String, String> tagColor = new HashMap<String, String>();
    private static Random random = new Random();

    public final ReadOnlyPerson person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label room;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
        initHighlightStatus();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        room.textProperty().bind(Bindings.convert(person.roomProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Initializes highlight background for highlighted persons
     */
    private void initHighlightStatus() {
        if (person.getHighlightStatus()) {
            cardPane.setStyle("-fx-background-color: #336D1C;");
        }
    }

    //@@author Haozhe321
    /**
     * Get the color related to a specified tag
     * @param tag the tag that we want to get the colour for
     * @return color of the tag in String
     */
    private static String getColorForTag(String tag) {
        if (!tagColor.containsKey(tag)) { //if the hashmap does not have this tag
            String chosenColor = colors.get(random.nextInt(colors.size()));
            tagColor.put(tag, chosenColor); //put the tag and color in
        }
        return tagColor.get(tag);
    }

    /**
     * initialise the tag with the colors and the tag name
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            String randomizedTagColor = getColorForTag(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + randomizedTagColor);
            tag.setTagColor(randomizedTagColor);
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
