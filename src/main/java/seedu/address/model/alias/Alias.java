//@@author keloysiusmak
package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.Serializable;
import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Alias implements Serializable {

    public static final String MESSAGE_ALIAS_CONSTRAINTS =
            "Aliases should only contain alphanumeric characters and spaces, and it should not be blank";
    private String aliasCommand;
    private String aliasString;

    /**
     * Every field must be present and not null.
     */

    public Alias() {
        this.aliasCommand = null;
        this.aliasString = null;
    }

    public Alias(String aliasCommand, String aliasString) {
        try {
            requireAllNonNull(aliasCommand, aliasString);
            if (!(aliasCommand instanceof String && aliasString instanceof String)) {
                throw new IllegalValueException(MESSAGE_ALIAS_CONSTRAINTS);
            }
            this.aliasCommand = aliasCommand;
            this.aliasString = aliasString;
        } catch (IllegalValueException e) {
            ;
        }
    }

    public String getAlias() {
        return aliasString;
    }

    public void setAlias(String alias) {
        this.aliasString = (requireNonNull(alias));
    }

    public String getCommand() {
        return aliasCommand;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Alias// instanceof handles nulls
                && this.aliasString == ((Alias) other).aliasString);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(aliasCommand, aliasString);
    }

}
