//@@author AceCentury

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.DeleteByNameCommand.MESSAGE_SUGGESTED_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.CaseInsensitiveExactNamePredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByNameCommand}.
 */

public class DeleteByNameCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = ALICE;
        DeleteByNameCommand deleteByNameCommand = prepareCommand(ALICE.getName());

        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() throws Exception {
        Name name = new Name("Fake name");
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
    }

    @Test
    public void execute_validNameFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = ALICE;
        DeleteByNameCommand deleteByNameCommand = prepareCommand(ALICE.getName());

        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_partialNameUnfilteredList_throwsCommandException() throws IllegalValueException {
        String bensonFirstName = Arrays.asList(BENSON.getName().toString().split(" ")).get(0).toLowerCase();
        List<String> predicateList = Arrays.asList(bensonFirstName);
        Name name = new Name(bensonFirstName);

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(predicateList));
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, MESSAGE_SUGGESTED_PERSONS);
    }

    @Test
    public void execute_partialNameFilteredList_throwsCommandException() throws IllegalValueException {
        showFirstPersonOnly(model);

        String carlFirstName = Arrays.asList(CARL.getName().toString().split(" ")).get(0).toLowerCase();
        List<String> predicateList = Arrays.asList(carlFirstName);
        Name name = new Name(carlFirstName);

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(predicateList));
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, MESSAGE_SUGGESTED_PERSONS);
    }

    /**
     * Tests the deletion of a person not shown in filtered list.
     */

    @Test
    public void execute_validNameNotInFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToDelete = CARL;
        DeleteByNameCommand deleteByNameCommand = prepareCommand(CARL.getName());

        String expectedMessage = String.format(DeleteByNameCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showFirstPersonOnly(expectedModel);

        assertCommandSuccess(deleteByNameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameFilteredList_throwsCommandException() throws IllegalValueException {
        showFirstPersonOnly(model);

        Name name = new Name("Fake name");
        DeleteByNameCommand deleteByNameCommand = prepareCommand(name);

        assertCommandFailure(deleteByNameCommand, model, Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
    }

    @Test
    public void execute_multiplePersonsWithSameNameFilteredList_throwsCommandException()
            throws IllegalValueException {

        showFirstPersonOnly(model);
        Person alice2 = new Person(ALICE);
        alice2.setPhone(new Phone("12345678"));
        model.addPerson(alice2);
        model.updateFilteredPersonList(new CaseInsensitiveExactNamePredicate(alice2.getName()));

        DeleteByNameCommand deleteByNameCommand = prepareCommand(alice2.getName());

        assertCommandFailure(deleteByNameCommand, model, deleteByNameCommand.MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME);
    }

    @Test
    public void execute_multiplePersonsWithSameNameUnfilteredList_throwsCommandException()
            throws IllegalValueException {

        Person alice2 = new Person(ALICE);
        alice2.setPhone(new Phone("12345678"));
        model.addPerson(alice2);
        model.updateFilteredPersonList(new CaseInsensitiveExactNamePredicate(alice2.getName()));

        DeleteByNameCommand deleteByNameCommand = prepareCommand(alice2.getName());

        assertCommandFailure(deleteByNameCommand, model, deleteByNameCommand.MESSAGE_MULTIPLE_PERSON_WITH_SAME_NAME);
    }

    @Test
    public void equals() {
        DeleteByNameCommand deleteFirstCommand = new DeleteByNameCommand(ALICE.getName());
        DeleteByNameCommand deleteSecondCommand = new DeleteByNameCommand(BENSON.getName());

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByNameCommand deleteFirstCommandCopy = new DeleteByNameCommand(ALICE.getName());
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteByNameCommand} with the parameter {@code index}.
     */
    private DeleteByNameCommand prepareCommand(Name name) {
        DeleteByNameCommand deleteByNameCommand = new DeleteByNameCommand(name);
        deleteByNameCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteByNameCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
