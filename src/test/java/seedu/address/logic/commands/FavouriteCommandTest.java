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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FavouriteCommand.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test 
    public void execute_favourite_success() throws Exception {
        Person favouritedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()))
                .withFavouriteStatus(true).build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_SECOND_PERSON);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITED_PERSON, favouritedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), favouritedPerson);
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfavourite_success() throws Exception {
        Person favouritedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        favouritedPerson.setFavouriteStatus(new FavouriteStatus(true));
        Person unfavouritedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        unfavouritedPerson.setFavouriteStatus(new FavouriteStatus(false));
        model.updatePerson(model.getFilteredPersonList().get(0), favouritedPerson);
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_UNFAVOURITED_PERSON, unfavouritedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), unfavouritedPerson);
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test 
    public void execute_filteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person favouritedPerson = new PersonBuilder(personInFilteredList)
                .withFavouriteStatus(true).build();
        FavouriteCommand favouriteCommand = prepareCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITED_PERSON, favouritedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), favouritedPerson);
        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        FavouriteCommand favouriteCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final FavouriteCommand standardCommand = new FavouriteCommand(INDEX_FIRST_PERSON);
        // same values -> returns true
        FavouriteCommand commandWithSameValues = new FavouriteCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_SECOND_PERSON)));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private FavouriteCommand prepareCommand(Index index) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }
}
