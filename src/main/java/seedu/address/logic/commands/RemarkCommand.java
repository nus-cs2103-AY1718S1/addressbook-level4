package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

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

    private final Index targetIndex;

    public RemarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_ADD_REMARK_FAILURE);
        /**
        try {
            return null;
        } catch (Exception e) {
            throw e;
        }
        **/
    }
}
