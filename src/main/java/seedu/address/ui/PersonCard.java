package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Shape;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = { "DarkCyan", "CadetBlue", "DarkOrange",
        "Gold", "Olive", "PaleVioletRed", "LightSeaGreen", "OrangeRed", "YellowGreen",
        "Chocolate", "Plum"};
    private static HashMap<String, String> tagColorMap = new HashMap<>();
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
    private Shape fav;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    //@@author ZhangH795
    private static String getColorForTag(String tagString) {
        String color = "";
        boolean uniqueColor = false;
        while (!uniqueColor) {
            color = colors[random.nextInt(colors.length)];
            if (!tagColorMap.containsKey(tagString)) {
                if (!tagColorMap.containsValue(color)) {
                    tagColorMap.put(tagString, color);
                    break;
                }
            } else {
                color = tagColorMap.get(tagString);
                break;
            }
        }
        return color;
    }
    //@@author ZhangH795

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    //@@author ZhangH795
    /**
     * Binds the Tag with a randomly generated color
     */
    private void initTags(ReadOnlyPerson person) {
        boolean favourite = false;
        for (Tag tag : person.getTags()) {
            if (!tag.tagName.toLowerCase().contains("fav")) {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
                tags.getChildren().add(tagLabel);
            } else {
                favourite = true;
            }
        }
        if (favourite) {
            fav.setVisible(true);
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
