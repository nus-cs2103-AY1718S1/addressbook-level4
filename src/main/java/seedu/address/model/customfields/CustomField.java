package seedu.address.model.customfields;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;
//@@author syy94
/**
 * Represents a field as created by the user in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidField(String)}
 */
public class CustomField {
    public static final String MESSAGE_FIELD_CONSTRAINTS =
            "Custom fields should be 2 alphanumeric strings separated by ':'";
    public static final String FIELD_VALIDATION_REGEX = "(\\p{Alnum}+ *)+: *(\\p{Alnum}+ *)+";

    public final String key;
    public final String value;

    public CustomField(String field) throws IllegalValueException {
        requireAllNonNull(field);
        final String trimmedField = field.trim();
        if (!isValidField(trimmedField)) {
            throw new IllegalValueException(MESSAGE_FIELD_CONSTRAINTS);
        }
        final String[] fieldData = trimmedField.split(":", 2);
        requireAllNonNull(fieldData[0], fieldData[1]);
        this.key = fieldData[0].trim().toUpperCase();
        this.value = fieldData[1].trim();
    }

    /**
     * Returns if a given string is a valid custom field.
     */
    public static boolean isValidField(String test) {
        return test.matches(FIELD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof CustomField)) {
            return false;
        }

        // state check
        final CustomField other = (CustomField) obj;
        return key.equals(other.key) && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }

    /**
     * returns a String representation of custom fields as a sentence.
     *
     * @see seedu.address.model.person.PersonContainsKeywordsPredicate#test(ReadOnlyPerson)
     */
    public String asData() {
        return key + " " + value;
    }
}
