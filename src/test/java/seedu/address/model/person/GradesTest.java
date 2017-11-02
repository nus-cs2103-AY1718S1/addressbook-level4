package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author lincredibleJC
public class GradesTest {
    @Test
    public void isValidGrades() throws Exception {

        //invalid grades
        assertFalse(Grades.isValidGrades("")); // empty string
        assertFalse(Grades.isValidGrades(" ")); // spaces only
        assertFalse(Grades.isValidGrades("^")); // contains a symbol
        assertFalse(Grades.isValidGrades("A")); // contains alphabets
        assertFalse(Grades.isValidGrades("A+")); //contains alphabets and symbols
        assertFalse(Grades.isValidGrades("-100")); // negative number
        assertFalse(Grades.isValidGrades("150.")); // does not end after the decimal point
        assertFalse(Grades.isValidGrades("0.00001")); // more than 2 decimal place
        assertFalse(Grades.isValidGrades("01")); // first digit is 0
        assertFalse(Grades.isValidGrades("120, 150")); //contains two numbers

        //valid grades
        assertTrue(Grades.isValidGrades("0")); // lowest number
        assertTrue(Grades.isValidGrades("80.0")); // 1 decimal point
        assertTrue(Grades.isValidGrades("120.92")); // 2 decimal point
        assertTrue(Grades.isValidGrades("22023232323232")); // very large number
    }
}
