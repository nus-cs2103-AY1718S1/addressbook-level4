package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
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

/**
 * Contains integration tests (interaction with the Model) for {@code FindByEmailCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FindPersonDescriptor first = new FindPersonDescriptor();
        FindPersonDescriptor second = new FindPersonDescriptor();
        second.setName("Kruz Bob");
        second.setAddress("10th");
        second.setEmail("anna@example.com");

        //find with empty descriptor
        FindCommand findFirstCommandTrue = new FindCommand(true, first);
        FindCommand findFirstCommandFalse = new FindCommand(false, first);

        //find with non-empty descriptor
        FindCommand findSecondCommandTrue = new FindCommand(true, second);
        FindCommand findSecondCommandFalse = new FindCommand(false, second);

        //empty descriptor not the same type will not return same find
        assertFalse(findFirstCommandTrue.equals(findFirstCommandFalse));
        assertFalse(findSecondCommandTrue.equals(findSecondCommandFalse));

        // same object -> returns true
        assertTrue(findFirstCommandTrue.equals(findFirstCommandTrue));

        // same values -> returns true
        FindCommand findSecondCommandCopy = new FindCommand(true, second);
        assertTrue(findSecondCommandTrue.equals(findSecondCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommandTrue.equals(1));

        // null -> returns false
        assertFalse(findFirstCommandTrue.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommandTrue.equals(findSecondCommandTrue));
        assertFalse(findFirstCommandFalse.equals(findSecondCommandFalse));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        String[] userInput = {"OR", "  ", " "};
        FindCommand command = prepareCommand(userInput);
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String[] userInput = {"OR", "ali", "heinz@example.com werner@example.com"};
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindCommand command = prepareCommand(userInput);
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindByEmailCommand}.
     */
    private FindCommand prepareCommand(String[] userInput) {
        FindPersonDescriptor personDescriptor = new FindPersonDescriptor();
        personDescriptor.setName(userInput[1]);
        personDescriptor.setEmail(userInput[2]);
        boolean type = (userInput[0].equals("AND")) ? true : userInput[0].equals("OR") ? false : null;
        FindCommand command = new FindCommand(type, personDescriptor);
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
