package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PORTRAIT_PATH_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PortraitPath;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * An integration and unit test for portrait command
 */
public class PortraitCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    @Test
    public void validTestSuccess() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PortraitPath path = new PortraitPath("");
        Person editedPerson = new Person(personToEdit);
        editedPerson.setPortraitPath(path);
        PortraitCommand portraitCommand = prepareCommand(INDEX_FIRST_PERSON, path);

        String expectedMessage = String.format(PortraitCommand.MESSAGE_DELETE_PORTRAIT_SUCCESS,
            personToEdit.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        expectedModel.updatePerson(personToEdit, editedPerson);

        assertCommandSuccess(portraitCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void invalidTestIndexFailure() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PortraitPath path = new PortraitPath("");
        Person editedPerson = new Person(personToEdit);
        editedPerson.setPortraitPath(path);
        Index largeIndex = Index.fromOneBased(10000000);
        PortraitCommand portraitCommand = prepareCommand(largeIndex, path);

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

        assertCommandFailure(portraitCommand, model, expectedMessage);
    }

    @Test
    public void invalidUrlFailure() throws Exception {
        ReadOnlyPerson personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PortraitPath path = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
        Person editedPerson = new Person(personToEdit);
        editedPerson.setPortraitPath(path);
        PortraitCommand portraitCommand = prepareCommand(INDEX_FIRST_PERSON, path);

        String expectedMessage = PortraitCommand.MESSAGE_INVALID_URL;

        assertCommandFailure(portraitCommand, model, expectedMessage);
    }

    @Test
    public void testEquals() {
        PortraitPath expectedPath = null;
        PortraitPath differentPath = null;
        try {
            expectedPath = new PortraitPath(VALID_PORTRAIT_PATH_FIRST);
            differentPath = new PortraitPath(VALID_PORTRAIT_PATH_SECOND);
        } catch (IllegalValueException e) {
            fail("Test data cannot throw exception");
        }
        requireNonNull(expectedPath);

        PortraitCommand command = new PortraitCommand(INDEX_FIRST_PERSON, expectedPath);

        assertTrue(command.equals(command));

        PortraitCommand commandCopy = new PortraitCommand(INDEX_FIRST_PERSON, expectedPath);
        assertTrue(command.equals(commandCopy));

        assertFalse(command.equals(new ClearCommand()));

        assertFalse(command.equals(null));

        PortraitCommand differentCommandOne = new PortraitCommand(INDEX_FIRST_PERSON, differentPath);
        PortraitCommand differentCommandTwo = new PortraitCommand(INDEX_SECOND_PERSON, expectedPath);
        assertFalse(command.equals(differentCommandOne));
        assertFalse(command.equals(differentCommandTwo));
    }

    /**
     * Generates a new PortraitCommand with the details of the given image path and index.
     */
    private PortraitCommand prepareCommand(Index index, PortraitPath path) {
        PortraitCommand command = new PortraitCommand(index, path);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


}
