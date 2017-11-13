//@@author conantteo
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.MULTIPLE_INDEX;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ExportRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ExportCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    //Model simply provides the required details of recipients and are not modified in any way.
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Index[] indexArray;

    @Test
    public void execute_exportRequestEvent_success() throws CommandException {
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON);
        CommandResult result = exportCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExportRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        ReadOnlyPerson personToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                personToExport.getName().toString());
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ExportCommand exportCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased());

        assertCommandFailure(exportCommand, model, expectedMessage);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws CommandException {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                personToExport.getName().toString());
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ExportCommand exportCommand = prepareCommand(outOfBoundIndex);
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + outOfBoundIndex.getOneBased();

        assertCommandFailure(exportCommand, model, expectedMessage);
    }

    @Test
    public void execute_multipleValidIndex_success() throws CommandException {
        ReadOnlyPerson firstPersonToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondPersonToExport = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        ExportCommand exportCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                firstPersonToExport.getName().toString() + ", " + secondPersonToExport.getName().toString());
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_all_success() throws CommandException {
        // Preparing command to export all contacts
        ExportCommand exportCommand = new ExportCommand();
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        // StringBuilder for building the names of each person
        StringBuilder allPerson = new StringBuilder();
        model.getFilteredPersonList().stream().forEach((
                person) -> allPerson.append(person.getName().toString() + ", "));
        // expectedMessage to remove the trailing ',' when building the message
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS,
                allPerson.toString().substring(0, allPerson.length() - 2));
        CommandResult result = exportCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
    }
    @Test
    public void equals() {
        ExportCommand exportAllCommand = new ExportCommand();
        ExportCommand exportSomeCommand = new ExportCommand(MULTIPLE_INDEX);

        // same object -> returns true
        assertTrue(exportAllCommand.equals(exportAllCommand));
        assertTrue(exportSomeCommand.equals(exportSomeCommand));

        // same values -> returns true
        ExportCommand exportSomeCommandCopy = new ExportCommand(MULTIPLE_INDEX);
        assertTrue(exportSomeCommand.equals(exportSomeCommandCopy));

        // different types -> returns false
        assertFalse(exportSomeCommand.equals(1));

        //null -> returns false
        assertFalse(exportSomeCommand.equals(null));

        // different commands -> returns false
        assertFalse(exportAllCommand.equals(exportSomeCommand));
    }

    /**
     * Returns a {@code ExportCommand} using one index parameter {@code index}
     */
    private ExportCommand prepareCommand(Index index) {
        indexArray = new Index[1];
        indexArray[0] = index;

        ExportCommand exportCommand = new ExportCommand(indexArray);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return exportCommand;
    }

    /**
     * Returns a {@code ExportCommand} using two index parameter {@code index1, index2}
     */
    private ExportCommand prepareCommand(Index index1, Index index2) {
        indexArray = new Index[2];
        indexArray[0] = index1;
        indexArray[1] = index2;

        ExportCommand exportCommand = new ExportCommand(indexArray);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return exportCommand;
    }
}
