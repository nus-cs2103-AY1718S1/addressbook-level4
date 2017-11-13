package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author Pengyuz
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private static final ReadOnlyPerson DUPLICATE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("124, Jurong West Ave 7, #08-112").withEmail("alicee@example.com")
            .withPhone("85333333")
            .withTags("workmate").build();
    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
    private ArrayList<Index> indexToDelete = new ArrayList<>();

    @Test
    public void excute_duplicatePerson_sucess() throws Exception {

        String duplicatePerosnName = "Alice Pauline";
        model.addPerson(DUPLICATE);

        List<String> duplicatePerson = Arrays.asList(duplicatePerosnName);
        NameContainsKeywordsPredicate updatedpredicate = new NameContainsKeywordsPredicate(duplicatePerson);

        DeleteCommand deleteCommand = prepareCommand(duplicatePerosnName);

        String expectedMessage = "Duplicate persons exist, please choose one to delete.";

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel.updateFilteredPersonList(updatedpredicate);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);

        model.deletePerson(DUPLICATE);

    }


    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        indexToDelete.add(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deleteList = new ArrayList<>();
        deleteList.add(personToDelete);

        DeleteCommand deleteCommand1 = prepareCommand(indexToDelete);

        String expectedMessage = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel.deletePerson(deleteList);

        assertCommandSuccess(deleteCommand1, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_success2() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        indexToDelete.clear();
        indexToDelete.add(INDEX_FIRST_PERSON);
        indexToDelete.add(INDEX_SECOND_PERSON);

        DeleteCommand deleteCommand1 = prepareCommand(indexToDelete);

        String expectedMessage = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        ArrayList<ReadOnlyPerson> deleteList = new ArrayList<>();
        deleteList.add(personToDelete);
        deleteList.add(secondToDelete);

        expectedModel.deletePerson(deleteList);

        assertCommandSuccess(deleteCommand1, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        indexToDelete.clear();

        ArrayList<ReadOnlyPerson> deleteList = new ArrayList<>();
        deleteList.add(personToDelete);

        String deleteName = personToDelete.getName().fullName;

        DeleteCommand deleteCommand = prepareCommand(deleteName);

        String expectedMessage = DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel.deletePerson(deleteList);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        indexToDelete.clear();
        indexToDelete.add(outOfBoundIndex);
        DeleteCommand deleteCommand = prepareCommand(indexToDelete);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        indexToDelete.clear();

        indexToDelete.add(INDEX_FIRST_PERSON);

        ArrayList<ReadOnlyPerson> deleteList = new ArrayList<>();

        deleteList.add(personToDelete);

        DeleteCommand deleteCommand = prepareCommand(indexToDelete);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());

        expectedModel.deletePerson(deleteList);

        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        indexToDelete.clear();

        ArrayList<ReadOnlyPerson> deleteList = new ArrayList<>();

        deleteList.add(personToDelete);

        String deleteName = personToDelete.getName().fullName;

        DeleteCommand deleteCommand = prepareCommand(deleteName);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new AddressBook(), new UserPrefs());
        expectedModel.deletePerson(deleteList);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexToDelete.clear();
        indexToDelete.add(INDEX_SECOND_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(indexToDelete);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() {
        model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        indexToDelete.clear();
        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        String name = personToDelete.getName().fullName;

        showFirstPersonOnly(model);


        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(name);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void equals() {
        ArrayList<Index> first = new ArrayList<>();
        ArrayList<Index> second = new ArrayList<>();
        first.add(INDEX_FIRST_PERSON);
        second.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(first);
        DeleteCommand deleteSecondCommand = new DeleteCommand(second);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(first);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(ArrayList<Index> indexes) {

        DeleteCommand deleteCommand = new DeleteCommand(indexes);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    private DeleteCommand prepareCommand(String name) {
        DeleteCommand deleteCommand = new DeleteCommand(name);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }


    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

}
