package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
//@@author Pengyuz
/**
 * Clears the recyclebin.
 */
public class BinclearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-fresh";
    public static final String MESSAGE_SUCCESS = "Recyclebin has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear all the person in the recyclebin.";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetRecyclebin(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
