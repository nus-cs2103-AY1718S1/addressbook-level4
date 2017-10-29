package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.FilterCommand.MESSAGE_FILTER_ACKNOWLEDGEMENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jelneo
/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";
    public static final String[] SAMPLE_TAGS = "violent friendly".split(ONE_OR_MORE_SPACES_REGEX);
    public static final String[] SAMPLE_TAG = {"cooperative"};

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        List<String> firstTagList = Arrays.asList(SAMPLE_TAGS);
        List<String> secondTagList = Arrays.asList(SAMPLE_TAG);

        FilterCommand filterFirstCommand = new FilterCommand(firstTagList);
        FilterCommand filterSecondCommand = new FilterCommand(secondTagList);

        // same object
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstTagList);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different values
        assertFalse(filterFirstCommand.equals(filterSecondCommand));

        // different types
        assertFalse(filterFirstCommand.equals(1));

        // null vlaue
        assertFalse(filterFirstCommand.equals(null));
    }

    @Test
    public void execute_noTags_noPersonFound() {
        String expectedMessage =  String.format(MESSAGE_FILTER_ACKNOWLEDGEMENT, Arrays.asList(""),
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0));
        FilterCommand filterCommand = prepareCommand("      ");
        assertCommandSuccess(filterCommand, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_singleTag_noPersonFound() {
        String expectedMessage =  String.format(MESSAGE_FILTER_ACKNOWLEDGEMENT, Arrays.asList("safsaf2sf"),
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0));
        FilterCommand filterCommand = prepareCommand("safsaf2sf");
        assertCommandSuccess(filterCommand, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_singleTag_multiplePersonsFound() {
        String expectedMessage =  String.format(MESSAGE_FILTER_ACKNOWLEDGEMENT, Arrays.asList("violent"),
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2));
        FilterCommand filterCommand = prepareCommand("violent");
        assertCommandSuccess(filterCommand, expectedMessage, Arrays.asList(DANIEL, ELLE));
    }

    @Test
    public void execute_multipleTags_multiplePersonsFound() {
        String expectedMessage =  String.format(MESSAGE_FILTER_ACKNOWLEDGEMENT, Arrays.asList("friendly", "tricky"),
                String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4));
        FilterCommand filterCommand = prepareCommand("friendly tricky");
        assertCommandSuccess(filterCommand, expectedMessage, Arrays.asList(ALICE, BENSON, DANIEL, FIONA));
    }

    /**
     * Parses {@code userInput} into a {@code FilterCommand}.
     */
    private FilterCommand prepareCommand(String userInput) {
        FilterCommand command =
                new FilterCommand(Arrays.asList(userInput.split(ONE_OR_MORE_SPACES_REGEX)));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.executeUndoableCommand();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expectedList, model.getFilteredPersonList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }
}
