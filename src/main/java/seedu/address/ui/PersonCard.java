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
    private static String[] colors = { "#007bff", "#868e96", "#28a745", "#dc3545", "#ffc107", "#17a2b8"};
    private static HashMap<String, String> colorMapping = new HashMap<String, String>();

    public final ReadOnlyPerson person;

    private final int cardNum;
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
    //@@author siri99
    @FXML
    private Label birthday;
    //@@author siri99
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label score;
    @FXML
    private FlowPane tags;

    //@@author Jacob Vosburgh
    public PersonCard(ReadOnlyPerson person, int displayedIndex, Logic inlogic) {
        super(FXML);
        this.person = person;
        logic = inlogic;
        //@@author Jacob Vosburgh
        cardNum = displayedIndex;
        id.setText(cardNum + ". ");
        initTags(person);
        bindListeners(person);
    }
    //@@author Jacob Vosburgh
    /**
     * Provides a consistent color based on the first letter of a tag
     * ie, the same color will return for every call using 'friend' or any other tag.
     * This also matches the color scheme used to choose colors in the browser, so tags appear the same in both.
     * @param tagValue
     */

    private String mapTagToColor(String tagValue) {
        if (!colorMapping.containsKey(tagValue)) {
            int x = Character.getNumericValue(tagValue.charAt(0)) + 87; //magic number to match to javascript char->int
            colorMapping.put(tagValue, colors[x % 6]); //colors.length]);
        }
        return colorMapping.get(tagValue);
    }
    //@@author Jacob Vosburgh
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     * @param person
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        //@@author siri99
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        //@@author siri99
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        score.textProperty().bind(Bindings.convert(person.scoreProperty()));
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
    //@@author Jacob Vosburgh

    /**
     * handles button events given to it by the fxml doc that it is set as controller for by the constructor in UiPart
     * @param buttonEvent
     */
    @FXML
    private void handleDeleteButtonAction(ActionEvent buttonEvent) {
        try {
            CommandResult commandResult = new CommandResult("");
            String justIndex = id.getText().substring(0, id.getText().length() - 2);
            String delCommand = "delete " + justIndex;
            //@@author
            //@@author siri99
            if (logic.getCurrentList().contains("favlist")) {
                commandResult = new CommandResult("Delete command does not work in favourite list");;
            } else {
                commandResult = logic.execute(delCommand);
            }
            //@@author siri99
            //@@author Jacob Vosburgh
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Delete call failed on index " + id.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * handles edit button presses, triggering a new window.
     * @param buttonEvent
     */
    @FXML
    private void handleEditButtonAction(ActionEvent buttonEvent) {
        //@@author Jacob Vosburgh
        //@@author siri99
        CommandResult commandResult = new CommandResult("");
        if (logic.getCurrentList().contains("favlist")) {
            commandResult = new CommandResult("Edit command does not work in favourite list");;
        } else { //@@author siri99
            //@@author Jacob Vosburgh
            EditWindow editWindow = new EditWindow(logic, cardNum);
            editWindow.show();
        }
        logger.info("Result: " + commandResult.feedbackToUser);
        raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        //@@author Jacob Vosburgh
    }
}
