package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
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
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.AnyContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.TagContainsKeywordsPredicate;
import seedu.address.testutil.StorageStub;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AnyContainsKeywordsPredicate firstPredicate =
                new AnyContainsKeywordsPredicate(Collections.singletonList("first"));
        AnyContainsKeywordsPredicate secondPredicate =
                new AnyContainsKeywordsPredicate(Collections.singletonList("second"));
        NameContainsKeywordsPredicate thirdPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("third"));
        NameContainsKeywordsPredicate fourthPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("fourth"));
        AddressContainsKeywordsPredicate fifthPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("fifth"));
        AddressContainsKeywordsPredicate sixthPredicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("sixth"));
        EmailContainsKeywordsPredicate seventhPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("seventh"));
        EmailContainsKeywordsPredicate eighthPredicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("eighth"));
        PhoneContainsKeywordsPredicate ninthPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("ninth"));
        PhoneContainsKeywordsPredicate tenthPredicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("tenth"));
        TagContainsKeywordsPredicate eleventhPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("eleventh"));
        TagContainsKeywordsPredicate twelfthPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("twelfth"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);
        FindCommand findThirdCommand = new FindCommand(thirdPredicate);
        FindCommand findFourthCommand = new FindCommand(fourthPredicate);
        FindCommand findFifthCommand = new FindCommand(fifthPredicate);
        FindCommand findSixthCommand = new FindCommand(sixthPredicate);
        FindCommand findSeventhCommand = new FindCommand(seventhPredicate);
        FindCommand findEighthCommand = new FindCommand(eighthPredicate);
        FindCommand findNinthCommand = new FindCommand(ninthPredicate);
        FindCommand findTenthCommand = new FindCommand(tenthPredicate);
        FindCommand findEleventhCommand = new FindCommand(eleventhPredicate);
        FindCommand findTwelfthCommand = new FindCommand(twelfthPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));
        assertTrue(findThirdCommand.equals(findThirdCommand));
        assertTrue(findFifthCommand.equals(findFifthCommand));
        assertTrue(findSeventhCommand.equals(findSeventhCommand));
        assertTrue(findNinthCommand.equals(findNinthCommand));
        assertTrue(findEleventhCommand.equals(findEleventhCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));
        FindCommand findThirdCommandCopy = new FindCommand(thirdPredicate);
        assertTrue(findThirdCommand.equals(findThirdCommandCopy));
        FindCommand findFifthCommandCopy = new FindCommand(fifthPredicate);
        assertTrue(findFifthCommand.equals(findFifthCommandCopy));
        FindCommand findSeventhCommandCopy = new FindCommand(seventhPredicate);
        assertTrue(findSeventhCommand.equals(findSeventhCommandCopy));
        FindCommand findNinthCommandCopy = new FindCommand(ninthPredicate);
        assertTrue(findNinthCommand.equals(findNinthCommandCopy));
        FindCommand findEleventhCommandCopy = new FindCommand(eleventhPredicate);
        assertTrue(findEleventhCommand.equals(findEleventhCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));
        assertFalse(findThirdCommand.equals(1));
        assertFalse(findFifthCommand.equals(1));
        assertFalse(findSeventhCommand.equals(1));
        assertFalse(findNinthCommand.equals(1));
        assertFalse(findEleventhCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));
        assertFalse(findThirdCommand.equals(null));
        assertFalse(findFifthCommand.equals(null));
        assertFalse(findSeventhCommand.equals(null));
        assertFalse(findNinthCommand.equals(null));
        assertFalse(findEleventhCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(findThirdCommand.equals(findFourthCommand));
        assertFalse(findFifthCommand.equals(findSixthCommand));
        assertFalse(findSeventhCommand.equals(findEighthCommand));
        assertFalse(findNinthCommand.equals(findTenthCommand));
        assertFalse(findEleventhCommand.equals(findTwelfthCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = expectedMessage(0);

        FindCommand command = prepareGlobalCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());

        FindCommand nameCommand = prepareNameCommand(" ");
        assertCommandSuccess(nameCommand, expectedMessage, Collections.emptyList());

        FindCommand addressCommand = prepareAddressCommand(" ");
        assertCommandSuccess(addressCommand, expectedMessage, Collections.emptyList());

        FindCommand emailCommand = prepareEmailCommand(" ");
        assertCommandSuccess(emailCommand, expectedMessage, Collections.emptyList());

        FindCommand phoneCommand = preparePhoneCommand(" ");
        assertCommandSuccess(phoneCommand, expectedMessage, Collections.emptyList());

        FindCommand tagCommand = prepareTagCommand(" ");
        assertCommandSuccess(tagCommand, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_singleKeyword_onePersonFound() {
        String expectedMessage = expectedMessage(1);

        FindCommand command = prepareGlobalCommand("Kurz");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL));

        FindCommand nameCommand = prepareNameCommand("Elle");
        assertCommandSuccess(nameCommand, expectedMessage, Arrays.asList(ELLE));

        FindCommand addressCommand = prepareAddressCommand("10th");
        assertCommandSuccess(addressCommand, expectedMessage, Arrays.asList(DANIEL));

        FindCommand emailCommand = prepareEmailCommand("alice@example.com");
        assertCommandSuccess(emailCommand, expectedMessage, Arrays.asList(ALICE));

        FindCommand phoneCommand = preparePhoneCommand("948242");
        assertCommandSuccess(phoneCommand, expectedMessage, Arrays.asList(FIONA));

        FindCommand tagCommand = prepareTagCommand("owesMoney");
        assertCommandSuccess(tagCommand, expectedMessage(2), Arrays.asList(BENSON, FIONA));
    }

    @Test
    public void execute_singleKeyword_multiplePersonsFound() {
        FindCommand command = prepareGlobalCommand("Meier");
        assertCommandSuccess(command, expectedMessage(2), Arrays.asList(BENSON, DANIEL));

        FindCommand nameCommand = prepareNameCommand("Meier");
        assertCommandSuccess(nameCommand, expectedMessage(2), Arrays.asList(BENSON, DANIEL));

        FindCommand addressCommand = prepareAddressCommand("ave");
        assertCommandSuccess(addressCommand, expectedMessage(3), Arrays.asList(ALICE, BENSON, ELLE));

        FindCommand emailCommand = prepareEmailCommand("@example.com");
        assertCommandSuccess(emailCommand, expectedMessage(7),
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));

        FindCommand phoneCommand = preparePhoneCommand("94824");
        assertCommandSuccess(phoneCommand, expectedMessage(2), Arrays.asList(FIONA, GEORGE));

        FindCommand tagCommand = prepareTagCommand("friends");
        assertCommandSuccess(tagCommand, expectedMessage(5),
                Arrays.asList(ALICE, BENSON, CARL, DANIEL, GEORGE));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        FindCommand command = prepareGlobalCommand("Kurz Elle Kunz");
        assertCommandSuccess(command, expectedMessage(3), Arrays.asList(CARL, ELLE, FIONA));

        FindCommand nameCommand = prepareNameCommand("Kurz Elle Kunz");
        assertCommandSuccess(nameCommand, expectedMessage(3), Arrays.asList(CARL, ELLE, FIONA));

        FindCommand addressCommand = prepareAddressCommand("ave 10");
        assertCommandSuccess(addressCommand, expectedMessage(4), Arrays.asList(ALICE, BENSON, DANIEL, ELLE));

        FindCommand emailCommand = prepareEmailCommand("alice anna");
        assertCommandSuccess(emailCommand, expectedMessage(2), Arrays.asList(ALICE, GEORGE));

        FindCommand phoneCommand = preparePhoneCommand("953  94824");
        assertCommandSuccess(phoneCommand, expectedMessage(3), Arrays.asList(CARL, FIONA, GEORGE));

        FindCommand tagCommand = prepareTagCommand("owesMoney enemy");
        assertCommandSuccess(tagCommand, expectedMessage(3), Arrays.asList(BENSON, CARL, FIONA));
    }

    /**
     * Formats the expected message and changes the message according to the number of persons found.
     * @param personsFound
     */
    private String expectedMessage(int personsFound) {
        return String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, personsFound);
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a global find.
     */
    private FindCommand prepareGlobalCommand(String userInput) {
        FindCommand command =
                new FindCommand(new AnyContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a name search.
     */
    private FindCommand prepareNameCommand(String userInput) {
        FindCommand command =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for an address search.
     */
    private FindCommand prepareAddressCommand(String userInput) {
        FindCommand command =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for an email search.
     */
    private FindCommand prepareEmailCommand(String userInput) {
        FindCommand command =
                new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a phone search.
     */
    private FindCommand preparePhoneCommand(String userInput) {
        FindCommand command =
                new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
        return command;
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} for a tag search.
     */
    private FindCommand prepareTagCommand(String userInput) {
        FindCommand command =
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new StorageStub());
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
