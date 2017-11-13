package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.util.function.Predicate;
import javax.mail.AuthenticationFailedException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.email.EmailTask;
import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.exceptions.EmailMessageEmptyException;
import seedu.address.email.exceptions.EmailRecipientsEmptyException;
import seedu.address.email.message.MessageDraft;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ImageInit;

//@@author awarenessxz
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code EmailCommand}.
 */
public class EmailCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Predicate<ReadOnlyPerson> predicateShowNoPerson = unused -> false;

    @Before
    public void setUp() {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
    }

    @After
    public void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void email_sendingFail_emailMessageEmptyException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingEmailMessageEmptyException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_EMPTY_INVALID);

        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("send");
        getEmailCommand("", "", loginDetails, task, modelStub).execute();
    }

    @Test
    public void email_sendingFail_emailLoginInvalidException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingEmailLoginInvalidException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_LOGIN_INVALID);

        EmailTask task = new EmailTask("send");

        //Non Gmail Login --> Throws exception
        String [] loginDetail1 = {"adam@yahoo.com", "password"};
        getEmailCommand("", "", loginDetail1, task, modelStub).execute();

        //Invalid Login Details Only accepts 2 fields --> Throws exception
        String [] loginDetail2 = {"adam@yahoo.com", "password", "hello"};
        getEmailCommand("", "", loginDetail2, task, modelStub).execute();

        //Invalid Login Details --> Throws exception
        String [] loginDetail3 = {"password", "adam@gmail.com"};
        getEmailCommand("", "", loginDetail3, task, modelStub).execute();
    }

    @Test
    public void email_sendingFail_emailRecipientsEmptyException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingEmailRecipientsEmptyException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_RECIPIENT_INVALID);

        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("send");
        getEmailCommand("", "", loginDetails, task, modelStub).execute();
    }

    @Test
    public void email_sendingFail_authenticationFailedException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingAuthenticationFailedException();

        thrown.expect(CommandException.class);
        thrown.expectMessage(EmailCommand.MESSAGE_AUTHENTICATION_FAIL);

        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("send");
        getEmailCommand("", "", loginDetails, task, modelStub).execute();
    }

    @Test
    public void execute_emailClear_success() throws Exception {
        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask("clear");
        EmailCommand emailCommand = getEmailCommand("message", "subject", loginDetails, task, model);

        String expectedMessage = String.format(EmailCommand.MESSAGE_SUCCESS, "cleared.");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getEmailManager());
        expectedModel.clearEmailDraft();

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emailDraft_success() throws Exception {
        String [] loginDetails = {"adam@gmail.com", "password"};
        EmailTask task = new EmailTask();
        EmailCommand emailCommand = getEmailCommand("message", "subject", loginDetails, task, model);

        String expectedMessage = String.format(EmailCommand.MESSAGE_SUCCESS,
                "drafted.\nYou are logged in to " + loginDetails[0]);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getEmailManager());
        expectedModel.draftEmail(new MessageDraft("message", "subject"));

        assertCommandSuccess(emailCommand, model, expectedMessage, expectedModel);
    }

    /** =========== Model Stub that always throws exceptions ============================ */

    /**
     * A Model stub that always throw a EmailMessageEmptyException when trying to send email.
     */
    private class ModelStubThrowingEmailMessageEmptyException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new EmailMessageEmptyException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }
    }

    /**
     * A Model stub that always throw a EmailLoginInvalidException when trying to send email.
     */
    private class ModelStubThrowingEmailLoginInvalidException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new EmailLoginInvalidException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }
    }

    /**
     * A Model stub that always throw a EmailRecipientsEmptyException when trying to send email.
     */
    private class ModelStubThrowingEmailRecipientsEmptyException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new EmailRecipientsEmptyException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            model.updateFilteredPersonList(predicateShowNoPerson);
            return model.getFilteredPersonList();
        }
    }

    /**
     * A Model stub that always throw a AuthenticationFailedException when trying to send email.
     */
    private class ModelStubThrowingAuthenticationFailedException extends ModelStub {
        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            throw new AuthenticationFailedException();
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            model.loginEmail(loginDetails);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            model.updateFilteredPersonList(predicateShowNoPerson);
            return model.getFilteredPersonList();
        }
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public String addImage(EmailAddress email, Photo photo) throws IOException {
            fail("This method should not be called.");
            return "";
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void backupAddressBook() {
            fail("This method should not be called.");
        }

        @Override
        public Email getEmailManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonListBirthdate() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void sortFilteredPersons(int sortOrder) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not not be called.");
        }

        @Override
        public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
            fail("This method should not be called.");
        }

        @Override
        public void sendEmail(MessageDraft message) throws EmailLoginInvalidException,
                EmailMessageEmptyException, EmailRecipientsEmptyException, AuthenticationFailedException {
            fail("This method should not be called.");
        }

        @Override
        public String getEmailStatus() {
            fail("This method should not be called.");
            return "";
        }

        @Override
        public void clearEmailDraft() {
            fail("This method should not be called.");
        }

        @Override
        public void draftEmail(MessageDraft message) {
            fail("This method should not be called.");
        }

    }

    /** ================== End of Model Stub ========================================= */

    /**
     * Generates a new EmailCommand with the details of the given message.
     */
    private EmailCommand getEmailCommand(String message, String subject,
                                         String [] loginDetails, EmailTask task, Model model) {
        EmailCommand command = new EmailCommand(message, subject, loginDetails, task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void equals() {
        String [] loginDetails = {"adam@gmail.com:password"};
        EmailTask task = new EmailTask("send");

        EmailCommand emailFirstCommand = new EmailCommand("Hello", "subject",
                loginDetails, task);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandcopy = new EmailCommand("Hello", "subject",
                loginDetails, task);
        assertTrue(emailFirstCommand.equals(emailFirstCommandcopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        String [] loginDetails2 = {"bernice@gmail.com:word123"};
        EmailCommand emailSecondCommand = new EmailCommand("ello", "ubject",
                loginDetails2, new EmailTask());
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }

}
