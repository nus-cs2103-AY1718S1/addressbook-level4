package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.logic.commands.CommandTestUtil.quitEvent;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

// @@author Adoby7
/**
 * Contains integration tests for disjoin command
 */
public class DisjoinCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void testDisjoinInvalidIndexFail() {
        final Index validIndex = INDEX_FIRST_EVENT;
        Index invalidLargeIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DisjoinCommand invalidPersonIndexCommand = prepareCommand(invalidLargeIndex, validIndex, model);

        invalidLargeIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DisjoinCommand invalidEventIndexCommand = prepareCommand(validIndex, invalidLargeIndex, model);

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(invalidEventIndexCommand, model, expectedMessage);
        assertCommandFailure(invalidPersonIndexCommand, model, expectedMessage);
    }

    @Test
    public void testNotParticipantFail() {
        String expectedMessage = DisjoinCommand.MESSAGE_PERSON_NOT_PARTICIPATE;
        final Index validIndex = INDEX_FIRST_EVENT;
        // last person does not join any event
        final Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Model actualModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        DisjoinCommand noParticipantCommand = prepareCommand(invalidIndex, validIndex, actualModel);

        assertCommandFailure(noParticipantCommand, actualModel, expectedMessage);

    }

    @Test
    public void testSuccess() {
        Index personIndex = INDEX_SECOND_PERSON;
        Index eventIndex = INDEX_FIRST_EVENT;
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        Person person = (Person) expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event event = (Event) expectedModel.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String expectedMessage = String.format(DisjoinCommand.MESSAGE_DISJOIN_SUCCESS, person.getName(),
            event.getEventName());
        DisjoinCommand command = prepareCommand(personIndex, eventIndex, model);
        quitEvent(expectedModel, INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code DisjoinCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private DisjoinCommand prepareCommand(Index personIndex, Index eventIndex, Model model) {
        DisjoinCommand command = new DisjoinCommand(personIndex, eventIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
