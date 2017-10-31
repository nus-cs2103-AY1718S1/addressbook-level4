package seedu.address.model.person;
import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday should be in the format DDMMYY, separated by '/' or '-' or '.' or ' '. \n"
                   + "The month can be entered as a number or name of the month."
                   + "The date entered must be valid.\n"
                   + "eg. b/09 8 1987, b/23-Mar-98";
    public static final String BIRTHDAY_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.| )(?:0?[13578]|1[02]|"
           + "(?:jan|mar|may|jul|aug|oct|dec)))\\1|(?:(?:29|30)(\\/|-|\\.| )(?:0?[1,3-9]|1[0-2]|"
           + "(?:jan|mar|apr|may|jun|jul|aug|sep|oct|nov|dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.| )"
           + "(?:0?2|(?:feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
           + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.| )(?:(?:0?[1-9]|"
           + "(?:jan|feb|mar|apr|may|jun|jul|aug|sep))|(?:1[0-2]|(?:oct|nov|dec)))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim().toLowerCase();
        String temp = trimmedBirthday;
        if (!trimmedBirthday.isEmpty() & !isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        } else if (!"".equals(trimmedBirthday)) {
            String[] array = trimmedBirthday.replace("/", " ").replace("-", " ")
                    .replace(".", " ").split(" ");
            int monthInt = 0;
            String day;
            String month;
            String year;

            // day
            if (array[0].length() == 2) {
                if (array[0].startsWith("0")) {
                    day = array[0].substring(1); //0X
                } else {
                    day = array[0]; // XX
                }
            } else {
                day = array[0]; // X
            }

            // year
            if (array[2].length() == 2) {
                if (Integer.valueOf(array[2]) < 18) {
                    year = "20" + array[2];
                } else {
                    year = "19" + array[2];
                }
            } else {
                year = array[2];
            }

            // month
            if (array[1].length() < 3) {
                monthInt = Integer.valueOf(array[1]);
            }

            switch (monthInt) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;
            default:
                month = "";
                break;
            }
            if (array[1].length() == 3) {
                month = array[1].substring(0, 1).toUpperCase() + array[1].substring(1);
            }
            temp = day + " " + month + " " + year;
        }
        this.value = temp;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

