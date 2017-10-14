package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user search command.
 */
public class SearchBox extends UiPart<Region> {

    private static final String FXML = "SearchBox.fxml";

    private final Logger logger = LogsCenter.getLogger(SearchBox.class);
    private final Logic logic;

    private String searchBuffer;

    @FXML
    private TextField searchTextField;

    public SearchBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        searchBuffer = "";
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case BACK_SPACE: case DELETE:
            searchBuffer = searchBuffer.substring(0, searchBuffer.length() - 1);
            try {
                CommandResult commandResult = logic.executeSearch(searchBuffer);
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

            } catch (CommandException | ParseException e) {
                logger.info("Invalid search: " + searchTextField.getText());
                raise(new NewResultAvailableEvent((e.getMessage())));
            }
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Handles the Key typed event
     */
    @FXML
    private void handleKeyTyped(KeyEvent keyEvent) {
        String s = keyEvent.getCharacter();
        searchBuffer = searchBuffer + s;
        try {
            CommandResult commandResult = logic.executeSearch(searchBuffer);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            logger.info("Invalid search: " + searchTextField.getText());
            raise(new NewResultAvailableEvent((e.getMessage())));
        }
    }

}
