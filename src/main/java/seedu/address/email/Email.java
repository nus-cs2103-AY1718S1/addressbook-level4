package seedu.address.email;

import javax.mail.AuthenticationFailedException;

import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.exceptions.EmailMessageEmptyException;
import seedu.address.email.exceptions.EmailRecipientsEmptyException;
import seedu.address.email.message.MessageDraft;
import seedu.address.email.message.ReadOnlyMessageDraft;

//@@author awarenessxz
/**
 * API of Email component
 */
public interface Email {

    /**
     * Logins to Email Component with given login details
     *
     * @throws EmailLoginInvalidException if login fails
     */
    void loginEmail(String [] loginDetails) throws EmailLoginInvalidException;

    /** Returns true if user is Log in */
    boolean isUserLogin();

    /** Creates Email Draft with given message */
    void composeEmail(MessageDraft message);

    /** Views Email Draft */
    ReadOnlyMessageDraft getEmailDraft();

    /** Views Email Send Status */
    String getEmailStatus();

    /** Clears Email Draft content */
    void clearEmailDraft();

    /**
     * Sends Email Draft to all users
     *
     * @throws EmailLoginInvalidException if login details is empty
     * @throws EmailMessageEmptyException if message is empty
     * @throws EmailRecipientsEmptyException if recipients list is empty
     * @throws AuthenticationFailedException if gmail account can't be logged in
     */
    void sendEmail() throws EmailLoginInvalidException, EmailMessageEmptyException,
            EmailRecipientsEmptyException, AuthenticationFailedException;

}
