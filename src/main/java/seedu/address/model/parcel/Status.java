package seedu.address.model.parcel;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author kennard123661
/**
 * Status represents the delivery status of a parcel
 * It can only be one of these values: PENDING, DELIVER, COMPLETED
 */
public enum Status {

    PENDING, DELIVERING, COMPLETED, OVERDUE;

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status can only be PENDING, COMPLETED, COMPLETED or OVERDUE";

    public static Status getInstance(String status) throws IllegalValueException {
        String trimmedAndUpperCasedStatus = status.trim().toUpperCase();

        if (!isValidStatus(trimmedAndUpperCasedStatus)) {
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }

        switch (trimmedAndUpperCasedStatus) {

        case "OVERDUE":
            return OVERDUE;

        case "PENDING":
            return PENDING;

        case "DELIVERING":
            return DELIVERING;

        case "COMPLETED":
            return COMPLETED;

        default:
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }
    }

    /**
     * @return true if status is case-insensitive equal to the value of any enum Status values.
     */
    public static boolean isValidStatus(String status) {
        switch(status) {
        case "PENDING":  // fallthrough
        case "DELIVERING": // fallthrough
        case "COMPLETED": // fallthrough
        case "OVERDUE": // fallthrough
            return true;

        default:
            return false;
        }
    }
}
