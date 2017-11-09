package seedu.address.model.parcel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
     * Returns a static instance of Status based on the {@code status} parameter.
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

    /**
     * Returns an updated Status based on the delivery date of the parcel relative to the today's date.
     *
     * @param status the {@code Status} to be updated.
     * @param date the {@link DeliveryDate} of a parcel.
     * @return an updated {@code Status}. If {@code Status} is {@code PENDING} or {@code OVERDUE},
     *         returns {@code PENDING Status} if {@code date} is not after the present date or {@code OVERDUE Status}
     *         if {@code date} is after the present date. If {@code status} is not {@code OVERDUE} or {@code PENDING},
     *         returns {@code status}
     */
    public static Status getUpdatedInstance(Status status, DeliveryDate date) {
        switch (status.toString()) {
        case "PENDING": // fallthrough
        case "OVERDUE":
            return getUpdatedInstance(date);

        default:
            return status;
        }
    }

    /**
     * Returns an updated Status based on the delivery date of the parcel relative to the today's date.
     *
     * @param date the {@link DeliveryDate} of a parcel.
     * @return an updated {@code Status} based on the {@code date}. returns {@code PENDING Status} if {@code date} is
     * not after the present date and returns {@code OVERDUE Status} if {@code date} is after the present date.
     */
    private static Status getUpdatedInstance(DeliveryDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate delivery = LocalDate.parse(date.toString(), formatter);
        LocalDate present = LocalDate.now();

        return present.isAfter(delivery) ? Status.OVERDUE : Status.PENDING;
    }
}
