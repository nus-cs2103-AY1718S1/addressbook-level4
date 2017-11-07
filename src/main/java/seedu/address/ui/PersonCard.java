package seedu.address.ui;

import java.util.HashMap;
import java.util.Random;

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

    //@@author yanji1221
    private static String[] colors = { "red", "blue", "orange", "brown", "green", "pink", "black",
        "grey", "purple" , "gold", "crimson", "navy", "darkBlue", "mediumBlue", "darkGreen",
        "teal", "darkCyan", "deepSkyBlue", "lime", "springGreen", "midnightBlue", "forestGreen",
        "seaGreen", "royalBlue", "indigo", "darkOliveGreen" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
    private static int[] usedColors = new int[colors.length];
    //@@author

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
    //@@author yanji1221
    @FXML
    private Label birthday;
    //@@author quangtdn
    @FXML
    private Label profile;
    //@@author
    @FXML
    private FlowPane tags;
    @FXML
    private Label ingroups;
    @FXML
    private FlowPane groups;

    public PersonCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        initTags(person);
        initGroups(person);
        bindListeners(person);
    }
    //@@author yanji1221
    /**
     * Color getter for a tag
     */
    private static String colorGetterForTag(String tagValue) {
        int colorCode;
        boolean isUsedUpAllColors = true;
        for (int i = 0; i < colors.length; i++) {
            if (usedColors[i] == 0) {
                isUsedUpAllColors = false;
                break;
            }
        }
        if (isUsedUpAllColors) {
            for (int j = 0; j < colors.length; j++) {
                usedColors[j] = 0;
            }
        }

        if (!tagColors.containsKey(tagValue)) {
            do {
                colorCode = random.nextInt(colors.length);
            } while(usedColors[colorCode] == 1);
            usedColors[colorCode] = 1;
            tagColors.put(tagValue, colors[colorCode]);
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
        //@@author yanji1221
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        //@@author
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        profile.textProperty().bind(Bindings.convert(person.profilepageProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        ingroups.textProperty().setValue("In Groups:  ");
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
        person.groupProperty().addListener((observable, oldValue, newValue) -> {
            groups.getChildren().clear();
            initGroups(person);
        });
    }
    //@@author yanji1221
    /**
     * Distribute colors for tags
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + colorGetterForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    private void initGroups(ReadOnlyPerson person) {
        person.getGroups().forEach(group -> {
            Label groupLabel = new Label(group.getName().fullName);
            groupLabel.setStyle("-fx-background-color: " + colorGetterForTag(group.getName().fullName));
            groups.getChildren().add(groupLabel);
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
