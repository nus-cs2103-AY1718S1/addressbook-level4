package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

//@@author nahtanojmil
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("a lot of remarks").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(remarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemark_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(remarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand , model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        ReadOnlyPerson filteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(filteredList).withRemark("a lot of remarks").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark());

        String expectedMessage = String.format(remarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBounds = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBounds, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBounds = INDEX_SECOND_PERSON;
        //shows that outOfBounds is still within bounds of address book list
        assertTrue(outOfBounds.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = prepareCommand(outOfBounds, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand stdCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> returns true
        RemarkCommand toCompareCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(stdCommand.equals(toCompareCommand));

        //same object -> returns true
        assertTrue(stdCommand.equals(stdCommand));

        //null -> return false
        assertFalse(stdCommand.equals(null));

        //different types -> return false
        assertFalse(stdCommand.equals(new HelpCommand()));

        //different index -> return false
        assertFalse(stdCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        //different remarks -> return false
        assertFalse(stdCommand.equals((new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB)))));
    }

    /**
     *
     * @return an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private RemarkCommand prepareCommand(Index index, Remark string) {
        RemarkCommand remarkCommand = new RemarkCommand(index, string);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
