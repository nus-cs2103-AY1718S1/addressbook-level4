package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes the facebook link of an existing person in the address book.
 */
public class LinkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a facebook link to person identified "
            + "by the index number used in the last person listing. "
            + "Existing links will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LINK + "[LINK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK + "https://www.facebook.com/profile.php?id=100021659181463";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Link command not implemented yet";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}