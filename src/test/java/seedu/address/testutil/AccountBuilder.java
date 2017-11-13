//@@author cqhchan
package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;
/**
 *
 */
public class AccountBuilder {

    public static final String DEFAULT_NAME = "private";
    public static final String DEFAULT_PASSWORD = "password";

    private Account account;

    public AccountBuilder() {
        try {
            Username defaultName = new Username(DEFAULT_NAME);
            Password defaultPassword = new Password(DEFAULT_PASSWORD);
            this.account = new Account(defaultName, defaultPassword);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default person's values are invalid.");
        }
    }

    /**
     * Initializes the AccountBuilder with the data of {@code personToCopy}.
     */
    public AccountBuilder(ReadOnlyAccount accountToCopy) {
        this.account = new Account(accountToCopy);
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withUsername(String username) {
        try {
            this.account.setUsername(new Username(username));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withPassword(String password) {
        try {
            this.account.setPassword(new Password(password));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Password is expected to be unique.");
        }
        return this;
    }


    public Account build() {
        return this.account;
    }


}
