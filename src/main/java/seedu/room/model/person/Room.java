package seedu.room.model.person;

import seedu.room.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's room in the resident book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_ROOM_CONSTRAINTS =
            "Person rooms can take any values, and it should not be blank";

    /*
     * The first character of the room must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ROOM_VALIDATION_REGEX = "\d{2}-\d{3}[A-Z]?";
    public static final String ROOM_NOT_SET_DEFAULT = "Not Set";

    public final String value;

    /**
     * Validates given room.
     *
     * @throws IllegalValueException if given room string is invalid.
     */
    public Room(String room) throws IllegalValueException {
        if (room == null) {
            this.value = ROOM_NOT_SET_DEFAULT;
        } else {
            if (!isValidRoom(room)) {
                throw new IllegalValueException(MESSAGE_ROOM_CONSTRAINTS);
            }
            this.value = room;
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidRoom(String test) {
        return test.matches(ROOM_VALIDATION_REGEX) || test.equals(ROOM_NOT_SET_DEFAULT);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && this.value.equals(((Room) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
