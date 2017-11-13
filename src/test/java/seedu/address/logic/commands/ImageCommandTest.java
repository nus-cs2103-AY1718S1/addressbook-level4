package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.ImageCommand.DEFAULT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ModelStub;

//@@author liliwei25
public class ImageCommandTest {
    private static final boolean REMOVE = true;
    private static final String TEST_IMAGE = "test.png";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ImageCommand imageCommand = prepareCommand(INDEX_FIRST_PERSON, !REMOVE);

        String expectedMessage = ImageCommand.MESSAGE_CANCELLED;

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.changeImage(person);

        assertCommandSuccess(imageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilterListRemoveImage_success() throws Exception {
        ReadOnlyPerson person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ImageCommand imageCommand = prepareCommand(INDEX_FIRST_PERSON, REMOVE);

        String expectedMessage = String.format(ImageCommand.MESSAGE_IMAGE_SUCCESS, person);
        person.setImage(TEST_IMAGE);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ReadOnlyPerson personToEdit = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = getDefaultPerson(personToEdit);
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(imageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ImageCommand imageCommand = prepareCommand(outOfBoundIndex, !REMOVE);

        assertCommandFailure(imageCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ImageCommand imageCommand = prepareCommand(outOfBoundIndex, REMOVE);

        assertCommandFailure(imageCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_failure() throws Exception {
        ReadOnlyPerson person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson newPerson = new Person(person);
        person.setImage(TEST_IMAGE);
        model.addPerson(newPerson);
        ImageCommand imageCommand = prepareCommand(INDEX_FIRST_PERSON, REMOVE);

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_PERSON);

        imageCommand.execute();
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        ImageCommand imageCommand = prepareCommandForNotFoundException(INDEX_FIRST_PERSON);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        imageCommand.execute();
    }

    @Test
    public void equals() {
        ImageCommand imageFirstCommand = new ImageCommand(INDEX_FIRST_PERSON, !REMOVE);
        ImageCommand imageSecondCommand = new ImageCommand(INDEX_SECOND_PERSON, !REMOVE);
        ImageCommand imageFirstRemoveCommand = new ImageCommand(INDEX_FIRST_PERSON, REMOVE);

        // same object -> returns true
        assertTrue(imageFirstCommand.equals(imageFirstCommand));

        // same values -> returns true
        ImageCommand imageFirstCommandCopy = new ImageCommand(INDEX_FIRST_PERSON, !REMOVE);
        assertTrue(imageFirstCommand.equals(imageFirstCommandCopy));

        // different types -> returns false
        assertFalse(imageFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(imageFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(imageFirstCommand.equals(imageSecondCommand));

        // different mode (remove/edit) -> returns false
        assertFalse(imageFirstCommand.equals(imageFirstRemoveCommand));
    }

    /**
     * Creates and returns new person with default picture
     */
    private Person getDefaultPerson(ReadOnlyPerson personToEdit) {
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getTags(), new ProfilePicture(DEFAULT), personToEdit.getFavourite());
    }

    /**
     * Returns a {@code ImageCommand} with the parameter {@code index} and {@code remove}.
     */
    private ImageCommand prepareCommand(Index index, boolean remove) {
        ImageCommand imageCommand = new ImageCommand(index, remove);
        imageCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return imageCommand;
    }

    /**
     * Returns an {@code ImageCommand} with parameters {@code index} and {@code remove}
     * to test {@code PersonNotFoundException}
     */
    private ImageCommand prepareCommandForNotFoundException(Index index) {
        ImageCommand imageCommand = new ImageCommand(index, !REMOVE);
        imageCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return imageCommand;
    }

    /**
     * A Model stub that always throw a {@code PersonNotFoundException} when trying to change image.
     */
    private class ModelStubThrowingPersonNotFoundException extends ModelStub {
        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }

        @Override
        public void changeImage(ReadOnlyPerson target) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
