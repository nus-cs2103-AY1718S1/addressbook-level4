package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
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
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ModelStub;

//@@author liliwei25
/**
 * Test BirthdayCommand
 */
public class BirthdayCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        BirthdayCommand birthdayCommand = prepareCommand(INDEX_FIRST_PERSON, new Birthday(VALID_BIRTHDAY_AMY));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person editedPerson = getEditedPerson(personToEdit);
        expectedModel.updatePerson(personToEdit, editedPerson);

        String expectedMessage = String.format(BirthdayCommand.MESSAGE_BIRTHDAY_PERSON_SUCCESS, editedPerson);

        assertCommandSuccess(birthdayCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Creates a new {@code Person} with a valid birthday
     */
    private Person getEditedPerson(ReadOnlyPerson personToEdit) throws IllegalValueException {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getRemark(), new Birthday(VALID_BIRTHDAY_AMY),
                    personToEdit.getTags(), personToEdit.getPicture(), personToEdit.getFavourite());
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        BirthdayCommand birthdayCommand = prepareCommand(outOfBoundIndex, BIRTHDAY_BOB);

        assertCommandFailure(birthdayCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_failure() throws Exception {
        BirthdayCommand birthdayCommand = prepareCommandForDuplicateException(INDEX_FIRST_PERSON, BIRTHDAY_ALICE);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_PERSON);

        birthdayCommand.execute();
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        BirthdayCommand birthdayCommand = prepareCommandForNotFoundException(INDEX_FIRST_PERSON, BIRTHDAY_ALICE);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        birthdayCommand.execute();
    }

    @Test
    public void equals() {
        final BirthdayCommand standardCommand = new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_AMY);

        // same values -> returns true
        BirthdayCommand commandWithSameValues = new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_SECOND_PERSON, BIRTHDAY_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new BirthdayCommand(INDEX_FIRST_PERSON, BIRTHDAY_BOB)));
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     */
    private BirthdayCommand prepareCommand(Index index, Birthday birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, birthday);
        birthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return birthdayCommand;
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     * to test {@code DuplicatePersonException}
     */
    private BirthdayCommand prepareCommandForDuplicateException(Index index, Birthday birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, birthday);
        birthdayCommand.setData(new ModelStubThrowingDuplicatePersonException(), new CommandHistory(),
                new UndoRedoStack());
        return birthdayCommand;
    }

    /**
     * Returns an {@code BirthdayCommand} with parameters {@code index} and {@code birthday}
     * to test {@code PersonNotFoundException}
     */
    private BirthdayCommand prepareCommandForNotFoundException(Index index, Birthday birthday) {
        BirthdayCommand birthdayCommand = new BirthdayCommand(index, birthday);
        birthdayCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return birthdayCommand;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to edit birthday.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always throw a {@code PersonNotFoundException} when trying to update person.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
