package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.EmptyFieldException;

/**
 * Represents a Person's date of birth in the address book.
 */
public class DateOfBirth {

    public static final String MESSAGE_DOB_CONSTRAINTS =
            "Please enter in Day Month Year format where the month can be a number or the name"
                    + " and the year can be input in 2-digit or 4-digit format.";
    public static final String MESSAGE_INVALID_MONTH = "Month input is invalid.";
    public static final String MESSAGE_INVALID_DAY = "Day input is invalid.";
    public static final String MESSAGE_INVALID_YEAR = "Year input is invalid.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DOB_VALIDATION_REGEX = "\\d+[\\s-./,]\\p{Alnum}+[\\s-./,]\\d+.*";

    public static final String[] MONTH_NAME_SHORT = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public static final String[] MONTH_NAME_LONG = {"january", "february", "march",
        "april", "may", "june", "july", "august", "september", "october", "november", "december"};
    private static final DateTimeFormatter DOB_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    public final LocalDate dateOfBirth;
    private boolean dateSet;

    /**
     * Initialise a DateOfBirth object with value of empty String. This can ONLY be used in the default field of
     * {@code AddPersonOptionalFieldDescriptor}
     */
    public DateOfBirth() {
        this.dateOfBirth = LocalDate.now();
        this.dateSet = false;
    }

    /**
     * Validates given Date of Birth.
     *
     * @throws IllegalValueException if given date of birth string is invalid.
     */
    public DateOfBirth(String dob) throws IllegalValueException {
        requireNonNull(dob);
        if (dob.isEmpty()) {
            throw new EmptyFieldException(PREFIX_DOB);
        }
        if (!isValidDateOfBirth(dob)) {
            throw new IllegalValueException(MESSAGE_DOB_CONSTRAINTS);
        }
        this.dateOfBirth = dateFormatter(dob);
        this.dateSet = true;
    }

    /**
     * Parses input dob string
     */
    private LocalDate dateFormatter(String dob) throws IllegalValueException {
        List<String> arguments = Arrays.asList(dob.split("[\\s-/.,]"));
        if (arguments.size() < 2) {
            throw new IllegalValueException(MESSAGE_DOB_CONSTRAINTS);
        }
        String day = arguments.get(0);
        String month = arguments.get(1);
        String year = arguments.size() > 2 ? arguments.get(2) : String.valueOf(LocalDate.now().getYear());
        return LocalDate.parse(getValidDay(day) + " " + getValidMonth(month) + " " + getValidYear(year),
                DOB_FORMAT);
    }

    /**
     *
     * @param year 2 or 4 digit string
     * @return
     * @throws IllegalValueException
     */
    private String getValidYear(String year) throws IllegalValueException {
        int currYear = LocalDate.now().getYear();
        if (year.length() > 4) {
            year = year.substring(0, 4);
        }
        if (!year.matches("\\d+") || (year.length() != 2 && year.length() != 4)) {
            throw new IllegalValueException(MESSAGE_INVALID_YEAR);
        } else if (year.length() == 2) {
            int iYear = Integer.parseInt(year);
            if (iYear > currYear % 100) {
                return Integer.toString(iYear + (currYear / 100 - 1) * 100);
            } else {
                return Integer.toString(iYear + currYear / 100 * 100);
            }
        } else {
            return year;
        }
    }

    private String getValidDay(String day) throws IllegalValueException {
        if (Integer.parseInt(day) > 31) {
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
        if (day.length() == 1) {
            return "0" + day;
        } else if (day.length() == 2) {
            return day;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
    }

    private String getValidMonth(String month) throws IllegalValueException {
        int iMonth;
        if (month.matches("\\p{Alpha}+")) {
            iMonth = getMonth(month);
        } else {
            iMonth = Integer.parseInt(month);
        }
        if (iMonth > 12 || iMonth < 1) {
            throw new IllegalValueException(MESSAGE_INVALID_MONTH);
        } else {
            return MONTH_NAME_SHORT[iMonth - 1];
        }
    }

    /**
     * finds int month from string month name
     */
    private int getMonth(String monthName) throws IllegalValueException {
        for (int i = 0; i < MONTH_NAME_LONG.length; i++) {
            if (monthName.toLowerCase().equals(MONTH_NAME_LONG[i].toLowerCase())
                    || monthName.toLowerCase().equals(MONTH_NAME_SHORT[i].toLowerCase())) {
                return i + 1;
            }
        }
        throw new IllegalValueException(MESSAGE_INVALID_MONTH);
    }

    /**
     * Returns true if a given string is a valid person date of birth.
     */
    public static boolean isValidDateOfBirth(String test) {
        return test.matches(DOB_VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return dateSet ? dateOfBirth.format(DOB_FORMAT) : "";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && this.dateOfBirth.equals(((DateOfBirth) other).dateOfBirth)); // state check
    }

    @Override
    public int hashCode() {
        return dateOfBirth.hashCode();
    }
}
