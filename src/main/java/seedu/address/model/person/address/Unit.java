package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the unit in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnit(String)}
 */
public class Unit {
    public static final String MESSAGE_UNIT_CONSTRAINTS =
            "Unit must begin with '#' and be followed by 2 numeric strings separated by '-'";
    private static final String UNIT_VALIDATION_REGEX = "#[\\d\\.]+-[\\d\\.]+";

    public final String value;

    public Unit(String unit) throws IllegalValueException {
        requireNonNull(unit);
        String trimmedUnit = unit.trim();

        if (!isValidUnit(trimmedUnit)) {
            throw new IllegalValueException(MESSAGE_UNIT_CONSTRAINTS);
        }
        this.value = trimmedUnit;
    }

    /**
     * Returns true if a given string is a valid unit in an address.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(UNIT_VALIDATION_REGEX);
    }
}
