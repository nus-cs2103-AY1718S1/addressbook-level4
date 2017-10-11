package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import seedu.address.model.person.Remark;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a remark to the person identified by the index number used in the last person listing."
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + PREFIX_REMARK + "REMARK\n"
            + "Example: " + COMMAND_WORD + " 1"
            + PREFIX_REMARK + "Likes to drink coffee";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Remark added";
    public static final String MESSAGE_ADD_REMARK_FAILURE = "Remark does nothing";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit
     * @param remark details to add remarks
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), remark));
        /**
        try {
            return null;
        } catch (Exception e) {
            throw e;
        }
        **/
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }
        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
