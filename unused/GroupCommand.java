//@@author hxy0229
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

/** checkstyle comment */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Grouping several people together\n"
            + "Parameters: " + "GROUP_NAME " + "INDEX_1 " + "INDEX_2 " + "...\n"
            + "Example: " + COMMAND_WORD + " Stars " + "1 " + "3 ";

    public static final String MESSAGE_SUCCESS = "Success Grouping!";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group name already exists in the address book";
    public static final String MESSAGE_GROUP_CONSTRAINTS = "Groups names should be alphanumeric";

    private Group toAdd;

    private final List<Index> listTargetIndices;

    public GroupCommand(Group group, List<Index> listTargetIndices) throws IllegalValueException {
        toAdd = new Group(group);
        this.listTargetIndices = listTargetIndices;
    }

    /** checkstyle comment */
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addGroup(toAdd);

            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            for (Index targetIndex : listTargetIndices) {
                if (targetIndex.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
            }

            List<ReadOnlyPerson> listPersonsToGroup = new ArrayList<ReadOnlyPerson>();

            for (Index targetIndex : listTargetIndices) {
                ReadOnlyPerson personToGroup = lastShownList.get(targetIndex.getZeroBased());
                listPersonsToGroup.add(personToGroup);
            }

            requireNonNull(toAdd);
            for (ReadOnlyPerson personToGroup : listPersonsToGroup) {
                toAdd.addPerson(personToGroup);
                personToGroup.addGroup(toAdd);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateGroupException e) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_GROUP_CONSTRAINTS);
        }
    } //Constructor
}
//@@ author
