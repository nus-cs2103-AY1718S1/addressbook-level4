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
public class EditGroupCommand extends GroupTypeUndoableCommand {

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
        Group targetGrp = locateTargetGrp(grpList);

        if ("gn".equals(operation)) {
            return handleNameChangeOp(targetGrp);
        } else {
            Person targetPerson = null;
            if ("add".equals(operation)) {
                List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
                targetPerson = locateTargetPerson(lastShownList);
                return handleAddOp(targetGrp, targetPerson);

            } else {
                List<ReadOnlyPerson> personListInGroup = targetGrp.getPersonList();
                targetPerson = locateTargetPerson(personListInGroup);
                return handleDeleteOp(targetGrp, targetPerson);
            }
        }
    }

    /**
     * deletes the target person from target group
     * @param targetGrp
     * @param targetPerson
     * @return
     * @throws CommandException
     */
    private CommandResult handleDeleteOp(Group targetGrp, Person targetPerson) {
        try {
            model.removePersonFromGroup(targetGrp, targetPerson);
        } catch (PersonNotFoundException e) {
            assert false : "The target person cannot be missing";
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(this.grpIndex, true));

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, grpName,
                targetPerson.toString()));
    }

    /**
     * locate and return the target person in the person list
     * @param personList
     * @return
     * @throws CommandException
     */
    private Person locateTargetPerson(List<ReadOnlyPerson> personList) throws CommandException {
        try {
            ReadOnlyPerson targetPerson = personList.get(personIndex.getZeroBased());
            Person copiedPerson = new Person(targetPerson);
            return copiedPerson;
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * adds the target person to the target group
     * @param targetGrp
     * @return
     */
    private CommandResult handleAddOp(Group targetGrp, Person targetPerson) throws CommandException {
        try {
            model.addPersonToGroup(targetGrp, targetPerson);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_PERSON);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(this.grpIndex, true));

        return new CommandResult(String.format(MESSAGE_ADD_PERSON_SUCCESS, grpName,
                targetPerson.toString()));
    }

    /**
     * updates the group name of target group
     * @param targetGrp
     * @return
     * @throws CommandException if a group by the new name already exists
     */
    private CommandResult handleNameChangeOp(Group targetGrp) throws CommandException {
        try {
            model.setGrpName(targetGrp, newName);
            return new CommandResult(String.format(MESSAGE_CHANGE_NAME_SUCCESS, grpName, newName));
        } catch (DuplicateGroupException e) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, String.format(MESSAGE_DUPLICATE_GROUP, newName));
        }
    }

    /**
     * locate and return the target group indicated by either index or group name
     * @param grpList
     * @return target group
     * @throws CommandException
     */
    private Group locateTargetGrp(List<Group> grpList) throws CommandException {
        Group targetGrp = null;
        if (indicateByIndex) {
            try {
                targetGrp = grpList.get(grpIndex.getZeroBased());
                undoGroupIndex = Index.fromZeroBased(grpIndex.getZeroBased());
                grpName = targetGrp.getGrpName();
                return targetGrp;
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
        } else {
            for (Group grp : grpList) {
                if (grp.getGrpName().equals(grpName)) {
                    targetGrp = grp;
                    grpIndex = Index.fromZeroBased(grpList.indexOf(grp));
                    undoGroupIndex = Index.fromZeroBased(grpList.indexOf(grp));
                    return targetGrp;
                }
            }
        }

        if (targetGrp == null) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
        }
        return null;
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

    /**
     * Reconstructs command message, used by RedoCommand
     * @return command message as a string
     */
    public String reconstructCommandString() {
        StringBuilder sb = new StringBuilder();
        sb.append(COMMAND_WORD + " ");

        if (indicateByIndex) {
            sb.append(grpIndex.getOneBased());
        } else {
            sb.append(grpName);
        }
        sb.append(" " + operation + " ");

        if ("gn".equals(operation)) {
            sb.append(newName);
        } else {
            sb.append(personIndex.getOneBased());
        }
        return sb.toString();
    }
}
//@@author
