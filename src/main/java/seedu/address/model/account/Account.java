//@@author cqhchan
package seedu.address.model.account;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 */
public class Account implements ReadOnlyAccount {
    private ObjectProperty<Username> username;
    private ObjectProperty<Password> password;

    /**
     * Every field must be present and not null.
     */
    public Account(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = new SimpleObjectProperty<>(username);
        this.password = new SimpleObjectProperty<>(password);
    }

    public Account(ReadOnlyAccount source) {
        this(source.getUsername(), source.getPassword());
    }


    public void setUsername(Username username) {
        this.username.set(requireNonNull(username));
    }

    @Override
    public ObjectProperty<Username> usernameProperty() {
        return username;
    }

    @Override
    public Username getUsername() {
        return username.get();
    }

    public void setPassword(Password password) {
        this.password.set(requireNonNull(password));
    }

    @Override
    public ObjectProperty<Password> passwordProperty() {
        return password;
    }

    @Override
    public Password getPassword() {
        return password.get();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyAccount // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyAccount) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
