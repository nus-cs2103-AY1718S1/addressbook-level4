package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = { "Brown", "CadetBlue", "Coral", "Bisque", "DarkOrange", "DarkRed",
        "Gold", "LightBlue", "Olive", "PaleVioletRed", "Crimson",
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

    private static String getColorForTag(String tagString) {
        String color = colors[random.nextInt(colors.length)];
        if (!tagColorMap.containsKey(tagString)) {
            tagColorMap.put(tagString, color);
        } else {
            color = tagColorMap.get(tagString);
        }
        return color;
    }
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

    /**
     * Binds the Tag with a randomly generated color
     */
    private void initTags(ReadOnlyPerson person) {
        boolean fav = false;
        for (Tag tag : person.getTags()) {
            if (!tag.tagName.toLowerCase().contains("fav")) {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
                tags.getChildren().add(tagLabel);
            } else {
                fav = true;
            }
        }
        if (fav) {
            Circle circle = new Circle(0, 0, 8);
            circle.setFill(Color.CORAL);
            tags.getChildren().add(circle);
        }
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
