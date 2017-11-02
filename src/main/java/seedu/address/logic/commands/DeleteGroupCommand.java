//@@author hthjthtrh
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Deletes a group, depending on the existence of the group
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the group name.\n"
            + "Parameters: GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " TestGroup";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %s.";

    public static final String MESSAGE_NONEXISTENT_GROUP = "The group '%s' does not exist.";

    private final String groupName;

    private Group grpToDelete;

    public DeleteGroupCommand(String grpName) {
        this.groupName = grpName.trim();
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (groupExists()) {
            model.deleteGroup(grpToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupName));
        } else {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, String.format(MESSAGE_NONEXISTENT_GROUP, groupName));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && this.groupName.equals(((DeleteGroupCommand) other).groupName)); // state check
    }


    /**
     * Checks if the group to be deleted exists
     */
    private boolean groupExists() {
        List<Group> groupList = model.getAddressBook().getGroupList();
        for (Group grp : groupList) {
            if (grp.getGrpName().equals(this.groupName)) {
                grpToDelete = grp;
                return true;
            }
        }
        return false;
    }
}
//@@author
