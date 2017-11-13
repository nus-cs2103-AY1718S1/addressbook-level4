package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author sofarsophie
/**
 * The UI component that is responsible for processing a GUI-interaction triggered sort..
 */
public class SortControls extends UiPart<Region> {

    private static final String FXML = "SortControls.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    @FXML
    private ComboBox sortByDropdown;

    @FXML
    private Label dropdownLabel;

    public SortControls(Logic logic) {
        super(FXML);
        this.logic = logic;
        setDropDown();
        handleItemSelection();
    }

    /**
     * Sets the items in the dropdown list.
     */
    private void setDropDown() {
        sortByDropdown.getItems().addAll(
                "Name",
                "Address",
                "Phone",
                "Email",
                "Group"
        );
        sortByDropdown.getSelectionModel().selectFirst();
    }

    /**
     * Handles the event when a user selects an item from the dropdown list by
     * setting the matching prefix and triggering logic to execute a new sort command with the prefix.
     */

    private void handleItemSelection() {
        sortByDropdown.setOnAction((event) -> {
            String selectedField = sortByDropdown.getSelectionModel().getSelectedItem().toString();
            Prefix prefix;
            switch (selectedField) {
            case "Name": prefix = PREFIX_NAME;
                break;
            case "Address": prefix = PREFIX_ADDRESS;
                break;
            case "Phone": prefix = PREFIX_PHONE;
                break;
            case "Email": prefix = PREFIX_EMAIL;
                break;
            case "Group": prefix = PREFIX_GROUP;
                break;
            default: prefix = PREFIX_NAME;
            }
            try {
                CommandResult commandResult = logic.execute("sort " + prefix);
                initHistory();
                historySnapshot.next();
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

            } catch (CommandException | ParseException e) {
                initHistory();
                logger.info("Invalid sort");
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
        });
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }
}
//@@author
