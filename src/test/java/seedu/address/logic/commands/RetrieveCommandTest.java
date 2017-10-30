//@@author duyson98

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.RetrieveCommand.MESSAGE_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code RetrieveCommand}.
 */
public class RetrieveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        RetrieveCommand command = new RetrieveCommand(predicate);

        // same value -> returns true
        assertTrue(command.equals(new RetrieveCommand(predicate)));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different type -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // different tag name -> returns false
        assertFalse(command.equals(new RetrieveCommand(new TagContainsKeywordPredicate(new Tag("family")))));
    }

    @Test
    public void execute_noPersonFound() throws Exception {
        StringJoiner joiner = new StringJoiner(", ");
        for (Tag tag: model.getAddressBook().getTagList()) {
            joiner.add(tag.toString());
        }
        String expectedMessage = (MESSAGE_NOT_FOUND + "\n"
                + "You may want to refer to the following existing tags: "
                + joiner.toString());
        RetrieveCommand command = prepareCommand("thisTag");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multiplePersonsFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        RetrieveCommand command = prepareCommand("retrieveTester");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code RetrieveCommand}.
     */
    private RetrieveCommand prepareCommand(String userInput) throws Exception {
        if (userInput.isEmpty()) {
            RetrieveCommand command = new RetrieveCommand(new TagContainsKeywordPredicate(new Tag(userInput)));
        }
        RetrieveCommand command = new RetrieveCommand(new TagContainsKeywordPredicate(new Tag(userInput)));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    public void assertCommandSuccess(RetrieveCommand command, String expectedMessage,
                                     List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
