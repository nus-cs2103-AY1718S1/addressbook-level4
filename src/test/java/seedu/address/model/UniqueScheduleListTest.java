package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ELLE;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import junit.framework.TestCase;

import seedu.address.logic.parser.ScheduleCommandParser;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;
import seedu.address.model.schedule.exceptions.ScheduleNotFoundException;
import seedu.address.testutil.TypicalPersons;

//@@author limcel
public class UniqueScheduleListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Schedule scheduleOne;
    private Schedule scheduleTwo;

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueScheduleList.asObservableList().remove(0);
    }

    @Test
    public void compareScheduleTest() throws ParseException {
        scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), getDate());
        scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), getDate());
        assert !scheduleOne.equals(scheduleTwo);
    }

    // Check whether schedule set is non-null
    @Test
    public void scheduleSetUnitTest() {
        Set<Schedule> scheduleSet = new HashSet<Schedule>();
        scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), getDate());
        scheduleTwo = new Schedule(TypicalPersons.BENSON.getName().toString(), getDate());
        UniqueScheduleList uniqueList = new UniqueScheduleList(scheduleSet);
        scheduleSet.add(scheduleOne);
    }

    @Test
    public void ifScheduleListContainsScheduleTest() throws ScheduleNotFoundException {
        UniqueScheduleList scheduleList = new UniqueScheduleList();
        scheduleOne = new Schedule(TypicalPersons.ALICE.getName().toString(), getDate());
        scheduleList.add(scheduleOne);
        assertTrue(scheduleList.contains(scheduleOne));
        scheduleList.remove(scheduleOne);
        assertFalse(scheduleList.contains(scheduleOne));
    }

    @Test
    public void test_chronologicallySortedList() throws ParseException {
        UniqueScheduleList uniqueScheduleList = new UniqueScheduleList();
        Calendar dateOne = getDate();
        Calendar dateTwo = getDate();
        dateOne.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2019-12-25 10:00:00"));
        dateTwo.setTime(ScheduleCommandParser.DATE_FORMAT.parse("2018-12-25 10:00:00"));
        scheduleOne = new Schedule(ALICE.getName().toString(), dateOne);
        scheduleTwo = new Schedule(ELLE.getName().toString(), dateTwo);

        uniqueScheduleList.add(scheduleOne);
        uniqueScheduleList.add(scheduleTwo);

        TestCase.assertTrue(isSorted(uniqueScheduleList));

        ObservableList<Schedule> sortedList = uniqueScheduleList.asObservableListSortedChronologically();
        assertTrue(isSorted(sortedList));
    }


    //===================================== HELPER METHODS ========================================

    /**
     * @return boolean of value true if the list is not sorted chronologically, false otherwise.
     */
    private boolean isSorted(UniqueScheduleList e) {
        Iterator<Schedule> iterator = e.iterator();
        while (iterator.hasNext()) {
            Schedule date1 = iterator.next();
            Schedule date2 = iterator.hasNext() ? iterator.next() : null;
            if (date2 != null) {
                if (date1.getDate().compareTo(date2.getDate()) < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @returns true if the list is sorted chronologically, false otherwise.
     */
    private boolean isSorted(ObservableList<Schedule> e) {
        Iterator<Schedule> iterator = e.iterator();
        while (iterator.hasNext()) {
            Schedule date1 = iterator.next();
            Schedule date2 = iterator.hasNext() ? iterator.next() : null;
            if (date2 != null) {
                if (date1.getDate().compareTo(date2.getDate()) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return the instance of date
     */
    private Calendar getDate() {
        Calendar date = Calendar.getInstance();
        return date;
    }
    //@@author
}
