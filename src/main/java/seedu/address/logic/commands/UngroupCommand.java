package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.GroupNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Removes a person from a group
 */
public class UngroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "ungroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes from the group with name GROUP_NAME "
            + "the person identified by the index number used in the last person listing."
            + "If it was the last member of the group, the group is also removed.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_GROUP_NAME + "GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUP_NAME + "Family";

    public static final String MESSAGE_UNGROUP_SUCCESS = "Removed %1$s from a group: %2$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "%1$s does not belong to: %2$s";

    private final Index index;
    private final Group group;

    /**
     * @param index of the person in the filtered person list to remove from the group
     * @param group from which the person is to be removed
     */
    public UngroupCommand(Index index, Group group) {
        requireNonNull(index);
        requireNonNull(group);

        this.index = index;
        this.group = group;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        UniqueGroupList editedGroups = new UniqueGroupList(personToEdit.getGroups());

        Person editedPerson;
        try {
            editedGroups.remove(group);
            editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getAppointment(), personToEdit.getProfilePicture(),
                    editedGroups.toSet(), personToEdit.getTags());
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when adding to a group");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (GroupNotFoundException gnfe) {
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_FOUND, personToEdit.getName(), group.groupName));
        }
        model.updateGroups(group);
        model.updateFilteredPersonList(p ->true);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_UNGROUP_SUCCESS, personToEdit.getName(), group.groupName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UngroupCommand)) {
            return false;
        }

        // state check
        UngroupCommand e = (UngroupCommand) other;
        return index.equals(e.index)
                && group.equals(e.group);
    }
}
