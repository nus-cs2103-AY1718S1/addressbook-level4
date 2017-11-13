package seedu.address.logic.commands;
//@@author pjunwei95
import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cls";
    public static final String MESSAGE_SUCCESS = "Weaver has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBook());
        model.clearBrowserPanel();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
