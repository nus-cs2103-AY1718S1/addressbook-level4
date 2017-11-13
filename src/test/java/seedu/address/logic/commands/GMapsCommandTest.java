package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nbriannl
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code GMapsCommand}.
 */
public class GMapsCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_SELECT_PERSON_SUCCESS,
                personToFind.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showMapOf(personToFind, INDEX_FIRST_PERSON);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        GMapsCommand gMapsCommand = prepareCommand(outOfBoundIndex, null);

        assertCommandFailure(gMapsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, null);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_SELECT_PERSON_SUCCESS,
                personToFind.getName());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showMapOf(personToFind, INDEX_FIRST_PERSON);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        GMapsCommand gMapsCommand = prepareCommand(outOfBoundIndex, null);

        assertCommandFailure(gMapsCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexValidAddressUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Address address = new Address("NUS");
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, address);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_DIRECTIONS_TO_PERSON_SUCCESS,
                personToFind.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showDirectionsTo(personToFind, address, INDEX_FIRST_PERSON);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexValidAddressFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToFind = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Address address = new Address("NUS");
        GMapsCommand gMapsCommand = prepareCommand(INDEX_FIRST_PERSON, address);

        String expectedMessage = String.format(GMapsCommand.MESSAGE_DIRECTIONS_TO_PERSON_SUCCESS,
                personToFind.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.showDirectionsTo(personToFind, address, INDEX_FIRST_PERSON);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(gMapsCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        Address address1 = new Address("NUS");
        Address address2 = new Address("NTU");

        GMapsCommand commandWithIndex = new GMapsCommand(INDEX_FIRST_PERSON, null);
        GMapsCommand commandWithDiffIndex = new GMapsCommand(INDEX_SECOND_PERSON, null);
        GMapsCommand commandWithIndexAndAddress = new GMapsCommand(INDEX_FIRST_PERSON, address1);
        GMapsCommand commandWithIndexAndDiffAddress = new GMapsCommand(INDEX_FIRST_PERSON, address2);
        GMapsCommand commandWithDiffIndexAndDiffAddress = new GMapsCommand(INDEX_SECOND_PERSON, address2);

        // same object -> returns true
        assertTrue(commandWithIndex.equals(commandWithIndex));

        // same values -> returns true
        GMapsCommand commandWithIndexCopy = new GMapsCommand(INDEX_FIRST_PERSON, null);
        assertTrue(commandWithIndex.equals(commandWithIndexCopy));

        // different types -> returns false
        assertFalse(commandWithIndex.equals(1));

        // null -> returns false
        assertFalse(commandWithIndex == null);

        // different index or address -> returns false
        assertFalse(commandWithIndex.equals(commandWithDiffIndex));
        assertFalse(commandWithIndex.equals(commandWithIndexAndAddress));
        assertFalse(commandWithIndex.equals(commandWithIndexAndDiffAddress));
        assertFalse(commandWithIndex.equals(commandWithDiffIndexAndDiffAddress));
    }

    /**
     * Returns a {@code GMapsCommand} with the parameter {@code index}, and {@code address}.
     */
    private GMapsCommand prepareCommand(Index index, Address address) {
        GMapsCommand gmapsCommand = new GMapsCommand(index , address);
        gmapsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return gmapsCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
