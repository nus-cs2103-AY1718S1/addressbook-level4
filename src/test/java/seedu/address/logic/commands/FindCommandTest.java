package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.FindCommand.FALSE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.predicate.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicate.PhoneContainsKeywordsPredicate;

//@@author newalter
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        Predicate<ReadOnlyPerson> component1 = new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        Predicate<ReadOnlyPerson> component2 = new NameContainsKeywordsPredicate(Collections.singletonList("second"));
        Predicate<ReadOnlyPerson> component3 = new PhoneContainsKeywordsPredicate(Collections.singletonList("third"));
        Predicate<ReadOnlyPerson> component4 = new PhoneContainsKeywordsPredicate(Collections.singletonList("fourth"));

        ArrayList<Predicate<ReadOnlyPerson>> firstPredicates = new ArrayList<>();
        ArrayList<Predicate<ReadOnlyPerson>> secondPredicates = new ArrayList<>();
        firstPredicates.addAll(Arrays.asList(component1, component3, FALSE, FALSE, FALSE));
        secondPredicates.addAll(Arrays.asList(component2, component4, FALSE, FALSE, FALSE));

        FindCommand findFirstCommand = new FindCommand(firstPredicates);
        FindCommand findSecondCommand = new FindCommand(secondPredicates);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicates);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different keyword -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_wildcardKeywordsOnName_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/b?nson");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_wildcardKeywordsOnName_twoPersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("n/b*");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, GEORGE));
    }

    @Test
    public void execute_multipleKeywordsOnName_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("n/Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleKeywordsOnPhone_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand("p/85355255 98765432");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_wildcardKeywordOnEmail_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("e/*a@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(DANIEL, FIONA, GEORGE));
    }

    @Test
    public void execute_oneKeywordOnAddress_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("a/clementi");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_oneKeywordOnTag_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("t/owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleFields_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("n/meier t/fami??");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, DANIEL));
    }

    @Test
    public void execute_multipleFields_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/alice p/85355255 t/fami?y e/alice*");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {
        FindCommand command = new FindCommand(new ArrayList<>());
        try {
            command = (new FindCommandParser()).parse(" " + userInput);
            command.setData(model, new CommandHistory(), new UndoRedoStack());
        } catch (ParseException e) {
            assert false : "userInput is valid";
        }
        return command;
    }
    //@@author

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
