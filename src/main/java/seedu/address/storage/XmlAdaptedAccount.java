//@@author cqhchan
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.Username;

/**
 * JAXB-friendly version of the Account.
 */
public class XmlAdaptedAccount {

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedAccount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAccount() {}


    /**
     * Converts a given Account into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAccount
     */
    public XmlAdaptedAccount(ReadOnlyAccount source) {
        username = source.getUsername().fullName;
        password = source.getPassword().value;

    }

    /**
     * Converts this jaxb-friendly adapted account object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account
     */
    public Account toModelType() throws IllegalValueException {
        final Username username = new Username(this.username);
        final Password password = new Password(this.password);
        return new Account(username, password);
    }
}
