package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeValidRemarkAddSuccess() throws Exception {

        Person personToRemark = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withRemark("Remark To Add").build();

        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON, personToRemark.getRemark().value);

        String expectedMessage = String.format(remarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, personToRemark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidRemarkDeleteSuccess() throws Exception {

        Person personToRemark = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withRemark("").build();

        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON, personToRemark.getRemark().value);

        String expectedMessage = String.format(remarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, personToRemark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexFilteredListEditRemarksSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToRemark).withRemark("Test").build();
        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON,
                editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, personToRemark);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexFilteredListDeleteRemarksSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToRemark).withRemark("").build();
        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON,
                editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, personToRemark);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = getRemarkCommandForPerson(outOfBoundIndex, VALID_REMARK_AMY);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = getRemarkCommandForPerson(outOfBoundIndex, VALID_REMARK_AMY);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        //Test to check for null
        assertFalse(testCommand == null);
        assertFalse(testCommandTwo == null);

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
