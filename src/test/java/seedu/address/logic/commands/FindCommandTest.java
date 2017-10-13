package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
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
import seedu.address.model.person.AddressContainsKeywordPredicate;
import seedu.address.model.person.EmailContainsKeywordPredicate;
import seedu.address.model.person.NameContainsKeywordPredicate;
import seedu.address.model.person.PersonContainsFieldsPredicate;
import seedu.address.model.person.PhoneContainsKeywordPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {

        List<Predicate> firstPredicateList =
                Arrays.asList(
                        new NameContainsKeywordPredicate("name_first"),
                        new EmailContainsKeywordPredicate("email_first"),
                        new TagsContainKeywordPredicate("tag_first"),
                        new PhoneContainsKeywordPredicate("phone_first"),
                        new AddressContainsKeywordPredicate("address_first")
                );

        List<Predicate> secondPredicateList =
                Arrays.asList(
                        new NameContainsKeywordPredicate("name_second"),
                        new EmailContainsKeywordPredicate("email_second"),
                        new TagsContainKeywordPredicate("tag_second"),
                        new PhoneContainsKeywordPredicate("phone_second"),
                        new AddressContainsKeywordPredicate("address_second")
                );

        PersonContainsFieldsPredicate firstPredicate =
                new PersonContainsFieldsPredicate(firstPredicateList);

        PersonContainsFieldsPredicate secondPredicate =
                new PersonContainsFieldsPredicate(secondPredicateList);


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
    public void execute_multipleKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/Meier a/311, Clementi Ave 2, #02-25 t/friends p/98765432");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_shortenedKeyword_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("n/Ben");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleKeywordsCaseInsensitive_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand("meieR a/clEmenti n/BeNsOn");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }


    @Test
    public void execute_multipleKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand("n/Kurz n/Elle n/Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList());
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) {
        FindCommand command = new FindCommand((person) -> true);
        try {
            command = new FindCommandParser().parse(userInput);
        } catch (ParseException pe) {
            assert false;
        }

        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

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
