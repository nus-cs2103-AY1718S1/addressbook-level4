package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
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
 * Contains integration tests for disjoin command
 */
public class DisjoinCommandTest {

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void testDisjoinInvalidIndexFail() {
        final Index validIndex = Index.fromOneBased(1);
        final Index invalidLargeIndex = Index.fromOneBased(10000);
        DisjoinCommand invalidPersonIndexCommand = prepareCommand(invalidLargeIndex, validIndex, model);
        DisjoinCommand invalidEventIndexCommand = prepareCommand(validIndex, invalidLargeIndex, model);

        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(invalidEventIndexCommand, model, expectedMessage);
        assertCommandFailure(invalidPersonIndexCommand, model, expectedMessage);
    }

    @Test
    public void testNotParticipantFail() {
        String expectedMessage = DisjoinCommand.MESSAGE_PERSON_NOT_PARTICIPATE;
        final Index validIndex = Index.fromOneBased(1);
        final Index invalidIndex = Index.fromOneBased(6);
        Model actualModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        DisjoinCommand noParticipantCommand = prepareCommand(invalidIndex, validIndex, actualModel);

        assertCommandFailure(noParticipantCommand, actualModel, expectedMessage);

    }

    @Test
    public void testSuccess() throws Exception {
        Index personIndex = Index.fromOneBased(2);
        Index eventIndex = Index.fromOneBased(1);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());
        Person person = (Person) expectedModel.getFilteredPersonList().get(1);
        Event event = (Event) expectedModel.getFilteredEventList().get(0);
        String expectedMessage = String.format(DisjoinCommand.MESSAGE_DISJOIN_SUCCESS, person.getName(),
            event.getEventName());
        DisjoinCommand command = prepareCommand(personIndex, eventIndex, model);
        expectedModel.quitEvent(person, event);

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

    /**
     * Let some persons join certain events
     */
    private void joinEvents(Model model) throws Exception {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                Person person = (Person) model.getFilteredPersonList().get(i);
                Event event = (Event) model.getFilteredEventList().get(j);
                model.joinEvent(person, event);
            }
        }

    }
}
