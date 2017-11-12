package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in the address book. Tag can only be FLAMMABLE, FROZEN, HEAVY or FRAGILE
 * Guarantees: immutable;
 */
public enum Tag {

    FLAMMABLE, FROZEN, HEAVY, FRAGILE;

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags can only be FLAMMABLE, FROZEN, HEAVY or FRAGILE";

    /**
     * Validates given tag tagName.
     * @throws IllegalValueException if the given tag tagName string is invalid.
     */
    public static Tag getInstance(String tagName) throws IllegalValueException {
        requireNonNull(tagName);
        String trimmedName = tagName.trim();

        String upperCaseName = trimmedName.toUpperCase();

        switch (upperCaseName) {
        case "FROZEN":
            return FROZEN;

        case "FLAMMABLE":
            return FLAMMABLE;

        case "HEAVY":
            return HEAVY;

        case "FRAGILE":
            return FRAGILE;

        default:
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
    }

    /**
     * Format state as text for viewing.
     */
    public String getFormattedString() {
        return '[' + this.toString() + ']';
    }
}
