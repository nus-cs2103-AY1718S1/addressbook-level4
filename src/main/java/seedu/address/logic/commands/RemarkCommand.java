package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Remark;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. \n"
            + "Parameters: INDEX" + " "
            + PREFIX_REMARK
            + "Likes to drink coffee \n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to drink coffee \n"
            + "Removing Remarks: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK;

    // The first argument is referenced by "1$", the second by "2$"
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Remark: %2$s";


    private final Index personIndex;
    private final Remark remark;

    /**
     * Creates a RemarkCommand to add the remark
     */
    public RemarkCommand(Index inputIndex, Remark inputRemark) {
        requireNonNull(inputIndex);
        requireNonNull(inputRemark);

        this.personIndex = inputIndex;
        this.remark = inputRemark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, personIndex.getOneBased(), remark));
    }

    @Override
    public boolean equals(Object other) {
        // Check if
        // (a) Object is the same object
        // (b) Object is an instance of the object and that personIndex and remarkString are the same
        return other == this ||
                (other instanceof RemarkCommand &&
                        this.personIndex.equals(((RemarkCommand) other).personIndex)) &&
                        this.remark.equals(((RemarkCommand) other).remark);
    }

}
