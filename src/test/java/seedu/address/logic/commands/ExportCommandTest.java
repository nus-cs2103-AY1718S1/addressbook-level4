package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class ExportCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
    }

    @Test
    public void executeValidIndexUnfilteredListSuccess() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        ReadOnlyPerson firstPersonToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson lastPersonToExport;
        lastPersonToExport = model.getFilteredPersonList().get(lastPersonIndex.getZeroBased());

        final StringBuilder firstBuilder = new StringBuilder();
        firstPersonToExport.getTags().forEach(firstBuilder::append);
        String firstMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + firstPersonToExport.getName(),
                        "p/" + firstPersonToExport.getPhone(),
                        "e/" + firstPersonToExport.getEmail(),
                        "a/" + firstPersonToExport.getAddress(),
                        "r/" + firstPersonToExport.getRemark(),
                        "t/" + firstBuilder));
        final StringBuilder lastBuilder = new StringBuilder();
        lastPersonToExport.getTags().forEach(lastBuilder::append);
        String lastMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + lastPersonToExport.getName(),
                        "p/" + lastPersonToExport.getPhone(),
                        "e/" + lastPersonToExport.getEmail(),
                        "a/" + lastPersonToExport.getAddress(),
                        "r/" + lastPersonToExport.getRemark(),
                        "t/" + lastBuilder));

        assertExecutionSuccess(INDEX_FIRST_PERSON, firstMessage);
        //assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex, lastMessage);
    }

    @Test
    public void executeInvalidIndexUnfilteredListFailure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeValidIndexFilteredListSuccess() {
        showFirstPersonOnly(model);
        ReadOnlyPerson personToExport = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        personToExport.getTags().forEach(builder::append);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + personToExport.getName(),
                        "p/" + personToExport.getPhone(),
                        "e/" + personToExport.getEmail(),
                        "a/" + personToExport.getAddress(),
                        "r/" + personToExport.getRemark(),
                        "t/" + builder));

        assertExecutionSuccess(INDEX_FIRST_PERSON, expectedMessage);
    }

    @Test
    public void executeInvalidIndexFilteredListFailure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ExportCommand exportFirstCommand = new ExportCommand(INDEX_FIRST_PERSON);
        ExportCommand exportSecondCommand = new ExportCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(exportFirstCommand.equals(exportFirstCommand));

        // same values -> returns true
        ExportCommand exportFirstCommandCopy = new ExportCommand(INDEX_FIRST_PERSON);
        assertTrue(exportFirstCommand.equals(exportFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(exportFirstCommand.equals(exportSecondCommand));
    }

    /**
     * Executes a {@code ExportCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        ExportCommand exportCommand = prepareCommand(index);

        try {
            CommandResult commandResult = exportCommand.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ExportCommand exportCommand = prepareCommand(index);

        try {
            exportCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ExportCommand} with parameters {@code index}.
     */
    private ExportCommand prepareCommand(Index index) {
        ExportCommand exportCommand = new ExportCommand(index);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }
}
