package seedu.address.logic.commands;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Remark;

import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;


public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeUndoableCommand() throws Exception {
        final String testRemark = "Test Remark";

        assertCommandFailure(getRemarkCommandForPerson(INDEX_FIRST_PERSON, testRemark), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), testRemark));

    }

    @Test
    public void equals() {

        // Create RemarkCommand for testing
        // Amy used to test for empty remarks
        // Bob used to test for filled remarks
        RemarkCommand testCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        RemarkCommand testCommandTwo = new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_BOB));

        //Test for same object
        assertTrue(testCommand.equals(testCommand));
        assertTrue(testCommandTwo.equals(testCommandTwo));

        //Test for same values
        assertTrue(testCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY))));
        assertTrue(testCommandTwo.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_BOB))));

        //Test to ensure command is strictly a RemarkCommand
        assertFalse(testCommand.equals(new AddCommand(CARL)));
        assertFalse(testCommand.equals(new ClearCommand()));
        assertFalse(testCommand.equals(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(testCommand.equals(new HistoryCommand()));
        assertFalse(testCommand.equals(new HelpCommand()));
        assertFalse(testCommand.equals(new RedoCommand()));
        assertFalse(testCommand.equals(new UndoCommand()));
        assertFalse(testCommand.equals(new ListCommand()));
        assertFalse(testCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_AMY)));

        //Test to ensure command is not null
        assertFalse(testCommand.equals(null));
        assertFalse(testCommandTwo.equals(null));

        //Test to check different Index returns false
        assertFalse(testCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));
        assertFalse(testCommandTwo.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));

        //Test to check different remarks returns false
        assertFalse(testCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
        assertFalse(testCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

    }


    /**
     * Generates a new RemarkCommand with the remarks of the given person.
     *
     * @param index  of person in list
     * @param remark new remark to record
     */
    private RemarkCommand getRemarkCommandForPerson(Index index, String remark) {
        RemarkCommand command = new RemarkCommand(index, new Remark(remark));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
