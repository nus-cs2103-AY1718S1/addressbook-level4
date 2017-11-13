package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalEvents.SECOND;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.Person;

//@@author LeonChowWenHao

/**
 * Contains integration tests (interaction with the Model) for {@code SelectJoinedEventsCommand}.
 */
public class SelectJoinedEventsCommandTest {
    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinPersonsToEvents(model);
    }

    /**
     * Test for entering single invalid index value. All assertions should be failures.
     **/
    @Test
    public void executeInvalidIndexFailure() {
        // Invalid out of bounds index
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertExecutionFailure(Collections.singletonList(outOfBoundsIndex),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Test for entering multiple invalid index value. All assertions should be failures.
     **/
    @Test
    public void executeMultipleInvalidIndexesFailure() {
        // One valid index, one invalid out of bounds index
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        assertExecutionFailure(Arrays.asList(INDEX_FIRST_PERSON, outOfBoundsIndex),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // Multiple invalid out of bounds index
        assertExecutionFailure(Arrays.asList(outOfBoundsIndex, outOfBoundsIndex),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Test for entering single valid index value. All assertions should be successful.
     **/
    @Test
    public void executeValidIndexSuccess() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        // First person index - Alice - Valid - 1 event joined total
        assertExecutionSuccess(Collections.singletonList(INDEX_FIRST_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1), ALICE.getName()),
                Collections.singletonList(FIRST));

        // Third person index - Carl - Valid - 2 events joined total
        assertExecutionSuccess(Collections.singletonList(INDEX_THIRD_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2), CARL.getName()),
                Arrays.asList(FIRST, SECOND));

        // Last person index - George - Valid - 0 events joined total
        assertExecutionSuccess(Collections.singletonList(lastPersonIndex),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0), GEORGE.getName()),
                Collections.emptyList());
    }

    /**
     * Test for entering multiple valid index values. All assertions should be successful.
     **/
    @Test
    public void executeMultipleValidIndexesSuccess() {
        Index secondLastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size() - 1);
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        // First & last person index - Alice, George - Valid - 1 event joined total
        assertExecutionSuccess(Arrays.asList(INDEX_FIRST_PERSON, lastPersonIndex),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 1), ALICE.getName() + ", "
                        + GEORGE.getName()),
                Collections.singletonList(FIRST));

        // First & second person index - Alice, Benson - Valid - 2 events joined total
        assertExecutionSuccess(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2), ALICE.getName() + ", "
                        + BENSON.getName()),
                Arrays.asList(FIRST, SECOND));

        // Second & third person index - Benson, Carl - Valid - 2 events joined total
        assertExecutionSuccess(Arrays.asList(INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 2), BENSON.getName() + ", "
                        + CARL.getName()),
                Arrays.asList(FIRST, SECOND));

        // Second last & last person index - Fiona, George - Valid - 0 events joined total
        assertExecutionSuccess(Arrays.asList(secondLastPersonIndex, lastPersonIndex),
                String.format(SelectJoinedEventsCommand.MESSAGE_SELECT_PERSON_JOINED_EVENTS + ", "
                        + String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0), FIONA.getName() + ", "
                        + GEORGE.getName()),
                Collections.emptyList());
    }

    @Test
    public void equals() {
        SelectJoinedEventsCommand selectJoinedEventsFirstCommand = new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_FIRST_PERSON));
        SelectJoinedEventsCommand selectJoinedEventsSecondCommand = new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(selectJoinedEventsFirstCommand.equals(selectJoinedEventsFirstCommand));

        // same values -> returns true
        SelectJoinedEventsCommand selectJoinedEventsFirstCommandCopy = new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_FIRST_PERSON));
        assertTrue(selectJoinedEventsFirstCommand.equals(selectJoinedEventsFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectJoinedEventsFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectJoinedEventsFirstCommand == null);

        // different person index -> returns false
        assertFalse(selectJoinedEventsFirstCommand.equals(selectJoinedEventsSecondCommand));
    }

    /**
     * Executes a {@code SelectJoinedEventsCommand} with the given {@code indexList},
     * and checks that the expected message and filteredEventList are equal.
     */
    private void assertExecutionSuccess(List<Index> indexList, String expectedMessage,
                                        List<ReadOnlyEvent> expectedEventList) {
        SelectJoinedEventsCommand selectJoinedEventsCommand = prepareCommand(indexList);

        try {
            CommandResult commandResult = selectJoinedEventsCommand.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedEventList, model.getFilteredEventList());
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes a {@code SelectJoinedEventsCommand} with the given {@code indexList}, and checks that a
     * {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(List<Index> indexList, String expectedMessage) {
        SelectJoinedEventsCommand selectJoinedEventsCommand = prepareCommand(indexList);

        try {
            selectJoinedEventsCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code SelectJoinedEventsCommand} with parameters {@code indexList}.
     */
    private SelectJoinedEventsCommand prepareCommand(List<Index> indexList) {
        SelectJoinedEventsCommand selectJoinedEventsCommand = new SelectJoinedEventsCommand(indexList);
        selectJoinedEventsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectJoinedEventsCommand;
    }

    /**
     * Sets some persons to join events so the {@code SelectJoinedEventsCommand} can be tested.
     */
    private void joinPersonsToEvents (Model model) throws Exception {
        Person person1 = (Person) model.getFilteredPersonList().get(0);
        Person person2 = (Person) model.getFilteredPersonList().get(1);
        Person person3 = (Person) model.getFilteredPersonList().get(2);

        Event event1 = (Event) model.getFilteredEventList().get(0);
        Event event2 = (Event) model.getFilteredEventList().get(1);

        model.joinEvent(person1, event1);
        model.joinEvent(person2, event2);
        model.joinEvent(person3, event1);
        model.joinEvent(person3, event2);
    }

}
