package seedu.address.model.lesson;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.ClassType;

public class ClassTypeTest {

    @Test
    public void isValidClassType() {
        // invalid addresses
        assertFalse(ClassType.isValidClassType("")); // empty string
        assertFalse(ClassType.isValidClassType(" ")); // spaces only
        assertFalse(ClassType.isValidClassType("Lecture")); // spell out 'lecture'
        assertFalse(ClassType.isValidClassType("Tutorial")); // spell out 'Tutorial'


        // valid addresses
        assertTrue(ClassType.isValidClassType("lec"));
        assertTrue(ClassType.isValidClassType("Lec")); // One capital character
        assertTrue(ClassType.isValidClassType("LEc")); // Two capital characters
        assertTrue(ClassType.isValidClassType("LEC")); // Three capital characters

        assertTrue(ClassType.isValidClassType("tut"));
        assertTrue(ClassType.isValidClassType("Tut")); // One capital character
        assertTrue(ClassType.isValidClassType("TUt")); // Two capital characters
        assertTrue(ClassType.isValidClassType("TUT")); // Three capital characters
    }
}
