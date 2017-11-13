package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_LOGIN_LENGTH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_JOHN_EMAILTESTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_LOGIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MESSAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOHN_EMAILTESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.function.Predicate;

import javax.mail.AuthenticationFailedException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.email.EmailTask;
import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.exceptions.EmailMessageEmptyException;
import seedu.address.email.exceptions.EmailRecipientsEmptyException;
import seedu.address.email.message.MessageDraft;
import seedu.address.email.message.ReadOnlyMessageDraft;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.ImageInit;
import seedu.address.testutil.PersonBuilder;

//@@author awarenessxz
public class EmailCommandSystemTest extends AddressBookSystemTest {

    private static final String EMAIL_SUCCESSFULLY_DRAFTED = "drafted.\n";
    private static final String EMAIL_SUCCESSFULLY_CLEARED = "cleared.";
    private static final String EMAIL_NOT_LOGIN_STATUS = "You are not logged in to any Gmail account.";
    private static final String EMAIL_LOGIN_STATUS = "You are logged in to %1$s";
    private static final String EMAIL_COMMAND_SEND = " " + PREFIX_EMAIL_TASK + "send";
    private static final String EMAIL_COMMAND_CLEAR = " " + PREFIX_EMAIL_TASK + "clear";
    private Predicate<ReadOnlyPerson> predicateShowNoPerson = unused -> false;

