package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * This command is used to add people to an existing group
 * If the group doesn't already exist, the command will create it
 */

public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [group name] [names to add to group]. Example: "
            + COMMAND_WORD + " Vietnam Grant Ali Joey";
    public static final String MESSAGE_PARAMETERS = "[group name] [person names ...]";

    private final String groupName;
    private final List<String> names;

    public GroupCommand (String groupName, List<String> names) {
        this.groupName = groupName;
        this.names = names;
    }

    /**
     * This is just a stub for execute right now. Need to update the model component before it will work.
     * Coming in v1.3
     */
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && names.equals(((GroupCommand) other).names));
    }
}
