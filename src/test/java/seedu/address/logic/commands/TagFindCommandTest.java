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
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.TagMatchingKeywordPredicate;

//@@author ZhangH795

/**
 * Contains integration tests (interaction with the Model) for {@code TagFindCommand}.
 */
public class TagFindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());

    @Test
    public void equals() {
        boolean looseFind = true;
        TagMatchingKeywordPredicate firstPredicate =
                new TagMatchingKeywordPredicate("first", looseFind);
        TagMatchingKeywordPredicate secondPredicate =
                new TagMatchingKeywordPredicate("second", looseFind);

        TagFindCommand findFirstCommand = new TagFindCommand(firstPredicate);
        TagFindCommand findSecondCommand = new TagFindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        TagFindCommand findFirstCommandCopy = new TagFindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand == null);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void executeZeroKeywordNoPersonFound() {
        boolean looseFind = true;
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        TagFindCommand command = prepareCommand(" ", looseFind);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeSinglePersonFound() {
        boolean looseFind = true;
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        TagFindCommand command = prepareCommand("owesMoney", looseFind);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void executeMultiplePersonsFound() {
        boolean looseFind = true;
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        TagFindCommand command = prepareCommand("Friends", looseFind);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code TagFindCommand}.
     */
    private TagFindCommand prepareCommand(String userInput, boolean looseFind) {
        TagFindCommand command =
                new TagFindCommand(new TagMatchingKeywordPredicate(userInput, looseFind));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(TagFindCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
