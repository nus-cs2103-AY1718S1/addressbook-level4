package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static java.util.Objects.requireNonNull;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommand extends UndoableCommand{

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. \n"
            + "Parameters: INDEX" + " "
            + PREFIX_REMARK
            + "Likes to drink coffee \n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to drink coffee \n"
            + "Removing Remarks: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK;

    public static final String MESSAGE_WORK_IN_PROGRESS = "Remark Command Work in Progress";

    private final Index personIndex;
    private final String remarkString;

    /**
     * Creates an RemarkCommand to add the remark
     */
    public RemarkCommand(Index inputIndex, String inputString) {
        requireNonNull(inputIndex);
        requireNonNull(inputString);

        this.personIndex=inputIndex;
        this.remarkString=inputString;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_WORK_IN_PROGRESS);
    }
}
