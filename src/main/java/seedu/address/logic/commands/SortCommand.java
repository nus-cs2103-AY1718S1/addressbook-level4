//@@author yuheng222

package seedu.address.logic.commands;

/**
 * Sorts all persons in the address book lexicographically.
 */

public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all persons in the Address Book alphabetically by their name.";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
