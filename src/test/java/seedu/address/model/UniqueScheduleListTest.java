package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;
import seedu.address.testutil.TypicalPersons;

//@@author limcel
public class UniqueScheduleListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueScheduleList.asObservableList().remove(0);
    }

    @Test
    public void compareScheduleTest() throws ParseException {
        Calendar date = Calendar.getInstance();
        Schedule scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        Schedule scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), date);
        if (scheduleOne.equals(scheduleTwo)) {
            assert false;
        }
    }

    // Check whether schedule set is non-null
    @Test
    public void scheduleSetUnitTest() {
        Calendar date = Calendar.getInstance();
        Set<Schedule> scheduleSet = new HashSet<Schedule>();
        Schedule scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        Schedule scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), date);
        UniqueScheduleList uniqueList = new UniqueScheduleList(scheduleSet);
        scheduleSet.add(scheduleOne);
    }

    @Test
    public void ifScheduleListContainsScheduleTest() throws ScheduleNotFoundException {
        Calendar date = Calendar.getInstance();
        UniqueScheduleList scheduleList = new UniqueScheduleList();
        Schedule scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), date);
        scheduleList.add(scheduleOne);
        assertTrue(scheduleList.contains(scheduleOne));
        scheduleList.remove(scheduleOne);
        assertFalse(scheduleList.contains(scheduleOne));
    }
}
