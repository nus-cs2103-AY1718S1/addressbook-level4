//@@author srishag
package seedu.address.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.GoogleAuthException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class SendEmailCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private final AddressBookParser parser = new AddressBookParser();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /**
     * Checks if login is authenticated. In this case it is not, GoogleAuthException is thrown.
     */
    @Test
    public void executeLogin() throws Exception {
        LoginCommand loginCommand = new LoginCommand();
        CommandResult commandResult = loginCommand.execute();
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SendEmailCommand command = new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY);
        SendEmailCommand sameCommand = new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY);
        SendEmailCommand differentCommand = new SendEmailCommand(INDEX_SECOND_PERSON, VALID_EMAIL_SUBJECT,
                VALID_EMAIL_BODY);

        assertTrue(command.equals(sameCommand));

        assertTrue(command.equals(command));

        assertFalse(command == null);

        assertFalse(command == differentCommand);
    }

    /**
     * Executes a {@code SendEmailCommand} with the given {@code index, subject, body},
     * and checks that a {@code CommandException or GoogleAuthException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String subject, String body, String expectedMessage) {
        SendEmailCommand sendEmailCommand = prepareCommand(index, subject, body);

        try {
            sendEmailCommand.execute();
            Assert.fail("The expected exception was not thrown.");
        } catch (CommandException | GoogleAuthException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    /**
     * Returns a {@code SendEmailCommand} with parameters {@code index, subject, body}.
     */
    private SendEmailCommand prepareCommand(Index index, String subject, String body) {
        SendEmailCommand sendEmailCommand = new SendEmailCommand(index, subject, body);
        sendEmailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sendEmailCommand;
    }
}

