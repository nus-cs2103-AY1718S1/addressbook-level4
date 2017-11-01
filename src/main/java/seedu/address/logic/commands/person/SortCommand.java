package seedu.address.logic.commands.person;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleSortByLabelEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
//@@author Alim95

/**
 * Lists all persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "List has been sorted by %s.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list by name, phone, email or address \n"
            + "Parameters: KEYWORD\n"
            + "Example for name sort: " + COMMAND_WORD + " name\n"
            + "Example for phone sort: " + COMMAND_WORD + " phone";

    private final String toSort;

    public SortCommand(String toSort) {
        this.toSort = toSort;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortList(toSort);
        EventsCenter.getInstance().post(new ToggleSortByLabelEvent(toSort));
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSort));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.toSort.equals(((SortCommand) other).toSort)); // state check
    }
}
