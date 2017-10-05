package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to a person in the address book. "
            + "Parameters: "
            + "INDEX "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 " + PREFIX_REMARK
            + "Likes to drink coffee.";

    public CommandResult executeUndoableCommand() throws CommandException{
        throw new CommandException("dummy text");
    }
}
