package seedu.address.model.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.TreeSet;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author YuchenHe98
public class ScheduleTest {

    @Test
    public void isValidSchedule() throws IllegalValueException {

        TreeSet<Integer> timeSet = new TreeSet<>();
        timeSet.add(10630);
        timeSet.add(20700);
        timeSet.add(30730);
        timeSet.add(80730);
        assertFalse(Schedule.isValidTimeSet(timeSet));
        timeSet.remove(80730);
        assertTrue(Schedule.isValidTimeSet(timeSet));
        Schedule testSchedule = new Schedule(timeSet);
        TreeSet[] tables = testSchedule.splitScheduleToDays();
        assertTrue(tables[0].contains(630));
        assertTrue(tables[1].contains(700));
        assertTrue(tables[2].contains(730));
    }
}
