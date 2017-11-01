package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.events.ui.ToggleToPersonViewEvent;
import seedu.address.commons.events.ui.ToggleToTaskViewEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author Alim95

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
    private TextField searchBox;

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
        registerAsAnEventHandler(this);
    }

    /**
     * Handles search field changed event.
     */
    @FXML
    private void handleSearchFieldChanged() {
        try {
            if (searchBox.getText().trim().isEmpty()) {
                logic.execute(LIST_COMMAND_WORD);
            } else {
                logic.execute(FIND_COMMAND_WORD + " " + searchBox.getText());
            }
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to find person in search box");
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
            logger.warning("Failed to sort name using sort menu");
        }
    }

    /**
     * Handles phone item pressed event.
     */
    @FXML
    private void handlePhoneItemPressed() {
        try {
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + phoneItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort phone using sort menu");
        }
    }

    /**
     * Handles email item pressed event.
     */
    @FXML
    private void handleEmailItemPressed() {
        try {
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + emailItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort email using sort menu");
        }
    }

    /**
     * Handles address item pressed event.
     */
    @FXML
    private void handleAddressItemPressed() {
        try {
            CommandResult result = logic.execute(SORT_COMMAND_WORD + " " + addressItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort address using sort menu");
        }
    }

    /**
     * Handles switch to task view event
     */
    @Subscribe
    private void handleToggleToTaskViewEvent(ToggleToTaskViewEvent event) {
        switchToTaskView();
    }

    @Subscribe
    private void handleToggleToPersonViewEvent(ToggleToPersonViewEvent event) {
        switchToPersonView();
    }

    /**
     * Switches style to person view.
     */
    private void switchToPersonView() {
        searchBox.setPromptText("Search Person...");
        sortMenu.setVisible(true);
    }

    /**
     * Switches style to task view.
     */
    private void switchToTaskView() {
        searchBox.setPromptText("Search Task...");
        sortMenu.setVisible(false);
    }

    public MenuButton getSortMenu() {
        return sortMenu;
    }

    public TextField getSearchBox() {
        return searchBox;
    }

    public void highlightSortMenu() {
        sortMenu.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    public void highlightSearchBox() {
        searchBox.setStyle("-fx-border-color: lightgreen; -fx-border-width: 2");
    }

    /**
     * Unhighlights the sort menu and search box.
     */
    public void unhighlight() {
        sortMenu.setStyle("");
        searchBox.setStyle("");
    }
}
