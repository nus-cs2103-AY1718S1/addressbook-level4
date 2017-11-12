package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.person.Country.DEFAULT_COUNTRY_CODE;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    /**
     * Message for both invalid phone numbers and invalid country codes (both are scanned in same regex)
     */
    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers should be 4-15 digits long, and may only contain numbers,"
            + "with an optional country code prefix. Example: '97531', '+65 98765432' \n"
            + "Only valid codes belonging to current states will be accepted. \n"
            + "Please note that current version does not yet support codes with prefix '1-' or '44-', "
            + "support expected in version 2.0. Thank you for your understanding.";
    //@@author icehawker
    // without prefix
    public static final String CODE_VALIDATION_REGEX =
            "^(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|"
            + "6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)$";
    private static final String PHONE_VALIDATION_REGEX = "\\d{4,16}";
    // with country code prefix
    // current regex DOES NOT INCLUDE codes from 1000 onwards!
    private static final String PHONE_VALIDATION_REGEX_ALT =
            "\\+(9[976]\\d|8[987530]\\d|6[987]\\d|5[90]\\d|42\\d|3[875]\\d|2[98654321]\\d|9[8543210]|8[6421]|"
            + "6[6543210]|5[87654321]|4[987654310]|3[9643210]|2[70]|7|1)\\s\\d{4,16}$";
    public final String value;
    private String countryCode;
    //@@author
    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Phone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = trimmedPhone;
        this.countryCode = trimCode(trimmedPhone);
    }
    //@@author icehawker
    /**
     * Extracts country code from a valid phone number, otherwise returns a default code.
     * Note that any unacceptable input has already been rejected upstream by {@link #isValidPhone(String)}.
     */
    public static String trimCode(String trimmedPhone) {
        // only attempt to extract country code if regex is ALT
        if (trimmedPhone.matches(PHONE_VALIDATION_REGEX_ALT)) {
            // take pattern: end with whitespace (expected for ALT regex)
            String[] split = trimmedPhone.split("\\s+");
            return (split[0].trim()).substring(1);
        } else {
            return DEFAULT_COUNTRY_CODE.trim();
        }
    }

    /**
     * Returns true if a given string is a valid person country code. Only for JUnit tests.
     */
    public static boolean isValidCode(String test) {
        return test.matches(CODE_VALIDATION_REGEX);
    }

    /**
     * Returns requested country code String.
     * Note that any unacceptable input has already been rejected upstream by {@link #isValidPhone(String)}.
     */
    public String getCountryCode() {
        return countryCode;
    }
    //@@author
    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return (test.matches(PHONE_VALIDATION_REGEX) || test.matches(PHONE_VALIDATION_REGEX_ALT));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && this.value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
