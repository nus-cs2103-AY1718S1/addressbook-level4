package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 + * Changes the birthday of an existing person in the address book.
 + */

public class BirthdayCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "birthday";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the birthday of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing birthday will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BIRTHDAY + "[BIRTHDAY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BIRTHDAY + "01011995";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Birthday command not implemented yet";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
