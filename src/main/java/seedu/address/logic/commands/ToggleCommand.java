package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.TogglePanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Toggles between event and browser panels.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles between event display and browser.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TOGGLE_SUCCESS = "Successfully toggled.";

    public ToggleCommand() {

    }

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new TogglePanelEvent());
        return new CommandResult(MESSAGE_TOGGLE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToggleCommand // instanceof handles nulls
                && this.equals((other))); // state check
    }
}
