package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.group.Group;

//@@author kengying

/**
 * Lists all group in the address book to the user.
 */
public class ListGroupCommand extends ListCommand {

    public static final String SEARCH_TERM = "groups";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SEARCH_TERM + " : List all groups\n"
            + "Example: " + COMMAND_WORD +  " " + SEARCH_TERM;

    public static final String MESSAGE_SUCCESS = "List all groups: ";

    @Override
    public CommandResult execute() {
        List<Group> listGroups = model.getGroupList();
        return new CommandResult(String.format(MESSAGE_SUCCESS + " "
                + listGroups.toString().replaceAll("[\\[\\]]", "")));
    }

}
