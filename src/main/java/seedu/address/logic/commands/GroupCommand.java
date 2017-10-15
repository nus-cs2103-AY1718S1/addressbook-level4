package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Adds person with given index to a group. If such a group does not exist, creates it and then adds the person.
 */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds person to the group identified "
            + "by the group's name. Person to be added is specified by "
            + "index used in the last person listing. "
            + "Non existing group will be created before adding persons to it.\n"
            + "Parameters: " + "INDEX (must be a positive integer) "
            + PREFIX_GROUP_NAME + "GROUP_NAME.\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_GROUP_NAME + "Family";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Group: %2$s";

    private final Index index;
    private final Group groupName;

    /**
     * @param index of the person in the filtered person list to add to the group
     * @param groupName of the group
     */
    public GroupCommand(Index index, Group groupName) {
        requireNonNull(index);
        requireNonNull(groupName);

        this.index = index;
        this.groupName = groupName;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), groupName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCommand)) {
            return false;
        }

        // state check
        GroupCommand e = (GroupCommand) other;
        return index.equals(e.index)
                && groupName.equals(e.groupName);
    }
}
