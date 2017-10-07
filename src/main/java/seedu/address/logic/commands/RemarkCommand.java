package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import seedu.address.logic.commands.exceptions.CommandException;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified by INDEX.\n"
            + "Parameters: "
            + "INDEX "
            + PREFIX_REMARK + "[REMARK]\n"
            + "EXAMPLE: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + " Friend.";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark Command to be implemented";

    @Override
    public final CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
