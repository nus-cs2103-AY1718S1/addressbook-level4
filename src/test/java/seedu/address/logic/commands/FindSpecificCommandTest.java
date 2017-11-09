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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmailContainsSpecifiedKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsSpecifiedKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagContainsSpecifiedKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSpecificCommand}.
 */
//@@author aver0214
public class FindSpecificCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondNamePredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        PhoneContainsSpecifiedKeywordsPredicate firstPhonePredicate =
                new PhoneContainsSpecifiedKeywordsPredicate(Collections.singletonList("12345678"));

        EmailContainsSpecifiedKeywordsPredicate firstEmailPredicate =
                new EmailContainsSpecifiedKeywordsPredicate(Collections.singletonList("first@email.com"));

        TagContainsSpecifiedKeywordsPredicate firstTagPredicate =
                new TagContainsSpecifiedKeywordsPredicate(Collections.singletonList("[important]"));

        FindSpecificCommand findFirstNameCommand = new FindSpecificCommand(firstNamePredicate);
        FindSpecificCommand findSecondNameCommand = new FindSpecificCommand(secondNamePredicate);
        FindSpecificCommand findFirstPhoneCommand = new FindSpecificCommand(firstPhonePredicate);
        FindSpecificCommand findFirstEmailCommand = new FindSpecificCommand(firstEmailPredicate);
        FindSpecificCommand findFirstTagCommand = new FindSpecificCommand(firstTagPredicate);

        // same object -> returns true
        assertTrue(findFirstNameCommand.equals(findFirstNameCommand));

        // same object -> returns true
        assertTrue(findFirstPhoneCommand.equals(findFirstPhoneCommand));

        // same object -> returns true
        assertTrue(findFirstEmailCommand.equals(findFirstEmailCommand));

        // same object -> returns true
        assertTrue(findFirstTagCommand.equals(findFirstTagCommand));

        // same values -> returns true
        FindSpecificCommand findFirstCommandCopy = new FindSpecificCommand(firstNamePredicate);
        assertTrue(findFirstNameCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstNameCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstNameCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstNameCommand.equals(findSecondNameCommand));
    }

    /**
    * Find person(s) by name(s).
    */

    @Test
    public void execute_zeroKeywords_invalidCommandFormat() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindSpecificCommand command = prepareFindByNameCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findElleByName_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindSpecificCommand command = prepareFindByNameCommand("Elle");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE));
    }

    @Test
    public void execute_findMultipleNames_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindSpecificCommand command = prepareFindByNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Find person(s) by phone(s).
     */
    @Test
    public void execute_findEllebyPhone_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindSpecificCommand command = prepareFindByPhoneCommand("9482224");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE));
    }

    @Test
    public void execute_findMultiplePhones_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindSpecificCommand command = prepareFindByPhoneCommand("95352563 9482224 9482427");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Find person(s) by email(s).
     */
    @Test
    public void execute_findEllebyEmail_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindSpecificCommand command = prepareFindByEmailCommand("werner@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ELLE));
    }

    @Test
    public void execute_findMultipleEmails_multiplePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindSpecificCommand command =
            prepareFindByEmailCommand("heinz@example.com werner@example.com lydia@example.com");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Find person(s) by tag(s).
     */
    @Test
    public void execute_findATag_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindSpecificCommand command = prepareFindByTagCommand("[BOSS]");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findMultipleTags_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindSpecificCommand command = prepareFindByTagCommand("[owesMoney] [friend]");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    /**
     * Creates a new FindSpecificCommand using {@code NameContainsKeywordsPredicate}
     *
     * @param inputString full string of name(s) to find
     * @return a new FindSpecificCommand using {@code NameContainsKeywordsPredicate}
     */
    private FindSpecificCommand prepareFindByNameCommand (String inputString) {
        FindSpecificCommand command = new FindSpecificCommand(
                new NameContainsKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Creates a new FindSpecificCommand using {@code PhoneContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of phone(s) to find
     * @return a new FindSpecificCommand using {@code PhoneContainsSpecifiedKeywordsPredicate}
     */
    private FindSpecificCommand prepareFindByPhoneCommand (String inputString) {
        FindSpecificCommand command = new FindSpecificCommand(
                new PhoneContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Creates a new FindSpecificCommand using {@code EmailContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of email(s) to find
     * @return a new FindSpecificCommand using {@code EmailContainsSpecifiedKeywordsPredicate}
     */
    private FindSpecificCommand prepareFindByEmailCommand (String inputString) {
        FindSpecificCommand command = new FindSpecificCommand(
                new EmailContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Creates a new FindSpecificCommand using {@code TagContainsSpecifiedKeywordsPredicate}
     *
     * @param inputString full string of Tag(s) to find
     * @return a new FindSpecificCommand using {@code TagContainsSpecifiedKeywordsPredicate}
     */
    private FindSpecificCommand prepareFindByTagCommand (String inputString) {
        FindSpecificCommand command = new FindSpecificCommand(
                new TagContainsSpecifiedKeywordsPredicate(Arrays.asList(inputString.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    } //@@author

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindSpecificCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
