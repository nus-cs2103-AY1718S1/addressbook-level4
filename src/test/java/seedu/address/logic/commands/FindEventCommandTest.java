package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_EVENTS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalEvents.FORTH;
import static seedu.address.testutil.TypicalEvents.SECOND;
import static seedu.address.testutil.TypicalEvents.THIRD;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    @Test
    public void equals() {
        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("first"));
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        FindEventCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format
                (MESSAGE_EVENTS_LISTED_OVERVIEW, 4);
        FindEventCommand command = prepareCommand("meeting");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIRST, SECOND, THIRD, FORTH));
    }

    /**
     * Parses {@code userInput} into a {@code FindEventCommand}.
     */
    private FindEventCommand prepareCommand(String userInput) {
        FindEventCommand command =
                new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindEventCommand command, String expectedMessage, List<ReadOnlyEvent> expected) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expected, model.getFilteredEventList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
