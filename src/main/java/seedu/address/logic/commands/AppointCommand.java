package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Appoint;

/**
 * Changes the appoint of an existing person in the address book.
 */
public class AppointCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appoint";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Makes appointment for the person identified "
            + "by the index number used in the last person listing. "
            + "Existing appoint will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPOINT + "[APPOINT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINT + "18/10/2017 14:30";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Appoint: %2$s";

    private final Index index;
    private final Appoint appoint;

    /**
     * @param index of the person in the filtered person list to edit the appoint
     * @param appoint of the person
     */
    public AppointCommand(Index index, Appoint appoint) {
        requireNonNull(index);
        requireNonNull(appoint);

        this.index = index;
        this.appoint = appoint;
    }


    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), appoint));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointCommand)) {
            return false;
        }

        // state check
        AppointCommand e = (AppointCommand) other;
        return index.equals(e.index)
                && appoint.equals(e.appoint);
    }
}