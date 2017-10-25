package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Adds a person to the address book.
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "gdelete";
    public static final String COMMAND_ALT = "gd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";

    private final Index targetIndex;

    public DeleteGroupCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyGroup> lastShownList = model.getFilteredGroupList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        ReadOnlyGroup groupToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteGroup(groupToDelete);
        } catch (GroupNotFoundException gnfe) {
            assert false : "The target group cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteGroupCommand) other).targetIndex)); // state check
    }
}
