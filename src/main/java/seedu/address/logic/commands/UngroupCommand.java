package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Removes a person from a group
 */
public class UngroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "ungroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes from the group with name GROUP_NAME "
            + "the person identified by the index number used in the last person listing."
            + "If it was the last member of the group, the group is also removed.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_GROUP_NAME + "GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUP_NAME + "Family";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Ungroup command not implemented yet";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
