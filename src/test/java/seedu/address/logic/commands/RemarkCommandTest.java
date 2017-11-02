//@@author giang
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private String remarkString = "";

    @Test
    public void testExecuteUndoableCommand() throws Exception {
    }

    @Test
    public void testEqual() throws Exception {
        RemarkCommand remark1 = new RemarkCommand(Index.fromOneBased(1), new Remark("haha"));
        RemarkCommand remark2 = new RemarkCommand(Index.fromOneBased(1), new Remark("haha"));
        assertTrue(assertRemarkCommandEqual(remark1, remark2));
    }



    @Test
    public void execute_unfilteredList_success() {
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, new Remark(remarkString));
        ReadOnlyPerson remarkedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, remarkedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }


    /**
     * Check if two RemarkCommand objects are equal
     */
    private boolean assertRemarkCommandEqual(RemarkCommand remark1, RemarkCommand remark2) {
        if (remark1.equals(remark2)) {
            return true;
        }
        return false;
    }

    /**
     * Remark filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex,
                new Remark("Nice"));

        assertCommandFailure(remarkCommand, model,
                MESSAGE_EXECUTION_FAILURE + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private RemarkCommand prepareCommand(Index index, Remark remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, remark);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
