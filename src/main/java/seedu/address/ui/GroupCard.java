package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.HashMap;
import java.util.Random;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    /**
     * An enum that contains the different tag colours.
     */
    private enum Colours {
        red, orange, yellow, green, blue, purple, grey
    }

    private static HashMap<String, String> tagColourSet = new HashMap<String, String>();
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
    private HBox groupCardPane;
    @FXML
    private Label groupName;
    @FXML
    private Label groupId;
    @FXML
    private Label groupPhone;
    @FXML
    private Label groupAddress;
    @FXML
    private Label groupEmail;
    @FXML
    private FlowPane groupTags;

    public GroupCard(ReadOnlyPerson person, int displayedIndex) {
        super(FXML);
        this.person = person;
        groupId.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        groupName.textProperty().bind(Bindings.convert(person.nameProperty()));
        groupPhone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        groupAddress.textProperty().bind(Bindings.convert(person.addressProperty()));
        groupEmail.textProperty().bind(Bindings.convert(person.emailProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            groupTags.getChildren().clear();
            initTags(person);
        });
    }

    private static String setTagColour(String tagName) {
        if (!tagColourSet.containsKey(tagName)) {
            tagColourSet.put(tagName, Colours.values()[random.nextInt(Colours.values().length)].toString());
        }

        return tagColourSet.get(tagName);

    }

    /**
     * Initialise every contact's tag with its randomly assigned colours
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(
            tag -> {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setStyle("-fx-background-color: " + setTagColour(tag.tagName));
                groupTags.getChildren().add(tagLabel);
            });
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCard)) {
            return false;
        }

        // state check
        GroupCard card = (GroupCard) other;
        return groupId.getText().equals(card.groupId.getText())
                && person.equals(card.person);
    }
}
