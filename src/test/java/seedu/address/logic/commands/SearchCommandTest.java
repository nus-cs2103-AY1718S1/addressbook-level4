//@@author zenghou
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.ALICE;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.DetailsContainKeyphrasePredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.user.UserCreds;
import seedu.address.model.user.UserPrefs;


public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());

    //@@author zenghou-unused
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());
        SearchCommand searchCommand = new SearchCommand(new DetailsContainKeyphrasePredicate("testing one"));

        searchCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(searchCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
    //@@author

    @Test
    public void equals() {
        SearchCommand searchCommandOne = new SearchCommand(new DetailsContainKeyphrasePredicate("testing one"));
        SearchCommand searchCommandTwo = new SearchCommand(new DetailsContainKeyphrasePredicate("testing two"));

        // same object -> returns true
        assertTrue(searchCommandOne.equals(searchCommandOne));
        assertTrue(searchCommandOne.equals(new SearchCommand(new DetailsContainKeyphrasePredicate("testing one"))));

        // different object -> returns false
        assertFalse(searchCommandOne.equals(searchCommandTwo));

        // different type -> returns false
        assertFalse(searchCommandOne.equals(1));

        // null -> returns false
        assertFalse(searchCommandOne.equals(null));
    }

    @Test
    public void execute_keyPhrase_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        SearchCommand command = prepareCommand("Alice Pauline");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE));
    }

    /**
     * Parses {@code userInput} into a {@code SearchCommand}.
     */
    private SearchCommand prepareCommand(String userInput) {
        model.getUserCreds().validateCurrentSession(); // validate user
        String preppedInput = userInput.trim();
        SearchCommand command =
                new SearchCommand(new DetailsContainKeyphrasePredicate(preppedInput));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SearchCommand command, String expectedMessage, List<ReadOnlyPerson> expected) {
        model.getUserCreds().validateCurrentSession(); // validate user
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        try {
            CommandResult commandResult = command.execute();

            assertEquals(expectedMessage, commandResult.feedbackToUser);
            assertEquals(expected, model.getFilteredPersonList());
            assertEquals(expectedAddressBook, model.getAddressBook());
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }
}
