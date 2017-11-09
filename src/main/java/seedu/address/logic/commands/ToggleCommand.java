package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.BrowserPanel;

//@@author seankwekjk
/**
 * Toggles Browser function of the Select Command.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String TOGGLE_SUCCESS = "Browser Panel Toggled to ";

    @Override
    public CommandResult execute() throws CommandException {
        BrowserPanel.setBrowserMode();
        EventsCenter.getInstance().post(new ToggleChangedEvent());
        return new CommandResult(composeCommandResult());
    }

    /**
    * Composes the Command Result @String based on the status of the toggled boolean variable
    */
    private String composeCommandResult() {
        if (BrowserPanel.getBrowserMode()) {
            return TOGGLE_SUCCESS + "Display Address";
        }   else {
            return TOGGLE_SUCCESS + "Display Social Media";
        }
    }
}
