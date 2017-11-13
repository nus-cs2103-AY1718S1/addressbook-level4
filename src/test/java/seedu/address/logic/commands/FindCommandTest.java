package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.GuiUnitTest;

//@@author KhorSL

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest extends GuiUnitTest {
    private Model model;
    private Email emailManager = new EmailManager();
    private Logic logic = new LogicManager(model, emailManager);

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void equals() {
        HashMap<String, List<String>> firstPredicateMap = new HashMap<>();
        HashMap<String, List<String>> secondPredicateMap = new HashMap<>();
        firstPredicateMap.put("first", Collections.singletonList("first"));
        secondPredicateMap.put("second", Collections.singletonList("second"));

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(firstPredicateMap);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(secondPredicateMap);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws ParseException {
        String command = FindCommand.COMMAND_WORD + " ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertCommandFailure(ParseException.class, command, expectedMessage, logic);
    }

    @Test
    public void execute_singleKeyword() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand(" n/Kurz");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(CARL));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        command = prepareCommand(" e/lydia");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(FIONA));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        command = prepareCommand(" p/9482");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, GEORGE));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        command = prepareCommand(" ap/12:12");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        command = prepareCommand(" a/wall");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(CARL));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        command = prepareCommand(" c/tetris");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(GEORGE));

        expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        command = prepareCommand(" r/friend");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(" n/Kurz Elle Kunz r/dummy e/@dummy.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand(" e/lydia a/wall p/224");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));

        command = prepareCommand(" e/lydia werner a/tokyo wall c/swim ap/10:30");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) throws ParseException {
        FindCommandParser parser = new FindCommandParser();
        FindCommand command = parser.parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), null);
        return command;
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @param expectedException expected exception
     * @param inputCommand input command
     * @param expectedMessage expected message
     * @param expectedLogic expected logic
     */
    private void assertCommandFailure(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, Logic expectedLogic) {

        try {
            CommandResult result = expectedLogic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

    }
    //@@author

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
