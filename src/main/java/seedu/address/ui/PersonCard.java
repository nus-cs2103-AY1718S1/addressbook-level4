package seedu.address.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = { "#ff8080", "#009999", "#4da6ff", "#ff9933", "#00e68a", "#ff80ff", "grey" };
    private static HashMap<String, String> colorMapping = new HashMap<String, String>();

    public final ReadOnlyPerson person;

    private final Logic logic;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Button delete;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;


    public PersonCard(ReadOnlyPerson person, int displayedIndex, Logic inlogic) {
        super(FXML);
        this.person = person;
        logic = inlogic;
        id.setText(displayedIndex + ". ");
        initTags(person);
        bindListeners(person);
    }

    /**
     * Provides a consistent color based on the string of a tag's value
     * ie, the same color will return for every call using 'friend' or any other tag.
     */

    private String mapTagToColor(String tagValue) {
        if (!colorMapping.containsKey(tagValue)) {
            colorMapping.put(tagValue, colors[tagValue.length() % colors.length]);
        }
        return colorMapping.get(tagValue);
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
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });
    }

    /**
     * Initializes color tags
     * initializes tags for the person
     * @param person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + mapTagToColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
        //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * checks equality to the person
     * @param other
     * @return if the person equals the param
     */
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
     * handles button events given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent buttonEvent) {
        try {
            String justIndex = id.getText().substring(0, id.getText().length() - 2);
            String delCommand = "delete " + justIndex;
            CommandResult commandResult = logic.execute(delCommand);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Delete call failed on index " + id.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }

    }
}
