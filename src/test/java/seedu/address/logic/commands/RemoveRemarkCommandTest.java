package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class RemoveRemarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_removeRemark_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("").build();

        RemoveRemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON,  1);

        String expectedMessage = String.format(RemoveRemarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {

        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("").build();
        RemoveRemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, 1);

        String expectedMessage = String.format(RemoveRemarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveRemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_INDEX_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemoveRemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_INDEX_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /**
     * Returns a {@code RemarkCommand} with the parameter {@code index} and {@code remark}
     */
    private RemoveRemarkCommand prepareCommand(Index index, Integer remarkIndex) {
        ArrayList<Integer> indexArrayList = new ArrayList<>();
        indexArrayList.add(remarkIndex);
        RemoveRemarkCommand remarkCommand = new RemoveRemarkCommand(index, indexArrayList);
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }

    @Test
    public void equals() {
        ArrayList<Integer> remarkIndexAmy = new ArrayList<>();
        remarkIndexAmy.add(VALID_INDEX_AMY);
        final RemoveRemarkCommand standardCommand = new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarkIndexAmy);

        // same values -> returns true
        RemoveRemarkCommand commandWithSameValues = new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarkIndexAmy);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemoveRemarkCommand(INDEX_SECOND_PERSON, remarkIndexAmy)));

        ArrayList<Integer> remarkIndexBob = new ArrayList<>();
        remarkIndexBob.add(VALID_INDEX_BOB);
        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarkIndexBob)));
    }
}
