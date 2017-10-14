package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The panel for sort menu and search box of the App.
 */
public class SortFindPanel extends UiPart<Region> {

    private static final String FXML = "SortFindPanel.fxml";
    private static final String SORT_COMMAND_WORD = "sort";
    private static final String FIND_COMMAND_WORD = "find";
    private static final String LIST_COMMAND_WORD = "list";

    private final Logger logger = LogsCenter.getLogger(SortFindPanel.class);
    private final Logic logic;

    @FXML
    private TextField searchField;

    @FXML
    private MenuButton sortMenu;

    @FXML
    private MenuItem nameItem;

    @FXML
    private MenuItem phoneItem;

    @FXML
    private MenuItem emailItem;

    @FXML
    private MenuItem addressItem;

    public SortFindPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
    }

    /**
     * Handles search field changed event.
     */
    @FXML
    private void handleSearchFieldChanged() {
        try {
            if (searchField.getText().trim().isEmpty()) {
                logic.execute(LIST_COMMAND_WORD);
            } else {
                logic.execute(FIND_COMMAND_WORD + " " + searchField.getText());
            }
        } catch (CommandException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Handles name item pressed event.
     */
    @FXML
    private void handleNameItemPressed() {
        try {
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + nameItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort name");
        }
    }

    /**
     * Handles name item pressed event.
     */
    @FXML
    private void handlePhoneItemPressed() {
        try {
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + phoneItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort name");
        }
    }

    /**
     * Handles name item pressed event.
     */
    @FXML
    private void handleEmailItemPressed() {
        try {
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + emailItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort name");
        }
    }

    /**
     * Handles name item pressed event.
     */
    @FXML
    private void handleAddressItemPressed() {
        try {
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + addressItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort name");
        }
    }
}
