package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.parcel.Parcel;

//@@author kennard123661
/**
 * Represents a Tag in the address book.
 * It can only be one of these values: FLAMMABLE, FROZEN, HEAVY or FRAGILE
 * Guarantees: immutable;
 *
 * {@code Tag.FLAMMABLE} means that the {@link Parcel} contents are highly flammable.
 * {@code Tag.FROZEN} means that the {@link Parcel} contents are temperature sensitive and need to be kept cold.
 * {@code Tag.HEAVY} means that the {@link Parcel} contents are heavy and require additional manpower to deliver.
 * {@code Tag.FRAGILE} means that the {@link Parcel} contents are fragile and require additional care.
 */
public enum Tag {

    FLAMMABLE, FROZEN, HEAVY, FRAGILE;

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags can only be FLAMMABLE, FROZEN, HEAVY or FRAGILE";

    /**
     * Validates given tag.
     *
     * @param tag can be insensitive {@code String} of possible values of Tag.
     * @throws IllegalValueException if the given tag tag string is invalid.
     */
    public static Tag getInstance(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();

        for (Tag value : Tag.values()) {
            if (value.toString().equalsIgnoreCase(trimmedTag)) {
                return value;
            }
        }

        throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Format tags string as a text for viewing.
     */
    public String getFormattedString() {
        return '[' + this.toString() + ']';
    }
}
