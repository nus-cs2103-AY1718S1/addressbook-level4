package seedu.address.model.customField;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author LuLechuan
/**
 * Represents a CustomField in the address book.
 * Guarantees: immutable.
 */
public class CustomField {

    public static final String MESSAGE_CUSTOM_FIELD_CONSTRAINTS = "CustomFields names should be alphanumeric";

    public final String customFieldName;
    private String customFieldValue;

    /**
     * Validates given customFieldName and customFieldValue.
     *
     * @throws IllegalValueException if the given customFieldName or customFieldValue string is invalid.
     */
    public CustomField(String customFieldName, String customFieldValue) throws IllegalValueException {
        requireNonNull(customFieldName);
        requireNonNull(customFieldValue);

        this.customFieldName = customFieldName;
        this.customFieldValue = customFieldValue;
    }

    /**
     *  Returns custom field value of this CustomField
     *
     * @return customFieldValue
     */
    public String getCustomFieldValue() {
        return customFieldValue;
    }

    /**
     *  Sets a new custom field value for this CustomField
     *
     * @param newCustomFieldValue
     */
    public void setCustomFieldValue(String newCustomFieldValue) {
        this.customFieldValue = newCustomFieldValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CustomField // instanceof handles nulls
                && this.customFieldName.equals(((CustomField) other).customFieldName))
                && this.customFieldValue.equals(((CustomField) other).customFieldValue); // state check
    }

    @Override
    public int hashCode() {
        return customFieldName.hashCode() + customFieldValue.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return customFieldValue;
    }

}