    @BeforeClass
    public static void setup() throws Exception {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() throws Exception {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void sendEmail() throws Exception {
        String command = "";
        MessageDraft message = new MessageDraft();
        String [] loginDetails = new String[0];
        EmailTask task = new EmailTask();

        /**
         * Case: Email Command without any arguments --> invalid
         */
        command = EmailCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with invalid message --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_MESSAGE + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with empty subject --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_SUBJECT + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with empty message (no message and subject) --> invalid
         */
        command = EmailCommand.COMMAND_WORD + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, EmailCommand.MESSAGE_EMPTY_INVALID);

        /**
         * Case: Email Command to send email with invalid login parameters --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_LOGIN_LENGTH + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to send email with a non gmail account --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_MESSAGE + EMAIL_SUBJECT + INVALID_EMAIL_LOGIN + EMAIL_COMMAND_SEND;
        assertCommandFailure(command, EmailCommand.MESSAGE_LOGIN_INVALID);

        /**
         * Case: Email Commmand to draft email with only message parameter --> success
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_MESSAGE;
        message = new MessageDraft(VALID_EMAIL_MESSAGE, "");
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_DRAFTED
                + EMAIL_NOT_LOGIN_STATUS);

        /**
         * Case: Email Commmand to draft email with empty message parameter --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_MESSAGE;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Commmand to draft email with only subject parameter --> success
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_SUBJECT;
        message = new MessageDraft("", VALID_EMAIL_SUBJECT);
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_DRAFTED
                + EMAIL_NOT_LOGIN_STATUS);

        /**
         * Case: Email Commmand to draft email with empty subject parameter --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_SUBJECT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));


        /**
         * Case: Email Commmand to draft email with login parameter --> success
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_LOGIN;
        message = new MessageDraft();
        loginDetails = VALID_EMAIL_LOGIN.split(":");
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_DRAFTED
                + String.format(EMAIL_LOGIN_STATUS, loginDetails[0]));

        /**
         * Case: Email Commmand to draft email with invalid login parameter --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_LOGIN_LENGTH;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));

        /**
         * Case: Email Command to draft email with a non gmail account --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + INVALID_EMAIL_LOGIN;
        assertCommandFailure(command, EmailCommand.MESSAGE_LOGIN_INVALID);

        /**
         * Case: Email Command to send email with an empty last display list --> invalid
         **/
        command = EmailCommand.COMMAND_WORD + EMAIL_MESSAGE + EMAIL_SUBJECT + EMAIL_LOGIN + EMAIL_COMMAND_SEND;
        assertCommandFailureEmptyList(command, EmailCommand.MESSAGE_RECIPIENT_INVALID);

        /**
         * Case: Email Command to clear current draft --> success
         */
        command = EmailCommand.COMMAND_WORD + EMAIL_COMMAND_CLEAR;
        message = new MessageDraft();
        loginDetails = VALID_EMAIL_LOGIN.split(":");
        task.setTask("clear");
        assertCommandSucess(command, message, loginDetails, task, EMAIL_SUCCESSFULLY_CLEARED);
    }

    private void setupModel(Model expectedModel) throws Exception {
        /**
         *  Set up Model such the list is empty
         */
        ReadOnlyPerson toAdd = new PersonBuilder().withName(VALID_NAME_JOHN_EMAILTESTER)
                .withEmailAddress(VALID_EMAIL_JOHN_EMAILTESTER).build();
        String findCommand = FindCommand.COMMAND_WORD + " " + PREFIX_NAME + VALID_NAME_JOHN_EMAILTESTER;
        ModelHelper.setFilteredList(expectedModel, toAdd);
        executeCommand(findCommand);
    }

    /**
     * Extract Email from last display person {@code lastshownList} into an InternetAddresss[] for sending email
     *
     * @params: last shown display person list
     * @return: list of internet email address
     **/
    private InternetAddress[] extractEmailFromContacts(List<ReadOnlyPerson> lastShownList) throws AddressException {
        InternetAddress [] recipientsEmail = new InternetAddress[lastShownList.size()];
        try {
            for (int i = 0; i < lastShownList.size(); i++) {
                recipientsEmail[i] = new InternetAddress(lastShownList.get(i).getEmailAddress().value);
            }
        } catch (AddressException e) {
            throw new AddressException();
        }
        return recipientsEmail;
    }

    /**
     * Executes the {@code EmailCommand} that sends or draft an email {@code message} and verifies the command box
     * displays the email status results of executing the {@code EmailCommand} and the model related components equal
     * to the current model with the send command composed with email message {@code message}. These verifications are
     * done by {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String,String,Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes, the
            * browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model).
            */
    private void assertCommandSucess(String command, MessageDraft message, String [] loginDetails,
                                     EmailTask task, String status) throws Exception {
        Model expectedModel = getModel();
        message.setRecipientsEmail(extractEmailFromContacts(expectedModel.getFilteredPersonList()));

        try {
            //Set up Email Details
            expectedModel.loginEmail(loginDetails);
            switch(task.getTask()) {
            case "send":
                expectedModel.sendEmail(message);
                break;
            case "clear":
                expectedModel.clearEmailDraft();
                break;
            default:
                expectedModel.draftEmail(message);
                break;
            }
        } catch (EmailLoginInvalidException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_LOGIN_INVALID);
        } catch (EmailMessageEmptyException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_EMPTY_INVALID);
        } catch (EmailRecipientsEmptyException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_RECIPIENT_INVALID);
        } catch (AuthenticationFailedException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_AUTHENTICATION_FAIL);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException(EmailCommand.MESSAGE_FAIL_UNKNOWN);
        }
        String expectedResultMessage = String.format(EmailCommand.MESSAGE_SUCCESS, status);

        assertCommandSuccess(command, expectedModel, expectedModel.getEmailManager().getEmailDraft(),
                expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Message, String [], boolean)} except
     * that the result display box displays {@code expectedResultMessage}, the model related components equal to
     * {@code expectedModel} and the email tab displays {@code expectedMessage}.
     * @see EmailCommandSystemTest#
     * assertCommandSuccess(String, MessageDraft, String [], boolean)
     */
    private void assertCommandSuccess(String command, Model expectedModel, ReadOnlyMessageDraft expectedDraft,
                                      String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertEmailDisplayExpected(expectedDraft.getMessage(), expectedDraft.getSubject(),
                expectedDraft.getRecipientsEmailtoString(), 1);
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) throws Exception {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, String)} except
     * that the displayed person list is empty.
     * @see EmailCommandSystemTest#
     * assertCommandSuccess(String, String)
     */
    private void assertCommandFailureEmptyList(String command, String expectedResultMessage) throws Exception {
        Model expectedModel = getModel();
        setupModel(expectedModel);

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
