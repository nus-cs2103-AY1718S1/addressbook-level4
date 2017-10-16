package seedu.address.email;

import seedu.address.commons.exceptions.NotAnEmailException;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;

/**
 * API of Email component
 */

public interface Email {
    /**
     * Returns a log-in session object that can be used to send and receive email
     * @param email The email address that needs to be logged in
     * @param password Password
     * @return A Session object
     * @throws NoSuchProviderException if the email provider does not exist
     * @throws NotAnEmailException if the given email is not valid
     */
    public Session login(String email, String password) throws NotAnEmailException, NoSuchProviderException;

    /**
     *
     * @param session An Session object that represents a logged in email
     * @param recipient Recipient's email
     * @param title Title of the email
     * @param message Message to be included in email
     * @throws NotAnEmailException if the given emails is/are not valid
     * @throws MessagingException if the emails were failed to send
     */
    public void sendEmail(Session session, String recipient, String title, String message) throws NotAnEmailException, MessagingException;
}
