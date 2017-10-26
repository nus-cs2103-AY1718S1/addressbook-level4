package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class DuplicatesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private DuplicatesCommand prepareCommand() {
        DuplicatesCommand command = new DuplicatesCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void executeZeroDuplicatesFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        DuplicatesCommand command = prepareCommand();
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(DuplicatesCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
