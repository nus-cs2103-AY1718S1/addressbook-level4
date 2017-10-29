package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class GroupCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "group";
    public static final String MESSAGE_SUCCESS = "New group added: %1$s, with %2$s member(s)";

    /**
     * Shows message usage for Group Command
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds one or more people into a group\n"
            + "Parameters: "
            + PREFIX_NAME + "GROUP NAME "
            + PREFIX_INDEX + "INDEX...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2103 Project "
            + PREFIX_INDEX + "1 3 4";

    private GroupName groupName;
    private List<Index> indexes;

    public GroupCommand(GroupName name, List<Index> indexList) {
        groupName = name;
        indexes = indexList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> groupMembers = new ArrayList<>();

        for (Index index: indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            ReadOnlyPerson personToAdd = lastShownList.get(index.getZeroBased());
            groupMembers.add(personToAdd);
        }

        Group newGroup = new Group(groupName, groupMembers);

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName, groupMembers.size()));
    }
}
