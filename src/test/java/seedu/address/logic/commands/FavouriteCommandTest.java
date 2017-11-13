package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.person.Favourite.COLOR_OFF;
import static seedu.address.model.person.Favourite.COLOR_SWITCH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ModelStub;

//@@author justintkj
/**
 * Test FavouriteCommand
 */
public class FavouriteCommandTest {

    public static final int NUMBER_ONE = 1;
    public static final String DUPLICATE_EXCEPTION = "duplicate";
    public static final String MISSING_EXCEPTION = "Missing";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        FavouriteCommand favouriteCommand = prepareCommand(model, INDEX_FIRST_PERSON, new Favourite(COLOR_SWITCH));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person editedPerson = getEditedPerson(personToEdit);
        expectedModel.updatePerson(personToEdit, editedPerson);

        String expectedMessage = String.format(FavouriteCommand.MESSAGE_FAVOURITE_SUCCESS, editedPerson);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Creates a new {@code Person} with a valid favourite
     *
     * @param personToEdit The Person to edit
     * @return The edited person
     * @throws IllegalValueException Invalid value when generating the Person
     */
    private Person getEditedPerson(ReadOnlyPerson personToEdit) throws IllegalValueException {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getTags(), personToEdit.getPicture(), new Favourite(COLOR_SWITCH));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws IllegalValueException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + NUMBER_ONE);
        FavouriteCommand favouriteCommand = prepareCommand(model, outOfBoundIndex, new Favourite(COLOR_SWITCH));

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_failure() throws Exception {
        execute_personError_failure(DUPLICATE_EXCEPTION, MESSAGE_DUPLICATE_PERSON, CommandException.class);
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        execute_personError_failure(MISSING_EXCEPTION, MESSAGE_MISSING_PERSON, AssertionError.class);
    }

    /**
     * Generates a person error when invalid person type occurs
     *
     * @param exceptionType Duplicate or Missing person error
     * @param exceptionMessage Exception message for errors
     * @param exceptionClass Exception class
     * @throws IllegalValueException Duplicate person error
     * @throws CommandException Missing person error
     */
    private void execute_personError_failure(String exceptionType, String exceptionMessage, Class exceptionClass)
            throws IllegalValueException, CommandException {
        FavouriteCommand favouriteCommand = prepareCommand (
                new ModelStubThrowingPersonException(exceptionType), INDEX_FIRST_PERSON,
                new Favourite(COLOR_SWITCH));

        thrown.expect(exceptionClass);
        thrown.expectMessage(exceptionMessage);

        favouriteCommand.execute();
    }

    @Test
    public void equals() throws IllegalValueException {
        final FavouriteCommand standardCommand = new FavouriteCommand(INDEX_FIRST_PERSON, new Favourite (COLOR_SWITCH));

        // same values -> returns true
        FavouriteCommand commandWithSameValues = new FavouriteCommand(INDEX_FIRST_PERSON, new Favourite (COLOR_SWITCH));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_SECOND_PERSON, new Favourite (COLOR_SWITCH))));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new FavouriteCommand(INDEX_FIRST_PERSON, new Favourite (COLOR_OFF))));
    }

    /**
     * Returns an {@code FavouriteCommand} with parameters {@code index} and {@code favourite}
     *
     * @param model model to run the tet on
     * @param index index of the person to tet
     * @param favourite favourite state of the person
     * @return a new FavouriteCommand
     */
    private FavouriteCommand prepareCommand(Model model, Index index, Favourite favourite) {
        FavouriteCommand favouriteCommand = new FavouriteCommand(index, favourite);
        favouriteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return favouriteCommand;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException or PersonNotFoundException
     * when trying to edit favourite.
     */
    private class ModelStubThrowingPersonException extends ModelStub {
        private static final String EXCEPTION_DUPLICATE = "duplicate";
        private String exceptiontype;

        public ModelStubThrowingPersonException (String exceptionType) {
            this.exceptiontype = exceptionType;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException, PersonNotFoundException {
            if (exceptiontype.trim().toLowerCase().equals(EXCEPTION_DUPLICATE)) {
                throw new DuplicatePersonException();
            } else {
                throw new PersonNotFoundException();
            }
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
