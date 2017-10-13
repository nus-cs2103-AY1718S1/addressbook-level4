package seedu.address.logic.commands;

import seedu.address.model.AddressBook;

import static java.util.Objects.requireNonNull;

/*
* sorts the addressbook
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = "Sort the Addressbook alphabetically.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book has been sorted!";

    public SortCommand() {}


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.sort();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}