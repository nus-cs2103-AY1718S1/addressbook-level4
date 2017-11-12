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
import seedu.address.commons.events.ui.ToggleSearchBoxStyle;
import seedu.address.commons.events.ui.ToggleToAliasViewEvent;
import seedu.address.commons.events.ui.ToggleToAllPersonViewEvent;
import seedu.address.commons.events.ui.ToggleToTaskViewEvent;
import seedu.address.commons.events.ui.ValidResultDisplayEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.FindCommand;
import seedu.address.logic.commands.person.FindPinnedCommand;
import seedu.address.logic.commands.person.ListCommand;
import seedu.address.logic.commands.person.ListPinCommand;
import seedu.address.logic.commands.person.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author Alim95

/**
 * The panel for sort menu and search box of the App.
 */
public class SortFindPanel extends UiPart<Region> {

    private static final String FXML = "SortFindPanel.fxml";

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
        raise(new NewResultAvailableEvent(""));
        try {
            if (searchBox.getPromptText().contains("Person") || searchBox.getPromptText().contains("Task")) {
                if (searchBox.getText().trim().isEmpty()) {
                    logic.execute(ListCommand.COMMAND_WORD);
                } else {
                    logic.execute(FindCommand.COMMAND_WORD + " " + searchBox.getText());
                    raise(new ValidResultDisplayEvent(FindCommand.COMMAND_WORD));
                }
            } else if (searchBox.getPromptText().contains("Pinned")) {
                if (searchBox.getText().trim().isEmpty()) {
                    logic.execute(ListPinCommand.COMMAND_WORD);
                } else {
                    logic.execute(FindPinnedCommand.COMMAND_WORD + " " + searchBox.getText());
                    raise(new ValidResultDisplayEvent(FindPinnedCommand.COMMAND_WORD));
                }
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
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + nameItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
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
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + phoneItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
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
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + emailItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
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
            CommandResult result = logic.execute(SortCommand.COMMAND_WORD + " " + addressItem.getText());
            raise(new NewResultAvailableEvent(result.feedbackToUser));
            raise(new ValidResultDisplayEvent(SortCommand.COMMAND_WORD));
        } catch (CommandException | ParseException e1) {
            logger.warning("Failed to sort address using sort menu");
        }
    }

    /**
     * Handles switch to task view event
     */
    @Subscribe
    private void handleToggleToTaskViewEvent(ToggleToTaskViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToTaskView();
    }

    /**
     * Handles switch to all person view event
     */
    @Subscribe
    private void handleToggleToAllPersonViewEvent(ToggleToAllPersonViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToPersonView();
    }

    /**
     * Handles switch to alias view event
     */
    @Subscribe
    private void handleToggleToAliasViewEvent(ToggleToAliasViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchToAliasView();
    }

    /**
     * Handles switch to pinned person view event
     */
    @Subscribe
    private void handleToggleSearchBoxStyleEvent(ToggleSearchBoxStyle event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.isPinnedStyle()) {
            switchToPinnedPersonSearchStyle();
        } else {
            switchToAllPersonSearchStyle();
        }
    }

    /**
     * Switches style to alias view.
     */
    private void switchToAliasView() {
        searchBox.setVisible(false);
        sortMenu.setVisible(false);
    }

    /**
     * Switches style to pinned person search.
     */
    private void switchToPinnedPersonSearchStyle() {
        searchBox.setText("");
        searchBox.setPromptText("Search Pinned...");
    }

    /**
     * Switches style to all person search.
     */
    private void switchToAllPersonSearchStyle() {
        searchBox.setText("");
        searchBox.setPromptText("Search Person...");
    }

    /**
     * Switches style to task view.
     */
    private void switchToTaskView() {
        searchBox.setText("");
        searchBox.setPromptText("Search Task...");
        sortMenu.setVisible(false);
        searchBox.setVisible(true);
    }

    /**
     * Switches style to person view.
     */
    private void switchToPersonView() {
        searchBox.setText("");
        searchBox.setPromptText("Search Person...");
        sortMenu.setVisible(true);
        searchBox.setVisible(true);
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
