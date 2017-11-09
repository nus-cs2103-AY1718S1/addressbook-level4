//@@author hthjthtrh
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class GroupingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "createGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a group for the list of person based on group name(non-integer) "
            + "and index numbers provided\n"
            + "Parameters: GROUP_NAME (must not be an integer) INDEX [INDEX]... (must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " SmartOnes 1 4 2";

    public static final String MESSAGE_GROUPING_PERSON_SUCCESS = "Created group '%s' for people:\n";

    public static final String MESSAGE_DUPLICATE_GROUP_NAME = "This group already exists!\n";

    private List<Index> targetIdxs;
    private String groupName;

    public GroupingCommand(String groupName, List<Index> targetIndex) {
        this.groupName = groupName;
        this.targetIdxs = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<Integer> executableIdx = new ArrayList<>();
        Boolean hasExecutableIdx = false;

        for (Index idx : targetIdxs) {
            int intIdx = idx.getZeroBased();
            if (intIdx < lastShownList.size()) {
                executableIdx.add(intIdx);
                hasExecutableIdx = true;
            }
        }

        if (!hasExecutableIdx) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
        }

        List<ReadOnlyPerson> personToGroup = new ArrayList<>();
        executableIdx.forEach(idx -> personToGroup.add(lastShownList.get(idx)));

        try {
            model.createGroup(groupName, personToGroup);
        } catch (DuplicateGroupException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_GROUP_NAME);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);

        Index grpIndex = model.getGroupIndex(groupName);

        EventsCenter.getInstance().post(new JumpToListRequestEvent(grpIndex, true));

        return new CommandResult(getSb(groupName, personToGroup));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupingCommand // instanceof handles nulls
                && this.groupName.equals(((GroupingCommand) other).groupName)
                && this.targetIdxs.equals(((GroupingCommand) other).targetIdxs)); // state check
    }

    /**
     * Return a String
     * @param persons to be deleted
     * @return a String with all details listed
     */
    public static String getSb(String grpName, List<ReadOnlyPerson> persons) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_GROUPING_PERSON_SUCCESS, grpName));

        appendPersonList(sb, persons);

        return sb.toString();
    }
}
//@@author
