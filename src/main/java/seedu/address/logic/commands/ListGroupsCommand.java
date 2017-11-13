//@@author hthjthtrh
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DeselectAllEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Shows all groups in the addressbook
 * a temporary implementation
 */
public class ListGroupsCommand extends Command {

    public static final String COMMAND_WORD = "listGroups";

    public static final String MESSAGE_SUCCESS = "Listing all groups:\n";

    public static final String MESSAGE_EMPTY_GROUP_LIST = "There is no groups yet.";

    @Override
    public CommandResult execute() throws CommandException {
        List<Group> groupList = model.getAddressBook().getGroupList();
        if (groupList.size() == 0) {
            return new CommandResult(MESSAGE_EMPTY_GROUP_LIST);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS);

        int grpListSize = model.getAddressBook().getGroupList().size();

        for (int i = 1; i <= grpListSize; i++) {
            sb.append(i + ". ");
            sb.append((model.getFilteredGroupList().get(i - 1).getGrpName()));
            if (i != grpListSize) {
                sb.append("\n");
            }
        }

        EventsCenter.getInstance().post(new DeselectAllEvent());

        return new CommandResult(sb.toString());
    }
}
//@@author
