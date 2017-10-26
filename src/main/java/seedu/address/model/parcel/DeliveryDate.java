package seedu.address.model.parcel;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Parcel's delivery date in the address book.
 * Guarantees: mutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DeliveryDate {


    public static final String MESSAGE_DELIVERY_DATE_CONSTRAINTS =
            "Delivery dates should be in the format dd-mm-yyyy";
    public static final String DATE_VALIDATION_REGEX =
            "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
    public final String value;
    public final String[] valueArray;
    public final LocalDate date;

    /**
     * Validates given delivery date.
     *
     * @throws IllegalValueException if given delivery date string is invalid.
     */
    public DeliveryDate(String deliveryDate) throws IllegalValueException {
        requireNonNull(deliveryDate);
        String trimmedDate = deliveryDate.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
        this.valueArray = trimmedDate.split("-");
        this.date = LocalDate.parse(valueArray[2] + "-" + valueArray[1] + "-" + valueArray[0]);

    }

    /**
     * Returns true if a given string is a valid date for delivery.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    private LocalDate getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryDate // instanceof handles nulls
                && this.value.equals(((DeliveryDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(DeliveryDate deliveryDate) {
        return this.date.compareTo(deliveryDate.getDate());
    }
}
