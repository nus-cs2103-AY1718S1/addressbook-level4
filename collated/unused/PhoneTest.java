//@@author Lenaldnwj-unused
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
