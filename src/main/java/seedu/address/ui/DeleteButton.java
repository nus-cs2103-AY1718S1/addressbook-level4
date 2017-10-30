package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
//@@author blaqkrow
/**
 * The UI component that is responsible for deleting selected contacts in the PersonListPanel.
 */
public class DeleteButton extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "DeleteButton.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private int selectedIndex;

    @FXML
    private Button deleteButton;

    public DeleteButton(Logic logic, int selectedIn) {
        super(FXML);
        this.logic = logic;
        this.selectedIndex = selectedIn;
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleDeleteButtonPressed() throws CommandException, ParseException, IOException {
        CommandResult commandResult = logic.execute("delete " + getSelectedIndex());
        logger.info("Result: " + commandResult.feedbackToUser);
    }

    private void setSelectedIndex(int i) {
        selectedIndex = i;
    }

    private int getSelectedIndex() {
        return selectedIndex;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        int baseOneIndex = event.getSelectionIndex() + 1;
        setSelectedIndex(baseOneIndex);
    }
}
