package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Config;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Account;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.UiManager;

//@@author tingtx
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model expectedModel;
    private ListCommand listCommand;
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs(),
            new Account(), new Config());

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs(), new Account(),
                new Config());
        listCommand = new ListCommand("");
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_LIST_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        String expectedMessage = ListCommand.MESSAGE_LIST_ALL_SUCCESS;
        listCommand = prepareCommand("");
        assertListCommandSuccess(listCommand, expectedMessage, model.getFilteredPersonList());
    }


    @Test
    public void execute_listIsFiltered_showsSpecifiedGroup() {
        String expectedMessage = listCommand.MESSAGE_LIST_GROUP_SUCCESS + "NUS";
        listCommand = prepareCommand("g/NUS");
        assertListCommandSuccess(listCommand, expectedMessage, Arrays.asList(ELLE, FIONA));

        expectedMessage = listCommand.MESSAGE_LIST_GROUP_SUCCESS;
        listCommand = prepareCommand("g/");
        assertListCommandSuccess(listCommand, expectedMessage, Arrays.asList(ALICE));
    }

    /**
     * Parses {@code userInput} into a {@code ListCommand}.
     */
    private ListCommand prepareCommand(String userInput) {
        ListCommand command = new ListCommand(userInput);
        UserPrefs userPrefs = new UserPrefs();
        Config config = new Config();
        Logic logic = null;
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new Config(),
                new UiManager(logic, config, userPrefs));
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertListCommandSuccess(ListCommand command, String expectedMessage,
                                          List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());

        ContainsKeywordsPredicate.setPredicateType('g');
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }



}
