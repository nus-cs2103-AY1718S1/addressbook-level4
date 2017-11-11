# Lenaldnwj-unused
###### \Phone.java
``` java

/**
 * Reason for being unused: Removed the entire feature as during the acceptance testing, one user reported that it is inconvenient for users to edit the phone numbers. When editing the numbers,
 * they had to edit both the student number and parent number as the numbers are stored as a single string.
 */

package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the student's and parent's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Users are to enter their numbers in this format, p/ student/(STUDENT_NUMBER) parent/(PARENT_NUMBER)\n"
                    + "For example, p/ student/97271111 parent/97979797\n"
                    + "Phone numbers can only contain numbers, and should be exactly 8 digits";
    public static final String PHONE_VALIDATION_REGEX = "((student/)(\\d\\d\\d\\d\\d\\d\\d\\d)"
            + "( parent/)(\\d\\d\\d\\d\\d\\d\\d\\d))|((parent/)(\\d\\d\\d\\d\\d\\d\\d\\d))";
    public final String value;

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
        trimmedPhone = changeInputToUiFormat(trimmedPhone);
        this.value = trimmedPhone;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    /**
     * Converts user phone number input into an appropriate UI format by
     * replacing all occurrence of "/" with ": " and capitalising first letter of student and parent.
     */
    public static String changeInputToUiFormat(String value) {

        value = value.replace("/", ": ");
        value = value.replace("s", "S");
        value = value.replace("p", "P");
        return value;
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
```
###### \PhoneTest.java
``` java

/**
 * Reason for being unused: Test cases of the unused Phone.java
 */
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone number input format
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("student/97272131 "
                + "parent/9797979")); // parent do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("student/972745 parent/979")); // both do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("student/9727 "
                + "Parent: 97979797")); // student do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("student/91111031 parent/97971241")); // : instead of /
        assertFalse(Phone.isValidPhone("studen/91254311 parent/97213197")); //  misspelled student
        assertFalse(Phone.isValidPhone("student/97272121    Parent/97314197")); // consecutive whitespaces
        assertFalse(Phone.isValidPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(Phone.isValidPhone("student/972jb72031 parent/97jhb979797")); // alphanumeric numbers
        assertFalse(Phone.isValidPhone("student/gfxgfx parent/gfxgfxgfx")); // purely alphabets
        assertFalse(Phone.isValidPhone("student/97270000")); // must have at least parent number
        assertFalse(Phone.isValidPhone("parent/97971231 "
                + "student/97272271")); // parent number should be entered after student number
        // if both parent and student number are entered.
        assertFalse(Phone.isValidPhone(
                "parent/972701")); // have at least parent number, but not exactly 8 digits.
        assertFalse(Phone.isValidPhone("Student/97272031 "
                + "Parent/97979797")); // not all letters in lower case

        // valid phone number input format
        assertTrue(Phone.isValidPhone("student/97272031 "
                + "parent/97979797")); // both parent and student phone number of exactly 8 numbers
        assertTrue(Phone.isValidPhone("student/87272111 "
                + "parent/87767988")); // another set of parent and student phone number of exactly 8 digit numbers
        assertTrue(Phone.isValidPhone("parent/87767988")); // parent number only of exactly 8 digit numbers
    }

    @Test
    public void changeInputToUiFormat() {
        // Invalid Ui Format
        assertNotEquals(Phone.changeInputToUiFormat("student/97272030 parent/97971231"),
                "Student:97272030 Parent:97971231"); // No spacing after Student: and Parent:
        assertNotEquals(Phone.changeInputToUiFormat("student/97272011 parent/91173321"),
                "student:97272011 parent:91173321"); // No capital letter S and P
        assertNotEquals(Phone.changeInputToUiFormat("student/97272030 parent/97971122"),
                "student:97272011 parent:97979700"); // Incorrect numbers
        assertNotEquals(Phone.changeInputToUiFormat("student:97272030 parent:97979797"),
                "student/ 97272011 parent/ 97979700"); // Returned : instead of /
        assertNotEquals(Phone.changeInputToUiFormat("student/97272030 parent/97979797"),
                "student: 97272011 Parent: 97979700"); // Letter s not capitalised (Incorrect labelling)
        assertNotEquals(Phone.changeInputToUiFormat("student/97272030 parent/97979797"),
                "Student: 97272011 parent: 97979700"); // Letter p not capitalised (Incorrect labelling)
        assertNotEquals(Phone.changeInputToUiFormat("student/97272030 parent/97979797"),
                ""); // Returned empty string



        // Valid Ui format
        assertEquals(Phone.changeInputToUiFormat("student/97272030 parent/97979797"),
                "Student: 97272030 Parent: 97979797"); // User followed correct input format
        assertEquals(Phone.changeInputToUiFormat("parent/97979776"),
                "Parent: 97979776"); // Student number left out (student number is a optional field)
        assertEquals(Phone.changeInputToUiFormat("student/82278977 parent/97979776"),
                "Student: 82278977 Parent: 97979776"); // valid Ui format with another set of numbers
    }
}
```
