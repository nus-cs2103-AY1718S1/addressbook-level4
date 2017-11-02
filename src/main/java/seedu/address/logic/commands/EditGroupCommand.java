//@@author hthjthtrh
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.GroupContainsPersonPredicate;

/**
 * Edits the group, either 1.change group name, 2.adds a person to the group or 3.deletes a person from the group
 */
public class EditGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edits the group. Supports three kinds of operations: 1. change group name 2. add a person 3. delete"
            + " a person\n"
            + "Parameters: GROUP_NAME grpName NEW_GROUP_NAME\n"
            + "OR: GROUP_NAME add INDEX\n"
            + "OR: GROUP_NAME delete INDEX\n"
            + "Examples: " + COMMAND_WORD + " SmartOnes grpName SuperSmartOnes\n"
            + COMMAND_WORD + " SmartOnes add 3\n"
            + COMMAND_WORD + " SmartOnes delete 4";

    public static final String MESSAGE_ADD_PERSON_SUCCESS = "Added person to group '%s':\n'%s'";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person from group '%s':\n'%s'";

    public static final String MESSAGE_CHANGE_NAME_SUCCESS = "Name of group '%s' is changed to '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the group";

    public static final String MESSAGE_DUPLICATE_GROUP =
            "A group by the name of '%s' already exists in the addressbook!";

    private String grpName;
    private String operation;
    private String detail;
    private Predicate predicate;

    public EditGroupCommand(String grpName, String operation, String detail) {
        this.grpName = grpName;
        this.operation = operation;
        this.detail = detail;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<Group> grpList = model.getAddressBook().getGroupList();

        // find target group
        Group targetGrp = null;
        for (int i = 0; i < grpList.size(); i++) {
            if (grpList.get(i).getGrpName().equals(grpName)) {
                targetGrp = grpList.get(i);
                break;
            }
        }

        if (targetGrp == null) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
        }

        // use 'detail' differently according to operation type
        Index idx = null;
        if (operation.equals("add") || operation.equals("delete")) {
            try {
                idx = ParserUtil.parseIndex(detail);
            } catch (IllegalValueException e) {
                throw new AssertionError("detail should be an integer at this stage");
            }
        }

        // operation is change grpName
        if (idx == null) {
            try {
                model.setGrpName(targetGrp, detail);
            } catch (DuplicateGroupException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, String.format(MESSAGE_DUPLICATE_GROUP,
                        detail));
            }
            return new CommandResult(String.format(MESSAGE_CHANGE_NAME_SUCCESS, grpName, detail));
        } else {
            ReadOnlyPerson targetPerson;
            Person copiedPerson;
            if (operation.equals("add")) { //add operation

                List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
                if (idx.getZeroBased() >= lastShownList.size() || idx.getOneBased() <= 0) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                targetPerson = lastShownList.get(idx.getZeroBased());
                copiedPerson = new Person(targetPerson);

                try {
                    model.addPersonToGroup(targetGrp, copiedPerson);
                    predicate = new GroupContainsPersonPredicate(targetGrp);
                    model.updateFilteredPersonList(predicate);
                    return new CommandResult(String.format(MESSAGE_ADD_PERSON_SUCCESS, grpName,
                            copiedPerson.toString()));
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_DUPLICATE_PERSON);
                }
            } else { //delete operation

                List<ReadOnlyPerson> grpPersonList = targetGrp.getPersonList();
                if (idx.getZeroBased() >= grpPersonList.size()) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE,
                            Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                targetPerson = grpPersonList.get(idx.getZeroBased());
                copiedPerson = new Person(targetPerson);

                try {
                    model.removePersonFromGroup(targetGrp, copiedPerson);
                    predicate = new GroupContainsPersonPredicate(targetGrp);
                    model.updateFilteredPersonList(predicate);
                    return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, grpName,
                            copiedPerson.toString()));
                } catch (PersonNotFoundException e) {
                    assert false : "The target person cannot be missing";
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other instanceof EditGroupCommand) {
            EditGroupCommand temp = (EditGroupCommand) other;
            return (this.grpName.equals(temp.grpName) && this.operation.equals(temp.operation)
                && this.detail.equals(temp.detail));
        }

        return false;
    }
}
//@@author
