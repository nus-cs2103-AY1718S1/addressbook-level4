//@@author Lenaldnwj
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("Student: 97272031 Parent: 9797979")); // parent do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("Student: 9727 Parent: 979")); // both do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("Student: 9727 Parent: 97979797")); // student do not have exactly 8 digit
        assertFalse(Phone.isValidPhone("Student/ 97272031 Parent/ 97979797")); // / instead of :
        assertFalse(Phone.isValidPhone("studen: 97272031 paret: 97979797")); // wrong labelling
        assertFalse(Phone.isValidPhone("Student: 97272031   Parent: 97979797")); // consecutive whitespaces
        assertFalse(Phone.isValidPhone("!@@#$#@$#@{}")); // random symbol
        assertFalse(Phone.isValidPhone("Student: 972jb72031 Parent: 97jhb979797")); // alphanumeric numbers
        assertFalse(Phone.isValidPhone("Student: gfxgfx Parent: gfxgfxgfx")); // purely alphabets
        assertFalse(Phone.isValidPhone("Student: 97272031")); // must have at least parent number
        assertFalse(Phone.isValidPhone("Parent: 97979797 "
                + "Student: 97272031")); // parent number should be entered after student number
        // if both parent and student number are entered.
        assertFalse(Phone.isValidPhone("Parent: 972701")); // have at least parent number, but not exactly 8 digits.
        assertFalse(Phone.isValidPhone("StudenT: 97272031 "
                + "ParenT: 97979797")); // only first letter of student and parent can be in upper case

        // valid phone numbers
        assertTrue(Phone.isValidPhone("Student: 97272031 "
                + "Parent: 97979797")); // both parent and student phone number of exactly 8 numbers
        assertTrue(Phone.isValidPhone("Student: 87272111 "
                + "Parent: 87767988")); // another set of parent and student phone number of exactly 8 digit numbers
        assertTrue(Phone.isValidPhone("Parent: 87767988")); // parent number only of exactly 8 digit numbers
    }

    @Test
    public void changeToAppropriateUiFormat() {
        // Invalid Ui Format
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "Student:97272030 Parent:97979797"); // No spacing after Student: and Parent:
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student:97272030 parent:97979797"); // No capital letter S and P
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student:97272011 parent:97979700"); // Incorrect numbers
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student/ 97272011 parent/ 97979700"); // / does not change to :
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "student: 97272011 Parent: 97979700"); // Letter s not capitalised
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "Student: 97272011 parent: 97979700"); // Letter p not capitalised
        assertNotEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                ""); // Returned empty string


        // Valid Ui Format
        assertEquals(Phone.changeToAppropriateUiFormat("student/97272030 parent/97979797"),
                "Student: 97272030 Parent: 97979797"); // valid Ui format
        assertEquals(Phone.changeToAppropriateUiFormat("parent/97979776"),
                "Parent: 97979776"); // Student number left out (student number is a optional field)
        assertEquals(Phone.changeToAppropriateUiFormat("student/82278977 parent/97979776"),
                "Student: 82278977 Parent: 97979776"); // valid Ui format with another set of numbers
    }
}
