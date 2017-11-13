package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author eldonng
/**
 * Creates a group in the address book
 */
public class CreateGroupCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "creategroup";
    public static final String MESSAGE_SUCCESS = "New group added: %1$s, with %2$s member(s)";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

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

    public CreateGroupCommand(GroupName name, List<Index> indexList) {
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
            if (!groupMembers.contains(personToAdd)) {
                groupMembers.add(personToAdd);
            }
        }
        ReadOnlyGroup newGroup = new Group(groupName, groupMembers);
        try {
            model.addGroup(newGroup);
        } catch (DuplicateGroupException dge) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }
        model.updateFilteredGroupList(Model.PREDICATE_SHOW_ALL_GROUPS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName, groupMembers.size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateGroupCommand // instanceof handles nulls
                && this.groupName.equals(((CreateGroupCommand) other).groupName)
                && this.indexes.equals(((CreateGroupCommand) other).indexes)); // state check
    }
}
