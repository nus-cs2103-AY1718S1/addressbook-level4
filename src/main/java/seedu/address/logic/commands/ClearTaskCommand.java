package seedu.address.logic.commands;
//@@author Esilocke
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.model.AddressBook;

/**
 * Clears only the tasks in the address book.
 */
public class ClearTaskCommand extends ClearCommand {

    public static final String MESSAGE_SUCCESS = "All tasks have been cleared!";

    @Override
    //@@author Esilocke
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetPartialData(new AddressBook(), PREFIX_TASK);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
