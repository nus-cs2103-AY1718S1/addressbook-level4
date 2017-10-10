package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_BOB;
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
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code NoteCommand}.
 */
public class NoteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addNote_success() throws Exception {
            ReadOnlyPerson personToNote = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            Person editedPerson = new PersonBuilder(personToNote).withNote(VALID_NOTE_AMY).build();
            NoteCommand noteCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNote());

            String expectedMessage = String.format(NoteCommand.MESSAGE_NOTE_SUCCESS, editedPerson);

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

            assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
        }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToNote = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToNote).withNote(VALID_NOTE_AMY).build();
        NoteCommand noteCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getNote());

        String expectedMessage = String.format(NoteCommand.MESSAGE_NOTE_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(personToNote, editedPerson);

        assertCommandSuccess(noteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        NoteCommand noteCommand = prepareCommand(outOfBoundIndex, new Note(VALID_NOTE_BOB));

        assertCommandFailure(noteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        NoteCommand noteFirstCommand = new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY));
        NoteCommand noteSecondCommand = new NoteCommand(INDEX_SECOND_PERSON, new Note(VALID_NOTE_AMY));

        // same object -> returns true
        assertTrue(noteFirstCommand.equals(noteFirstCommand));

        // same values -> returns true
        NoteCommand noteFirstCommandCopy = new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY));
        assertTrue(noteFirstCommand.equals(noteFirstCommandCopy));

        // different types -> returns false
        assertFalse(noteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(noteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(noteFirstCommand.equals(noteSecondCommand));
    }

    /**
     * Returns a {@code NoteCommand} with the parameter {@code index}.
     */
    private NoteCommand prepareCommand(Index index, Note note) {
        NoteCommand notecommand = new NoteCommand(index, note);
        notecommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return notecommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
