package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRE;
//import static seedu.address.model.person.ExpiryDate.MESSAGE_EXPIRY_DATE_CONSTRAINTS;

//import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.person.ExpiryDate;

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

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Date string: %2$s";

    private final Index index;
    private final String date;

    public ExpireCommand(Index index, String date) {
        requireNonNull(index);
        requireNonNull(date);

        this.index = index;
        // create ExpiryDate object
        this.date = date;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExpireCommand)) {
            return false;
        }

        ExpireCommand temp = (ExpireCommand) other;
        return (index.equals(temp.index) && date.equals(temp.date));
    }
}
