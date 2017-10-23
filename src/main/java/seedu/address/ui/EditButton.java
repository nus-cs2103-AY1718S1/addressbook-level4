package seedu.address.ui;

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

/**
 * The UI component that is responsible for editing selected contacts in the PersonListPanel.
 */
public class EditButton extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "EditButton.fxml";
    private static NameTextField nameTextField;
    private static PhoneTextField phoneTextField;
    private static EmailTextField emailTextField;
    private static AddressTextField addressTextFieldTextField;
    private static TagTextField tagTextField;
    private static int selectedIndex;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;

    @FXML
    private Button editButton;
    public EditButton(Logic logic, NameTextField ntf, PhoneTextField pft,
                      EmailTextField etf, AddressTextField atf, TagTextField ttf) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleEditButtonPressed() throws CommandException, ParseException {
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
