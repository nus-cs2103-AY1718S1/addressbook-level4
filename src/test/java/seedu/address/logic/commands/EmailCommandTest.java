package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//@@author RonakLakhotia
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EmailCommand}.
 */

public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {


        EmailCommand emailCommandFirst = new EmailCommand("friends", "party");
        EmailCommand emailCommandCommandSecond = new EmailCommand("owesMoney", "debt");

        // same object -> returns true
        assertTrue(emailCommandFirst.equals(emailCommandFirst));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand("friends", "meeting");
        assertTrue(emailFirstCommandCopy.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailCommandFirst.equals(1));

        // null -> returns false
        assertFalse(emailCommandFirst.equals(null));

        // different person -> returns false
        assertFalse(emailCommandFirst.equals(emailCommandCommandSecond));
    }
    @Test
    public void valid_subjectForBrowser() {

        String tag = "friends";
        String subject = "party with friends";
        String modifiedSubject = "party+with+friends";
        EmailCommand emailCommand = new EmailCommand(tag, subject);
        String check = emailCommand.getSubjectForBrowser(subject);
        assertTrue(modifiedSubject.equals(check));


    }
    @Test
    public void invalid_tagForEmail() {

        String tagSecond = "fr+";
        EmailCommand emailCommand = prepareCommand(tagSecond, "party");
        assertCommandFailure(emailCommand, model, EmailCommand.MESSAGE_NOT_EXISTING_TAGS);

    }
    @Test
    public void validArgsSuccess() {

        String expectedMessage = String.format(EmailCommand.MESSAGE_EMAIL_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String tag = "friends";
        String friends = "party";
        EmailCommand emailCommand = prepareCommand(tag, friends);
        assertCommandSuccessEmail(emailCommand, model, expectedMessage, expectedModel);
    }
    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(String tag, String subject) {

        EmailCommand emailCommand = new EmailCommand(tag, subject);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccessEmail(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {

        assertEquals(expectedMessage, EmailCommand.MESSAGE_EMAIL_SUCCESS);;
        assertEquals(expectedModel, actualModel);

    }
}
