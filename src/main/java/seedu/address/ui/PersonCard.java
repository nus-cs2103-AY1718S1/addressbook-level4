package seedu.address.ui;

import java.util.HashMap;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static ArrayList<String> availableColorsLeft = new ArrayList<String>(
            Arrays.asList(
                    "TOMATO",
                    "TURQUOISE",
                    "LIGHTGRAY",
                    "GOLDENROD",
                    "LAWNGREEN",
                    "BURLYWOOD",
                    "PALEVIOLETRED",
                    "CORNFLOWERBLUE",
                    "CORAL",
                    "MOCCASIN",
                    "SPRINGGREEN",
                    "ORANGERED"));
    private static HashMap<String, String> currentTagColors = new HashMap<String, String>();

    private static String assignedColor;

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
    private Label formClass;
    @FXML
    private FlowPane tags;


    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initialiseTags(person);
        bindListeners(person);
    }

    /**
     * This method takes in the tagName, and returns the color associated with that tagName
     * If the if the tag has no associated color, a random one from the list available will be given.
     * Once the list runs out, all other tags will be grey.
     *
     * @param tagName is the String name of the tag
     * @return the color associated to the tagName
     */
    public static String obtainTagColors(String tagName) {
        if (!currentTagColors.containsKey(tagName)) {
            if (availableColorsLeft.size() != 0) {
                Random rand = new Random();
                int randIndex = rand.nextInt(availableColorsLeft.size());
                assignedColor = availableColorsLeft.get(randIndex);

                currentTagColors.put(tagName, assignedColor);
                availableColorsLeft.remove(randIndex);
            } else {
                currentTagColors.put(tagName, "GRAY");
            }
        }
        return currentTagColors.get(tagName);
    }

    /**
     * To access private String assignedColor for testing
     */
    public String getAssignedTagColor() {
        return this.assignedColor;
    }

    /**
     * To access private ArrayList availableColorsLeft for testing
     */
    public ArrayList<String> getAvailableColorsLeft() {
        return this.availableColorsLeft;
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        formClass.textProperty().bind(Bindings.convert(person.formClassProperty()));
        tags.getChildren().clear();
        initialiseTags(person);
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

    /**
     * Initialise the {@code person} tags
     *
     * @param person Person to be assigned tag colour.
     */
    private void initialiseTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + obtainTagColors(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
}
