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

/**
 * contains integration test for join command
 */
public class JoinCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void testJoinInvalidIndexFail() {
        final Index validIndex = INDEX_FIRST_EVENT;
        Index invalidLargeIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        JoinCommand invalidPersonIndexCommand = prepareCommand(invalidLargeIndex, validIndex, model);

        invalidLargeIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        JoinCommand invalidEventIndexCommand = prepareCommand(validIndex, invalidLargeIndex, model);

        String expectedMessageForPerson = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        String expectedMessageForEvent = Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;
        assertCommandFailure(invalidEventIndexCommand, model, expectedMessageForEvent);
        assertCommandFailure(invalidPersonIndexCommand, model, expectedMessageForPerson);
    }

    @Test
    public void testHaveParticipatedFail() {
        String expectedMessage = JoinCommand.MESSAGE_DUPLICATE_PERSON;
        final Index validIndex = INDEX_FIRST_EVENT;
        final Index invalidIndex = Index.fromOneBased(2);
        Model actualModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        JoinCommand noParticipantCommand = prepareCommand(invalidIndex, validIndex, actualModel);

        assertCommandFailure(noParticipantCommand, actualModel, expectedMessage);

    }

    @Test
    public void testSuccess() {
        Index personIndex = INDEX_SECOND_PERSON;
        Index eventIndex = INDEX_FIRST_EVENT;
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        Person person = (Person) expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Event event = (Event) expectedModel.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String expectedMessage = String.format(JoinCommand.MESSAGE_JOIN_SUCCESS, person.getName(),
                event.getEventName());
        JoinCommand command = prepareCommand(personIndex, eventIndex, model);
        quitEvent(expectedModel, INDEX_SECOND_PERSON, INDEX_FIRST_EVENT);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code DisjoinCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private JoinCommand prepareCommand(Index personIndex, Index eventIndex, Model model) {
        JoinCommand command = new JoinCommand(personIndex, eventIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
