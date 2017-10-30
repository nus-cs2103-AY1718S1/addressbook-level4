package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.NoPersonsException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a person to the address book.
 */
public class RemovePersonFromGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "gremove";
    public static final String COMMAND_ALT = "gr";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a person from a group. "
            + "Parameters: "
            + PREFIX_GROUP + "GROUP INDEX "
            + "p/PERSON INDEX"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GROUP + "2" + "p/1";

    public static final String MESSAGE_SUCCESS = "Removed %1$s from %2$s.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exist in the group.";
    public static final String MESSAGE_EMPTY_GROUP = "The group is empty.";

    private final Index personIndex;
    private final Index groupIndex;

    /**
     * Creates an CreateGroupCommand to add the specified {@code ReadOnlyGroup}
     */
    public RemovePersonFromGroupCommand(Index groupIndex, Index personIndex) {
        this.groupIndex = groupIndex;
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyGroup> lastShownGroupList = model.getFilteredGroupList();
        List<ReadOnlyPerson> lastShownPersonList = model.getFilteredPersonList();

        if (groupIndex.getZeroBased() >= lastShownGroupList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        ReadOnlyGroup targetGroup = lastShownGroupList.get(groupIndex.getZeroBased());
        String groupName = targetGroup.getName().toString();

        if (personIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson toAdd = lastShownPersonList.get(personIndex.getZeroBased());
        String personName = toAdd.getName().toString();

        try {
            model.deletePersonFromGroup(groupIndex, toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, personName, groupName));
        } catch (GroupNotFoundException gnfe) {
            assert false : "The target group cannot be missing";
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        } catch (NoPersonsException dpe) {
            throw new CommandException(MESSAGE_EMPTY_GROUP);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, personName, groupName));
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemovePersonFromGroupCommand // instanceof handles nulls
                && groupIndex.equals(((RemovePersonFromGroupCommand) other).groupIndex)
                && personIndex.equals(((RemovePersonFromGroupCommand) other).personIndex));

    }
}
