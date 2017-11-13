package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_BOOKMARKS_SHOWN;
import static seedu.address.testutil.TypicalPlaces.ELLE;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * Contains integration tests (interaction with the Model) for {@code ShowBookmarkCommand}.
 */
public class ShowBookmarkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_bookmark_found() {
        String expectedMessage = MESSAGE_BOOKMARKS_SHOWN;
        ShowBookmarkCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code ShowBookmarkCommand}.
     */
    private ShowBookmarkCommand prepareCommand() {

        ShowBookmarkCommand command =
                new ShowBookmarkCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPlace>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ShowBookmarkCommand command, String expectedMessage,
                                                                            List<ReadOnlyPlace> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPlaceList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
