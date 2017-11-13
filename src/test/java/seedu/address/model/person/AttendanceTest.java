package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.exceptions.PersonMaxAttendanceException;

//@@author pohjie
public class AttendanceTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void defaultConstructorIsWorking() {
        Attendance attendance = new Attendance();
        assertEquals(attendance.getAttended(), 0);
        assertEquals(attendance.getMissed(), 8);
    }

    @Test
    public void constructorWithParametersIsWorking() {
        /**
         * Due to the small range of possible inputs, we shall test all the possible inputs.
         */
        Attendance attendance;
        // boundary value
        try {
            attendance = new Attendance(0);
            assertEquals(attendance.getAttended(), 0);
            assertEquals(attendance.getMissed(), 8);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(1);
            assertEquals(attendance.getAttended(), 1);
            assertEquals(attendance.getMissed(), 7);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(2);
            assertEquals(attendance.getAttended(), 2);
            assertEquals(attendance.getMissed(), 6);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(3);
            assertEquals(attendance.getAttended(), 3);
            assertEquals(attendance.getMissed(), 5);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(4);
            assertEquals(attendance.getAttended(), 4);
            assertEquals(attendance.getMissed(), 4);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(5);
            assertEquals(attendance.getAttended(), 5);
            assertEquals(attendance.getMissed(), 3);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(6);
            assertEquals(attendance.getAttended(), 6);
            assertEquals(attendance.getMissed(), 2);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(7);
            assertEquals(attendance.getAttended(), 7);
            assertEquals(attendance.getMissed(), 1);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        // boundary value
        try {
            attendance = new Attendance(8);
            assertEquals(attendance.getAttended(), 8);
            assertEquals(attendance.getMissed(), 0);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }
    }

    @Test
    public void attendanceConstructor_invalidValue_throwsIllegalValueException() throws Exception {
        // boundary cases
        thrown.expect(IllegalValueException.class);
        new Attendance(-1);

        thrown.expect(IllegalValueException.class);
        new Attendance(9);
    }

    @Test
    public void addAttendance_withValidAttended() {
        try {
            Attendance attendance = new Attendance();
            attendance.addAttendance();
            assertEquals(attendance.getAttended(), 1);
            assertEquals(attendance.getMissed(), 7);
        } catch (PersonMaxAttendanceException e) {
            System.out.println("Max attendance already!");
        }

        // boundary case
        try {
            Attendance attendance = new Attendance(7);
            attendance.addAttendance();
            assertEquals(attendance.getAttended(), 8);
            assertEquals(attendance.getMissed(), 0);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        } catch (PersonMaxAttendanceException e) {
            System.out.println("Max attendance already!");
        }
    }

    @Test
    public void addAttendance_withMaxAttended() throws Exception {
        Attendance attendance = new Attendance();
        for (int i = 0; i < 8; i++) {
            try {
                attendance.addAttendance();
            } catch (PersonMaxAttendanceException e) {
                System.out.println("Max attendance already!");
            }
        }

        assertEquals(attendance.getAttended(), 8);
        assertEquals(attendance.getMissed(), 0);
        thrown.expect(PersonMaxAttendanceException.class);
        attendance.addAttendance();
    }

    @Test
    public void attendance_equalsWorking() {
        Attendance attendance0 = new Attendance();
        Attendance attendance1 = new Attendance();

        assertTrue(attendance0.equals(attendance1));

        try {
            attendance1.addAttendance();
        } catch (PersonMaxAttendanceException e) {
            System.out.println("Max attendance already!");
        }

        assertFalse(attendance0.equals(attendance1));
    }
}
