package seedu.address.logic.commands;


/**
 * Sorts all persons in the address book alphabetically for the user.
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons in the list";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
