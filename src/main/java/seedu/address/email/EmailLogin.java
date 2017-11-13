package seedu.address.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.email.exceptions.EmailLoginInvalidException;

//@@author awarenessxz
/**
 * Handles how user logs into email
 */
public class EmailLogin {
    private static final Pattern GMAIL_FORMAT = Pattern.compile("^[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(@gmail.com)$");

    private String [] loginDetails;

    /** Creates an EmailLogin with an empty login detail */
    public EmailLogin() {
        loginDetails = new String[0];
    }

    /**
     * Saves user's login details
     *
     * @param loginDetails login email and password
     * @throws EmailLoginInvalidException if loginDetails is in wrong format
     */
    public void loginEmail(String [] loginDetails) throws EmailLoginInvalidException {
        //replace login details and ignore if login details is omitted.
        if (loginDetails.length != 0 && loginDetails.length == 2) {
            if (wrongUserEmailFormat(loginDetails)) {
                throw new EmailLoginInvalidException();
            } else {
                this.loginDetails = loginDetails;
            }
        }
    }

    /**
     * Checks if user's login details have been stored
     *
     * @return true if loginDetails is available
     */
    public boolean isUserLogin() {
        if (loginDetails.length != 2) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Verifies if the user is using a gmail account
     *
     * @return true if gmail account, false for everything else
     */
    private boolean wrongUserEmailFormat(String [] loginDetails) {
        if (loginDetails.length == 2) {
            final Matcher matcher = GMAIL_FORMAT.matcher(loginDetails[0].trim());
            if (!matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /** Returns user's login email */
    public String getEmailLogin() {
        if (loginDetails.length == 2) {
            return loginDetails[0];
        } else {
            return "";
        }
    }

    /** Returns user's login password */
    public String getPassword() {
        if (loginDetails.length == 2) {
            return loginDetails[1];
        } else {
            return "";
        }
    }

    /**
     * Resets the existing data of this {@code loginDetails} with an empty login
     */
    public void resetData() {
        loginDetails = new String[0];
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailLogin // instanceof handles nulls
                && this.loginDetailsEquals(((EmailLogin) other).loginDetails));
    }

    /** Returns true if both have the same loginDetails */
    private boolean loginDetailsEquals(String [] other) {
        if (loginDetails.length == other.length) {
            for (int i = 0; i < loginDetails.length; i++) {
                if (loginDetails[i] != other[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
