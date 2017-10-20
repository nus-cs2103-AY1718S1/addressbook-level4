package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import seedu.address.commons.core.index.Index;
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

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Group: %2$s";

    private final Index index;
    private final String group;

    /**
     * @param index of the person in the filtered person list to remove from the group
     * @param group from which the person is to be removed
     */
    public UngroupCommand(Index index, String group) {
        requireNonNull(index);
        requireNonNull(group);

        this.index = index;
        this.group = group;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), group));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UngroupCommand)) {
            return false;
        }

        // state check
        UngroupCommand e = (UngroupCommand) other;
        return index.equals(e.index)
                && group.equals(e.group);
    }
}
