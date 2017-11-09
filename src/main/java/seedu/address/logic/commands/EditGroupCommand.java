//@@author hthjthtrh
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Edits the group, either 1.change group name, 2.adds a person to the group or 3.deletes a person from the group
 */
public class EditGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edits the group. Supports three kinds of operations: 1. change group name 2. add a person 3. delete"
            + " a person\n"
            + "Parameters: GROUP_NAME gn NEW_GROUP_NAME (new group name cannot be an integer)\n"
            + "OR: GROUP_NAME add INDEX (must be positive integer)\n"
            + "OR: GROUP_NAME delete INDEX (must be positive integer)\n"
            + "Examples: " + COMMAND_WORD + " SmartOnes gn SuperSmartOnes\n"
            + COMMAND_WORD + " SmartOnes add 3\n"
            + COMMAND_WORD + " SmartOnes delete 4";

    public static final String MESSAGE_ADD_PERSON_SUCCESS = "Added person to group '%s':\n'%s'";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person from group '%s':\n'%s'";

    public static final String MESSAGE_CHANGE_NAME_SUCCESS = "Name of group '%s' is changed to '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the group";

    public static final String MESSAGE_DUPLICATE_GROUP =
            "A group by the name of '%s' already exists in the addressbook!";

    private String grpName = null;
    private Index grpIndex = null;
    private String operation = null;
    private String newName = null;
    private Index personIndex = null;
    private boolean indicateByIndex;

    public EditGroupCommand(String grpName, Index grpIndex, String operation, String newName, boolean indicateByIdx) {
        if (indicateByIdx) {
            this.grpIndex = grpIndex;
        } else {
            this.grpName = grpName;
        }
        this.operation = operation;
        this.newName = newName;
        this.indicateByIndex = indicateByIdx;
    }

    public EditGroupCommand(String grpName, Index grpIndex, String operation, Index personIndex,
                            boolean indicateByIdx) {
        if (indicateByIdx) {
            this.grpIndex = grpIndex;
        } else {
            this.grpName = grpName;
        }
        this.operation = operation;
        this.personIndex = personIndex;
        this.indicateByIndex = indicateByIdx;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<Group> grpList = model.getAddressBook().getGroupList();
        Group targetGrp = null;

        // locate target group
        if (indicateByIndex) {
            try {
                targetGrp = grpList.get(grpIndex.getZeroBased());
                grpName = targetGrp.getGrpName();
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
        } else {
            for (Group grp : grpList) {
                if (grp.getGrpName().equals(grpName)) {
                    targetGrp = grp;
                    grpIndex = Index.fromZeroBased(grpList.indexOf(grp));
                    break;
                }
            }
        }

        if (targetGrp == null) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
        }

        if ("gn".equals(operation)) {
            try {
                model.setGrpName(targetGrp, newName);
                return new CommandResult(String.format(MESSAGE_CHANGE_NAME_SUCCESS, grpName, newName));
            } catch (DuplicateGroupException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, String.format(MESSAGE_DUPLICATE_GROUP, newName));
            }
        } else {
            ReadOnlyPerson targetPerson = null;
            Person copiedPerson = null;
            if ("add".equals(operation)) {
                List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

                try {
                    targetPerson = lastShownList.get(personIndex.getZeroBased());
                    copiedPerson = new Person(targetPerson);
                    model.addPersonToGroup(targetGrp, copiedPerson);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_PERSON);
                }

                EventsCenter.getInstance().post(new JumpToListRequestEvent(this.grpIndex, true));

                return new CommandResult(String.format(MESSAGE_ADD_PERSON_SUCCESS, grpName,
                        copiedPerson.toString()));
            } else {
                List<ReadOnlyPerson> personListInGroup = targetGrp.getPersonList();

                try {
                    targetPerson = personListInGroup.get(personIndex.getZeroBased());
                    copiedPerson = new Person(targetPerson);
                    model.removePersonFromGroup(targetGrp, copiedPerson);
                } catch (IndexOutOfBoundsException e) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                } catch (PersonNotFoundException e) {
                    assert false : "The target person cannot be missing";
                }

                EventsCenter.getInstance().post(new JumpToListRequestEvent(this.grpIndex, true));

                return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, grpName,
                        copiedPerson.toString()));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // sorry for this extremely long thing but I have null values to take care off
        if (other instanceof EditGroupCommand) {
            EditGroupCommand temp = (EditGroupCommand) other;
            if (this.indicateByIndex == temp.indicateByIndex) {
                if (this.indicateByIndex) {
                    if (this.operation.equals(temp.operation)) {
                        if (this.operation.equals("gn")) {
                            return this.grpIndex.equals(temp.grpIndex)
                                    && this.newName.equals(temp.newName);
                        } else {
                            return this.grpIndex.equals(temp.grpIndex)
                                    && this.personIndex.equals(temp.personIndex);
                        }
                    }
                } else {
                    if (this.operation.equals(temp.operation)) {
                        if (this.operation.equals("gn")) {
                            return this.grpName.equals(temp.grpName)
                                    && this.newName.equals(temp.newName);
                        } else {
                            return this.grpName.equals(temp.grpName)
                                    && this.personIndex.equals(temp.personIndex);
                        }
                    }
                }
            }

        }
        return false;
    }
}
//@@author
