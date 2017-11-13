package seedu.address.model.person;

//@@author jaivigneshvenugopal
/**
 * Represents the date of when the Person was sent to the whitelist in the address book, i.e. the date
 * the Person repaid his debt completely.
 * Guarantees: immutable;
 */
public class DateRepaid {

    public static final String NO_DATE_REPAID = "NOT REPAID";

    public final String value;

    public DateRepaid() {
        value = NO_DATE_REPAID;
    }

    /**
     * Creates a copy of the DateRepaid object with a set date.
     * @param date must be a valid date
     */
    public DateRepaid(String date) {
        value = date;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateRepaid // instanceof handles nulls
                && this.value.equals(((DateRepaid) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
