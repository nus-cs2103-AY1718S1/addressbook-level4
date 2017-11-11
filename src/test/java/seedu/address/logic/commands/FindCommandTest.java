package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_ADDRESS_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_EMAIL_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DEFAULT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_PHONE_DESCENDING;
import static seedu.address.logic.parser.SortUtil.setupArguments;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.SortArgument;
import seedu.address.logic.parser.exceptions.ParseArgsException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Rolodex;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonDataContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalRolodex(), new UserPrefs());

    @Test
    public void equals() {
        PersonDataContainsKeywordsPredicate firstPredicate =
                new PersonDataContainsKeywordsPredicate(Collections.singletonList("first"));
        PersonDataContainsKeywordsPredicate secondPredicate =
                new PersonDataContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate, new ArrayList<>());
        FindCommand findSecondCommand = new FindCommand(secondPredicate, new ArrayList<>());

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate, new ArrayList<>());
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleKeywordsSortedByNameDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_NAME_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleKeywordsSortedByNameDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_NAME_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, ELLE, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByNameAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_NAME_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleKeywordsSortedByPhoneDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_PHONE_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByPhoneDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_PHONE_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, ELLE));
    }

    @Test
    public void executeMultipleKeywordsSortedByPhoneAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_PHONE_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByEmailDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_EMAIL_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, ELLE));
    }

    @Test
    public void executeMultipleKeywordsSortedByEmailDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_EMAIL_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE, FIONA, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByEmailAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_EMAIL_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, FIONA, ELLE));
    }

    @Test
    public void executeMultipleKeywordsSortedByAddressDefaultShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_ADDRESS_DEFAULT);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, ELLE, CARL));
    }

    @Test
    public void executeMultipleKeywordsSortedByAddressDescendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_ADDRESS_DESCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void executeMultipleKeywordsSortedByAddressAscendingShowsMultiplePersonsSorted() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("Kurz Elle Kunz " + SORT_ARGUMENT_ADDRESS_ASCENDING);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, ELLE, CARL));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {

        String[] keywords = userInput.split("\\s+");
        List<String> findKeywordList = new ArrayList<>();
        List<SortArgument> sortKeywordList = new ArrayList<>();

        try {
            setupArguments(keywords, findKeywordList, sortKeywordList, FindCommand.MESSAGE_USAGE);
        } catch (ParseArgsException e) {
            throw new AssertionError("Unable to parse arguments.", e);
        }
        FindCommand command = new FindCommand(
                new PersonDataContainsKeywordsPredicate(findKeywordList), sortKeywordList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code Rolodex} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        Rolodex expectedRolodex = new Rolodex(model.getRolodex());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getLatestPersonList());
        assertEquals(expectedRolodex, model.getRolodex());
    }
}
