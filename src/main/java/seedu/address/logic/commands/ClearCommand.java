package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String[] COMMAND_WORDS = {"clear", "clr", "c", "cl"};
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    //@@author arnollim
    @Override
    public String toString() {
        return COMMAND_WORD;
    }
    //@@author
}
