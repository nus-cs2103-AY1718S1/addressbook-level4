package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;

/**
 * Shows all groups in the addressbook
 * a temporary implementation
 */
public class ViewGroupsCommand extends Command {

    public static final String COMMAND_WORD = "viewGroups";

    public static final String MESSAGE_SUCCESS = "Listing all groups:\n";

    public static final String MESSAGE_EMPTY_GROUP_LIST = "There is no groups yet.";

    @Override
    public CommandResult execute() throws CommandException {
        List<Group> groupList = model.getAddressBook().getGroupList();
        if (groupList.size() == 0) {
            throw new CommandException(MESSAGE_EMPTY_GROUP_LIST);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS);
        model.getAddressBook().getGroupList().forEach(group -> {
            sb.append(group.getGrpName());
            sb.append("\n");
        });
        return new CommandResult(sb.toString());
    }
}
