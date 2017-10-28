package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds person with given index to a group. If such a group does not exist, creates it and then adds the person.
 */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds person to the group identified "
            + "by the group's name. Person to be added is specified by "
            + "index used in the last person listing. "
            + "Non existing group will be created before adding persons to it.\n"
            + "Parameters: " + "INDEX (must be a positive integer) "
            + PREFIX_GROUP_NAME + "GROUP_NAME.\n"
            + "Example: " + COMMAND_WORD + " 3 " + PREFIX_GROUP_NAME + "Family";

    public static final String MESSAGE_ADD_GROUP_SUCCESS = "Added %1$s to the group: %2$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "%1$s already belongs to: %2$s";

    private final Index index;
    private final Group group;

    /**
     * @param index of the person in the filtered person list to add to the group
     * @param group where person is to be added
     */
    public GroupCommand(Index index, Group group) {
        requireNonNull(index);
        requireNonNull(group);

        this.index = index;
        this.group = group;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        UniqueGroupList editedGroups = new UniqueGroupList(personToEdit.getGroups());

        Person editedPerson;
        try {
            editedGroups.add(group);
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getAppointment(), personToEdit.getProfilePicture(),
                    editedGroups.toSet(), personToEdit.getTags());
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when adding to a group");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (UniqueGroupList.DuplicateGroupException dge) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_GROUP, personToEdit.getName(), group.groupName));
        }
        model.updateFilteredPersonList(p ->true);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_ADD_GROUP_SUCCESS, personToEdit.getName(), group.groupName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCommand)) {
            return false;
        }

        // state check
        GroupCommand e = (GroupCommand) other;
        return index.equals(e.index)
                && group.equals(e.group);
    }
}
