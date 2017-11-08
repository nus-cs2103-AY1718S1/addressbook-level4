package seedu.address.model.parcel;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author kennard123661
/**
 * Status represents the delivery status of a parcel.
 * It can only be one of these values: PENDING, DELIVERING, COMPLETED and OVERDUE.
 * Guarantees: immutable
 *
 * {@code Status.PENDING} means that the {@link Parcel} is pending delivery.
 * {@code Status.DELIVERING} means that the {@link Parcel} is being delivered.
 * {@code Status.COMPLETED} means that the {@link Parcel} has successfully been delivered.
 * {@code Status.OVERDUE} means that the {@link Parcel} is pending delivery and the present date is past the delivery
 * date.
 */
public enum Status {

    PENDING, DELIVERING, COMPLETED, OVERDUE;

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status can only be PENDING, DELIVERING, COMPLETED or OVERDUE";

    /**
     * Returns a static instance of Status based on the {@param status}.
     *
     * @param status can be case-insensitive {@code String} of PENDING, DELIVERING, COMPLETED or OVERDUE.
     * @return one of four possible {@code Status} values.
     * @throws IllegalValueException if {@param status} is not a possible value of {@code Status}
     */
    public static Status getInstance(String status) throws IllegalValueException {
        String trimmedStatus = status.trim().toUpperCase();

        // checks if trimmedStatus can be any of the possible values of Status.
        for (Status value : Status.values()) {
            if (value.toString().equalsIgnoreCase(trimmedStatus)) {
                return value;
            }
        }

        throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
    }
}
