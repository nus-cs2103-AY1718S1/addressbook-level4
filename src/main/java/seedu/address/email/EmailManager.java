package seedu.address.email;

import java.util.logging.Logger;

import javax.mail.AuthenticationFailedException;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.email.exceptions.EmailLoginInvalidException;
import seedu.address.email.exceptions.EmailMessageEmptyException;
import seedu.address.email.exceptions.EmailRecipientsEmptyException;
import seedu.address.email.message.MessageDraft;
import seedu.address.email.message.ReadOnlyMessageDraft;

//@@author awarenessxz
/**
 * Handles how email are sent out of the application.
 */
public class EmailManager extends ComponentManager implements Email {
    private static final Logger logger = LogsCenter.getLogger(EmailManager.class);

    private static final String STATUS_CLEARED = "cleared.";
    private static final String STATUS_DRAFTED = "drafted.\n";
    private static final String STATUS_SENT = "sent ";
    private static final String STATUS_LOGIN_FAIL = "You are not logged in to any Gmail account.";
    private static final String STATUS_LOGIN_SENT = "using %1$s";
    private static final String STATUS_LOGIN_SUCCESS = "You are logged in to %1$s";

    private final EmailLogin emailLogin;
    private final EmailCompose emailCompose;
    private final EmailSend emailSend;

    private String emailStatus;
    private String emailLoginStatus;

    /**
     * Initializes a EmailManager with new EmailLogin, EmailCompose and EmailSend
     */
    public EmailManager() {
        logger.fine("Initializing Default Email component");

        emailLogin = new EmailLogin();
        emailCompose = new EmailCompose();
        emailSend = new EmailSend();
        emailStatus = "";
        emailLoginStatus = STATUS_LOGIN_FAIL;
    }

    @Override
    public void composeEmail(MessageDraft message) {
        emailCompose.composeEmail(message);
        emailStatus = STATUS_DRAFTED;
    }

    @Override
    public ReadOnlyMessageDraft getEmailDraft() {
        return emailCompose.getMessage();
    }

    @Override
    public String getEmailStatus() {
        return emailStatus + emailLoginStatus;
    }

    @Override
    public void sendEmail() throws EmailLoginInvalidException, EmailMessageEmptyException,
            EmailRecipientsEmptyException, AuthenticationFailedException {
        logger.info("-------------------[Sending Email] ");

        emailSend.sendEmail(emailCompose, emailLogin);

        //reset the email draft after email have been sent
        emailStatus = STATUS_SENT;
        emailLoginStatus = String.format(STATUS_LOGIN_SENT, emailLogin.getEmailLogin());
        resetData();
    }

    @Override
    public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
        emailLogin.loginEmail(loginDetails);
        if (emailLogin.isUserLogin()) {
            emailLoginStatus = String.format(STATUS_LOGIN_SUCCESS, emailLogin.getEmailLogin());
        } else {
            emailLoginStatus = STATUS_LOGIN_FAIL;
        }
    }

    /**
     * Returns true if the emailLogin contains user's login details
     */
    @Override
    public boolean isUserLogin() {
        return emailLogin.isUserLogin();
    }


    @Override
    public void clearEmailDraft() {
        resetData();
        emailStatus = STATUS_CLEARED;
        emailLoginStatus = "";
    }

    /**
     * Resets the existing data of this {@code emailCompose} and this {@code emailLogin}
     */
    private void resetData() {
        emailCompose.resetData();
        emailLogin.resetData();
    }

    @Override
    public boolean equals(Object obj) {

        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof EmailManager)) {
            return false;
        }

        // state check
        EmailManager other = (EmailManager) obj;
        return this.emailCompose.equals(((EmailManager) obj).emailCompose)
                && this.emailLogin.equals(((EmailManager) obj).emailLogin);
    }

}
