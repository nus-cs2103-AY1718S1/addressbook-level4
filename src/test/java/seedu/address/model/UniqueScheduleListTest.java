package seedu.address.model;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.testutil.TypicalPersons;


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
}
