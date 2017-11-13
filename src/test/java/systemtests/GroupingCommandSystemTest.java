//@@ author hthjthtrh
package systemtests;

import static seedu.address.logic.commands.GroupingCommand.MESSAGE_GROUPING_PERSON_SUCCESS;
import static seedu.address.logic.commands.UndoableCommand.appendPersonList;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.GroupingCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.predicates.GroupContainsPersonPredicate;
import seedu.address.testutil.TypicalPersons;

public class GroupingCommandSystemTest extends AddressBookSystemTest {

    // this message is used to match message in result display, which should be empty for any failed execution
    private static final String MESSAGE_EXECUTION_FAILURE_EMPTY = "";


    @Test
    public void group() throws Exception {
        Model model = getModel();

        /* Case: create a new group and add to a non-empty address book, command with leading spaces
         * -> added
         */
        Group toCreate = new Group("TEST_GROUP");
        toCreate.add(TypicalPersons.getTypicalPersons().get(0));
        toCreate.add(TypicalPersons.getTypicalPersons().get(1));
        String cmd = "  " + GroupingCommand.COMMAND_WORD + "  TEST_GROUP    1     2  ";
        assertCommandSuccess(cmd, toCreate);

        /*
        Case: undo create group -> group "TEST_GROUP" deleted
         */
        cmd = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(cmd, model, expectedResultMessage);

        /*
        Case: redo prev command -> group "TEST_GROUP" added again
         */
        cmd = RedoCommand.COMMAND_WORD;
        Model expectedModel = prepModel(toCreate, true);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(cmd, expectedModel, expectedResultMessage);

        /*
        Case: create a duplicate group -> rejected
         */
        cmd = GroupingCommand.COMMAND_WORD + " TEST_GROUP 1 2";
        assertCommandFailure(cmd, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /*
        Case: create a group of existing name but different person -> rejected
         */
        cmd = GroupingCommand.COMMAND_WORD + " TEST_GROUP   3 4";
        assertCommandFailure(cmd, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /*
        Case: create a group of invalid name -> rejected
         */
        cmd = GroupingCommand.COMMAND_WORD + " 123 1 2";
        assertCommandFailure(cmd, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /*
        Case: create a group of valid name but invalid person indexes -> rejected
         */
        cmd = GroupingCommand.COMMAND_WORD + " TEST_GROUP 10 11 12";
        assertCommandFailure(cmd, MESSAGE_EXECUTION_FAILURE_EMPTY);

        /*
        Case: add group to an empty addressbook -> rejected
         */
        cmd = GroupingCommand.COMMAND_WORD + " TEST_GROUP 1 2";
        executeCommand(ClearCommand.COMMAND_WORD, false);
        assert getModel().getAddressBook().getPersonList().size() == 0
                && getModel().getAddressBook().getGroupList().size() == 0;
        assertCommandFailure(cmd, MESSAGE_EXECUTION_FAILURE_EMPTY);

        // undo clear command
        executeCommand(UndoCommand.COMMAND_WORD, false);
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String cmd, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(cmd, true);
        assertApplicationDisplaysExpected(cmd, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * execute the GroupingCommand and verify outputs
     * @param cmd
     * @param grp
     */
    private void assertCommandSuccess(String cmd, Group grp) {
        Model expectedModel = prepModel(grp, true);

        String expectedResultMessage = getSb(grp.getGrpName(), grp.getPersonList());

        assertCommandSuccess(cmd, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Group)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see GroupingCommandSystemTest#assertCommandSuccess(String, Group)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command, false);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * adds the group to the model
     * @param grp
     * @param needUpdate
     * @return the modified model
     */
    private Model prepModel(Group grp, Boolean needUpdate) {
        Model expectedModel = getModel();
        try {
            expectedModel.createGroup(grp.getGrpName(), grp.getPersonList());
            if (needUpdate) {
                expectedModel.updateFilteredGroupList(new Predicate<Group>() {
                    @Override
                    public boolean test(Group group) {
                        return true;
                    }
                });
                expectedModel.updateFilteredPersonList(new GroupContainsPersonPredicate(grp));
            }
        } catch (DuplicateGroupException e) {
            throw new IllegalArgumentException("Group already exists in the model.");
        }
        return expectedModel;
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
