package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.FindCommandParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 * Cases only test for finding of name and tag (name has the same implementation as other fields)
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //@@author NabeelZaheer
    @Test
    public void equals() {
        List<String> keywords1 = Arrays.asList("first", "third");
        List<String> keywords2 = new ArrayList<>();
        List<String> keywords3 = new ArrayList<>();
        List<String> keywords4 = new ArrayList<>();
        List<String> keywords5 = new ArrayList<>();
        List<List<String>> list1 = new ArrayList<>();
        list1.add(keywords1);
        list1.add(keywords2);
        list1.add(keywords3);
        list1.add(keywords4);
        list1.add(keywords5);

        List<String> keywordsA = Arrays.asList("second", "fourth");
        List<String> keywordsB = new ArrayList<>();
        List<String> keywordsC = new ArrayList<>();
        List<String> keywordsD = new ArrayList<>();
        List<String> keywordsE = new ArrayList<>();
        List<List<String>> list2 = new ArrayList<>();
        list2.add(keywordsA);
        list2.add(keywordsB);
        list2.add(keywordsC);
        list2.add(keywordsD);
        list2.add(keywordsE);

        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(list1);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(list2);


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
    public void execute_keyword_noPersonFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindCommand command = prepareCommand(" n/Bob");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand(" n/Kurz n/Elle n/Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    @Test
    public void execute_multipleTagKeywords_multiplePersonFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindCommand command = prepareCommand(" t/owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }
    //@@author
    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String userInput) throws Exception {
        FindCommandParser parser = new FindCommandParser();

        FindCommand command = parser.parse(userInput);
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
