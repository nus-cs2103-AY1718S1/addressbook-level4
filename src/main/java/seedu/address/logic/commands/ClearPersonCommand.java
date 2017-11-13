package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import seedu.address.model.AddressBook;

/**
 * Clears only the contacts in the address book.
 */
public class ClearPersonCommand extends ClearCommand {

    public static final String MESSAGE_SUCCESS = "All contacts have been cleared!";

    @Override
    //@@author Esilocke
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetPartialData(new AddressBook(), PREFIX_PERSON);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
