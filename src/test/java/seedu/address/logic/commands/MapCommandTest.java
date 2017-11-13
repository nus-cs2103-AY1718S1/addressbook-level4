package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ModelStub;

//@@author liliwei25
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code MapCommand}.
 */
public class MapCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToMap = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MapCommand mapCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(MapCommand.MESSAGE_MAP_SHOWN_SUCCESS, personToMap);
        assertEquals(mapCommand.execute().feedbackToUser, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MapCommand mapCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(mapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MapCommand mapCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(mapCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_missingPerson_failure() throws Exception {
        MapCommand mapCommand = prepareCommandForNotFoundException(INDEX_FIRST_PERSON);

        thrown.expect(AssertionError.class);
        thrown.expectMessage(MESSAGE_MISSING_PERSON);

        mapCommand.execute();
    }

    @Test
    public void equals() {
        MapCommand mapFirstCommand = new MapCommand(INDEX_FIRST_PERSON);
        MapCommand mapSecondCommand = new MapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(mapFirstCommand.equals(mapFirstCommand));

        // same values -> returns true
        MapCommand mapFirstCommandCopy = new MapCommand(INDEX_FIRST_PERSON);
        assertTrue(mapFirstCommand.equals(mapFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapFirstCommand.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(mapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapFirstCommand.equals(mapSecondCommand));
    }

    /**
     * Returns a {@code MapCommand} with the parameter {@code index}.
     */
    private MapCommand prepareCommand(Index index) {
        MapCommand mapCommand = new MapCommand(index);
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapCommand;
    }

    /**
     * Returns an {@code MapCommand} with parameters {@code index}
     * to test {@code PersonNotFoundException}
     */
    private MapCommand prepareCommandForNotFoundException(Index index) {
        MapCommand mapCommand = new MapCommand(index);
        mapCommand.setData(new ModelStubThrowingPersonNotFoundException(), new CommandHistory(),
                new UndoRedoStack());
        return mapCommand;
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
        public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {
            throw new PersonNotFoundException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
