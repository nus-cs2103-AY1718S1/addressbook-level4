package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.UniqueMeetingList;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Data have been cleared!";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook(), new UniqueMeetingList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
