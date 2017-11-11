package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests for show participants command
 */
public class ShowParticipantsCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void executeNoPersonJoinsEventShowsEmptyList() {
        Event event = (Event) model.getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased());
        String expectedMessage =
                String.format(ShowParticipantsCommand.MESSAGE_SHOW_PARTICIPANTS_SUCCESS, event.getEventName());
        ShowParticipantsCommand command = prepareCommand(INDEX_THIRD_EVENT);

        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executePersonJoinsEventShowsEverything() {
        Event event = (Event) model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        String expectedMessage =
                String.format(ShowParticipantsCommand.MESSAGE_SHOW_PARTICIPANTS_SUCCESS, event.getEventName());
        ShowParticipantsCommand command = prepareCommand(INDEX_FIRST_EVENT);

        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL));
    }

    /**
     * Parses {@code eventIndex} into a {@code ShowParticipantsCommand}.
     */
    private ShowParticipantsCommand prepareCommand(Index eventIndex) {
        ShowParticipantsCommand command = new ShowParticipantsCommand(eventIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code message}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code list}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ShowParticipantsCommand command, String message, List<ReadOnlyPerson> list) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();
            assertEquals(message, commandResult.feedbackToUser);
            assertEquals(list, model.getFilteredPersonList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        }   catch (CommandException e)  {
            e.printStackTrace();
        }
    }
}
