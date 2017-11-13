package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author justinpoh
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {

    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS = "Birthday must be a valid date"
            + " and in the following format:\n"
            + "'.', '-' and '/' can be used to separate the day, month and year fields,"
            + " and need not be used in pairs (i.e. 21.10/1995 works as well).\n"
            + "Day field: 1 - 31.\n"
            + "Month field: 1-12.\n"
            + "Year field: 1900 - 2099.\n"
            + "Example: 21/10/1995, 21-05-1996, 8.10.1987, 01/12-1995, 01.01-1990";
    public static final int EMPTY_BIRTHDAY_FIELD_MONTH = 0;
    public static final int EMPTY_BIRTHDAY_FIELD_DAY = 0;

    private static final String EMPTY_STRING = "";

    private static final String BIRTHDAY_DASH_SEPARATOR = "-";

    private static final int DATE_DAY_INDEX = 0;
    private static final int DATE_MONTH_INDEX = 1;
    private static final int DATE_YEAR_INDEX = 2;

    private static final int DUMMY_YEAR = 2000;

    private static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";
    private static final String BIRTHDAY_SPLIT_REGEX = "[///./-]";

    private static final int BIRTHDAY_TOMORROW_VALIDATOR = 1;

    public final String value;

    private final LocalDate currentDate;


    /**
     * Validates the given birthday and instantiate a LocalDate object with the date
     * this Birthday object was instantiated.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);

        if (!isValidBirthday(birthday)) {
            System.out.println(birthday);
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        String trimmedBirthday = birthday.trim();
        if (trimmedBirthday.isEmpty()) {
            this.value = EMPTY_STRING;
        } else {
            this.value = convertToDefaultDateFormat(birthday);
        }

        currentDate = LocalDate.now();
    }

    /**
     * This constructor is used for testing purposes.
     * This is because the value of {@code LocalDate.now()} is dependent on the date the tests are conducted
     * and might lead to tests failing depending on the date the tests are conducted.
     */
    private Birthday(String birthday, LocalDate currentDateForTesting) {
        this.value = convertToDefaultDateFormat(birthday);
        this.currentDate = currentDateForTesting;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String birthday) {
        String trimmedBirthday = birthday.trim();
        if (trimmedBirthday.isEmpty()) {
            return true;
        }
        if (!trimmedBirthday.matches(BIRTHDAY_VALIDATION_REGEX)) {
            return false;
        }

        String[] splitDate = getSplitDate(trimmedBirthday);

        if (!isValidDate(splitDate)) {
            return false;
        }
        return true;
    }

    /**
     * Return a Birthday instance that is used for testing.
     */
    public static Birthday getBirthdayTestInstance(String birthday, LocalDate currentDateForTesting) {
        Birthday testInstance = new Birthday(birthday, currentDateForTesting);
        return testInstance;
    }

    /**
     * Returns the month of the birthday in this Birthday object.
     * If the birthday field is empty, return EMPTY_BIRTHDAY_FIELD_MONTH
     */
    public int getMonthOfBirthday() {
        if (value.isEmpty()) {
            return EMPTY_BIRTHDAY_FIELD_MONTH;
        }
        String[] splitDate = getSplitDate(value);
        try {
            final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
            return month;
        } catch (NumberFormatException nfe) {
            throw new AssertionError("Should not happen");
        }
    }

    /**
     * Returns the day of the birthday in this Birthday object.
     * If the birthday field is empty, return EMPTY_BIRTHDAY_FIELD_DAY
     */
    public int getDayOfBirthday() {
        if (value.isEmpty()) {
            return EMPTY_BIRTHDAY_FIELD_DAY;
        }
        String[] splitDate = getSplitDate(value);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        return day;
    }

    /**
     * Returns true if the date for this Birthday object is the same
     * as the date today (relative to the date this Birthday object was instantiated).
     */
    public boolean isBirthdayToday() {
        if (value.isEmpty()) {
            return false;
        }
        final String[] splitDate = getSplitDate(value);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);

        final boolean isMonthEqual = (month == currentDate.getMonthValue());
        final boolean isDayEqual = day == (currentDate.getDayOfMonth());

        return isMonthEqual && isDayEqual;
    }

    /**
     * Returns true of the date for this Birthday object is tomorrow
     * (relative to the date this Birthday object was instantiated)
     */
    public boolean isBirthdayTomorrow() {
        if (value.isEmpty()) {
            return false;
        }
        final String[] splitDate = getSplitDate(value);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);

        final int currentMonth = currentDate.getMonthValue();
        final int currentDay = currentDate.getDayOfMonth();

        final LocalDate birthday = LocalDate.of(DUMMY_YEAR, month, day);
        final LocalDate currentDummyDate = LocalDate.of(DUMMY_YEAR, currentMonth, currentDay);

        final long daysUntilBirthday = currentDummyDate.until(birthday, ChronoUnit.DAYS);
        return daysUntilBirthday == BIRTHDAY_TOMORROW_VALIDATOR;
    }

    /**
     * Returns true if a given date is a valid date.
     * A date is valid if it exists.
     */
    private static boolean isValidDate(String[] splitDate) {
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException dte) {
            return false;
        }
        return true;
    }

    /**
     * Converts the date into the year, month and date fields.
     */
    private static String[] getSplitDate(String trimmedDate) {
        return trimmedDate.split(BIRTHDAY_SPLIT_REGEX);
    }

    /**
     * Converts date in integer array into dd-mm-yyyy String format.
     */
    private static String convertToDefaultDateFormat(String date) {
        String[] splitDate = getSplitDate(date);

        StringBuilder builder = new StringBuilder();

        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);

        builder.append(day);
        builder.append(BIRTHDAY_DASH_SEPARATOR);
        builder.append(month);
        builder.append(BIRTHDAY_DASH_SEPARATOR);
        builder.append(year);
        return builder.toString();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return this == other
                || (other instanceof Birthday
                && this.value.equals(((Birthday) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
