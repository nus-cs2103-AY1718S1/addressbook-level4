package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes the graphs tabs shown on the graph panel in the address book.
 */
//@@author limcel
public class TabCommand extends Command {
    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SELECT_TAB_SUCCESS = "Selected Tab: %1$s";
    public static final String MESSAGE_INVALID_TAB_INDEX = "Invalid Tab Value";

    // there are only 2 types of graphs available for display (Graph / Bar)
    public static final int TAB_NUMBER = 2;

    private final Index targetIndex;

    /**
     * Selects @param targetIndex of the TabPane in the graph panel for switching
     */
    public TabCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (targetIndex.getZeroBased() >= TAB_NUMBER) {
            throw new CommandException(MESSAGE_INVALID_TAB_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TAB_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TabCommand // instanceof handles nulls
                && this.targetIndex.equals(((TabCommand) other).targetIndex)); // state check
    }
}
