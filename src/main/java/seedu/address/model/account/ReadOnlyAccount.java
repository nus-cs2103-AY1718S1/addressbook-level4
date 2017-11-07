//@@author cqhchan
package seedu.address.model.account;

import javafx.beans.property.ObjectProperty;

/**
 *
 */
public interface ReadOnlyAccount {

    ObjectProperty<Username> usernameProperty();
    Username getUsername();
    ObjectProperty<Password> passwordProperty();
    Password getPassword();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyAccount other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getUsername().equals(this.getUsername()) // state checks here onwards
                && other.getPassword().equals(this.getPassword()));

    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Password: ")
                .append(getPassword());
        return builder.toString();
    }

}

