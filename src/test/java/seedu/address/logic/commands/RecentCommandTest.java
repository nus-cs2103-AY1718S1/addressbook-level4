package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.FindHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author karrui
public class RecentCommandTest {
    private RecentCommand recentCommand;
    private FindHistory findHistory;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        findHistory = new FindHistory();
        recentCommand = new RecentCommand();
        recentCommand.setData(model, new CommandHistory(), findHistory, new UndoRedoStack());
    }

    @Test
    public void execute() {
        String expectedMessage = RecentCommand.MESSAGE_SUCCESS;
        assertCommandResult(recentCommand, RecentCommand.MESSAGE_NO_RECENT);    // nothing in findHistory yet
        findHistory.add(ALICE);
        assertCommandSuccess(Collections.singletonList(ALICE)); // backing list should be equals to argument
        assertCommandResult(recentCommand, expectedMessage); // success message
    }

    /**
     * Asserts that the result message from the execution of {@code recentCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(RecentCommand recentCommand, String expectedMessage) {
        assertEquals(expectedMessage, recentCommand.execute().feedbackToUser);
    }

    /**
     * Asserts that
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        recentCommand.execute();
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
