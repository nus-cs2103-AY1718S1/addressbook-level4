package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;

//@@author seankwekjk
public class RemarkCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void remark() throws Exception {
        /* Case: follows expected syntax -> remark edited*/
        Index index = INDEX_FIRST_PERSON;
        String command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " " + REMARK_DESC_AMY;
        Remark remark = new Remark(VALID_REMARK_AMY);
        assertCommandSuccess(command, index, remark, RemarkCommand.REMARK_EDIT_SUCCESS);

        /* Case: clears remark -> remark edited*/
        command = RemarkCommand.COMMAND_WORD + " " + index.getOneBased() + " r/";
        Remark emptyRemark = new Remark("");
        assertCommandSuccess(command, index, emptyRemark, RemarkCommand.REMARK_CLEAR_SUCCESS);

        /* Case: filtered person list, edit index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = RemarkCommand.COMMAND_WORD + " " + invalidIndex + " " + REMARK_DESC_AMY;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid index (0) -> rejected */
        command = RemarkCommand.COMMAND_WORD + " 0" + REMARK_DESC_AMY;
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes @code command
     * 1. Asserts that @code remark is equal to the updated remark @code toCheck
     * 2. Asserts that @code expectedMessage is the expected type of message result
     */
    private void assertCommandSuccess(String command, Index index, Remark remark, String expectedMessage) {
        executeCommand(command);
        ReadOnlyPerson personToCheck = getModel().getFilteredPersonList().get(index.getZeroBased());
        Remark toCheck = personToCheck.getRemark();
        assertTrue(remark.toString().equals(toCheck.toString()));
        assertEquals(expectedMessage, getResultDisplay().getText());
    }

    /**
     * Executes @code command
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
    }
}
