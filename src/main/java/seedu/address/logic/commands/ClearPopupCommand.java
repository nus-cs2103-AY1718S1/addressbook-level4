package seedu.address.logic.commands;
//@@author pjunwei95
import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearPopupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.clearBrowserPanel();
        model.resetData(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
