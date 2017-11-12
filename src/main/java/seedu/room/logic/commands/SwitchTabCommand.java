package seedu.room.logic.commands;

import seedu.room.commons.core.EventsCenter;
import seedu.room.commons.events.ui.SwitchTabRequestEvent;
import seedu.room.logic.commands.exceptions.CommandException;

/**
 * Switches between Residents tab and Events tab.
 */
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches between Residents tab and Events tab.\n"
            + "Parameters: INDEX (must 1 or 2)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SWITCH_TAB_SUCCESS = "Switched to %1$s tab";

    private final int targetIndex;
    private final String tabName;

    public SwitchTabCommand(int targetIndex) {
        System.out.println("The command received: " + targetIndex);

        this.targetIndex = targetIndex;
        if (targetIndex == 1) {
            tabName = "Residents";
        } else if (targetIndex == 2) {
            tabName = "Events";
        } else {
            tabName = "";
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (targetIndex > 2 || targetIndex < 1) {
            throw new CommandException(MESSAGE_USAGE);
        }

        EventsCenter.getInstance().post(new SwitchTabRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SWITCH_TAB_SUCCESS, tabName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchTabCommand // instanceof handles nulls
                && this.targetIndex == (((SwitchTabCommand) other).targetIndex)); // state check
    }
}
