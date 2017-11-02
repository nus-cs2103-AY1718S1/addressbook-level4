package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

//@@author hj2304
/**
* sorts the addressbook
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = "Sort the Addressbook alphabetically.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book has been sorted!";

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author hj2304
