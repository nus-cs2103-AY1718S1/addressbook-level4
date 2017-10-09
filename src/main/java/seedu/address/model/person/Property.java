package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * A generic class that represents a property of a person. All properties of a person (including name, email, phone
 * and address) should inherit from this class.
 */
public class Property {
    /**
     * There should be no constraint to a property by default. The regular expression below will match everything
     * except for newline {@code \n}. However, these two strings should be overridden when this class is being
     * inherited by a more specific subclass.
     *
     * TODO: Investigate the performance drawback without using static final for these two strings.
     * TODO: Should we create an arrayList in PropertyManager to store all constraints?
     */
    private String messageConstraints = "There is no constraint for this property.";
    private String validationRegex = ".*";

    private final String propertyName;
    private String value;

    /**
     * Minimally, a property should at least have a name so that it can be distinguished from others. Meanwhile, it
     * should have a value validated by {@link #isValid(String)}.
     *
     * TODO: Investigate how to remove the duplication part of the constructors below.
     * TODO: Can we consider using non-static initialization block?
     *
     * @param name is the name (identifier) of this property.
     */
    public Property(String name, String value) throws IllegalValueException {
        requireNonNull(value);
        if (!isValid(value)) {
            throw new IllegalValueException(messageConstraints);
        }
        this.value = value;
        this.propertyName = name;
    }

    /**
     * Creates a property of the person with customized constraints.
     *
     * @param name is the name (identifier) of this property.
     * @param value is the value of this property.
     * @param messageConstraint is the message displayed when the input value of this property is invalid.
     * @param validationRegex is a regular expression used to validate the value of this property.
     */
    public Property(String name, String value, String messageConstraint, String validationRegex)
            throws IllegalValueException {
        // Sets the customized constraints first so that later can be used for validity check.
        this.messageConstraints = messageConstraint;
        this.validationRegex = validationRegex;

        requireNonNull(value);
        if (!isValid(value)) {
            throw new IllegalValueException(messageConstraints);
        }
        this.value = value;
        this.propertyName = name;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public boolean isValid(String test) {
        return test.matches(validationRegex);
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Property // instanceof handles nulls
                    && this.propertyName.equals(((Property) (other)).getPropertyName())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
