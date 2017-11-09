//@@author hthjthtrh
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DeselectAllEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Deletes a group, depending on the existence of the group
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the group name or index.\n"
            + "Parameters: GROUP_NAME\n"
            + "or: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " TestGroup\n"
            + "or: " + COMMAND_WORD + " 4";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %s.";

    public static final String MESSAGE_NONEXISTENT_GROUP = "The group '%s' does not exist.";

    public static final String MESSAGE_INDEXOUTOFBOUND_GROUP = "Provided index is out of range.";

    private String groupName;
    private Index index;
    private boolean isIndex;

    private Group grpToDelete;

    public DeleteGroupCommand(Object object, boolean isIndex) {
        if (isIndex) {
            this.index = (Index) object;
        } else {
            this.groupName = (String) object;
        }
        this.isIndex = isIndex;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (groupExists()) {
            model.deleteGroup(grpToDelete);

            EventsCenter.getInstance().post(new DeselectAllEvent());
            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupName));
        } else {
            if (!isIndex) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                        String.format(MESSAGE_NONEXISTENT_GROUP, groupName));
            } else {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                        MESSAGE_INDEXOUTOFBOUND_GROUP);
            }
        }

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof DeleteGroupCommand) {
            DeleteGroupCommand temp = (DeleteGroupCommand) other;
            if (isIndex) {
                return this.index.equals(temp.index);
            } else {
                return this.groupName.equals(temp.groupName);
            }
        }
        return false;

    }


    /**
     * Checks if the group to be deleted exists
     */
    private boolean groupExists() {
        List<Group> groupList = model.getAddressBook().getGroupList();
        if (!isIndex) {
            for (Group grp : groupList) {
                if (grp.getGrpName().equals(this.groupName)) {
                    grpToDelete = grp;
                    return true;
                }
            }
        } else {
            try {
                grpToDelete = groupList.get(index.getZeroBased());
                groupName = grpToDelete.getGrpName();
                return true;
            } catch (IndexOutOfBoundsException iobe) {
                return false;
            }
        }
        return false;
    }
}
//@@author
