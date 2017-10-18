package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRE;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sets expiry date of a person in the address book.
 */

public class ExpireCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "expire";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the expiry date "
            + "of the person identified by the index number used in the last person listing. "
            // overwrittable?
            + "Parameters: INDEX (must be positive integer) "
            + PREFIX_EXPIRE + "[DATE in YYYY-MM-DD format]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPIRE + "2017-09-09";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "expire commnad to be implemented";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
