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
//@@author willxujun
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

    public TextField getTextField() {
        return searchTextField;
    }

    /**
     * Captures user input in searchBox.
     */
    @FXML
    private void handleKeyTyped(KeyEvent keyEvent) {
        String s = keyEvent.getCharacter();
        if (isDeleteOrBackspace(s)) {
            if (!searchBuffer.isEmpty()) {
                searchBuffer = searchBuffer.substring(0, searchBuffer.length() - 1);
            } else {
                return;
            }
        } else {
            searchBuffer = searchBuffer + s;
        }
        try {
            CommandResult commandResult = logic.executeSearch(searchBuffer);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            logger.info("Invalid search: " + searchTextField.getText());
            raise(new NewResultAvailableEvent((e.getMessage())));
        }
    }

    private boolean isDeleteOrBackspace (String toTest) {
        return toTest.equals("\u0008") || toTest.equals("\u007F");
    }

}
